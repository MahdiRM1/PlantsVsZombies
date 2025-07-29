package main.plantsvszombies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.layout.Pane;

public class GameLogic {

    private final List<Plant> plants = new ArrayList<>();
    private final List<Zombie> zombies = new ArrayList<>();
    private final List<Bullet> bullets = new ArrayList<>();
    private final List<Grave> graves = new ArrayList<>();

    //constructor: to load the previously saved game
    public GameLogic(GameState state) {
        loadPlants(state.getPlants());
        loadZombies(state.getZombies());
        loadGraves(state.getGraves());
    }

    //constructor: to start a new game
    public GameLogic(GameMode mode) {
        if (mode == GameMode.NIGHT) {
            makeGraves();
        }
    }

    //generates graves for night
    private void makeGraves() {
        Random rdm = new Random();
        int graveNum = rdm.nextInt(5) + 3;
        for (int i = 0; i < graveNum; i++) {
            int row = rdm.nextInt(5);
            int col = rdm.nextInt(5) + 4;
            if (getGrave(row, col) == null) graves.add(new Grave(row, col));
            else i--;
        }
    }

    //generates the plants list to reload a saved game
    private void loadPlants(List<PlantData> plantDataList) {
        for (PlantData data : plantDataList) {
            if (data.getType().equals("CoffeeBean")|| data.getType().equals("GraveBuster")) continue;
            Plant plant = Constants.getPlant(data.getRow(), data.getCol(), data.getType(), data.isSleep());
            plant.setHP(data.getHP());
            plants.add(plant);
        }
    }

    //generates the zombie list to reload a saved game
    private void loadZombies(List<ZombieData> zombieDataList) {
        for (ZombieData data : zombieDataList) {
            Zombie zombie = switch (data.getType()) {
                case "OriginalZombie" -> new OriginalZombie(data);
                case "ConeheadZombie" -> new ConeheadZombie(data);
                case "ScreenDoorZombie" -> new ScreenDoorZombie(data);
                case "BucketheadZombie" -> new BucketheadZombie(data);
                case "Imp" -> new Imp(data);
                default -> new FlagZombie(data);
            };
            zombies.add(zombie);
        }
    }

    //generates the graves list to reload a saved game
    private void loadGraves(List<GraveData> graveDataList) {
        for (GraveData data : graveDataList) graves.add(new Grave(data));
    }

    public boolean isPlantable(int row, int col) {
        for (Grave grave : graves)
            if (grave.getRow() == row && grave.getCol() == col) return false;

        return getPlant(row, col) == null;
    }

    //plant arraylist to manage all plants
    public void setPlant(Plant plant) {
        plants.add(plant);
    }

    //zombie arraylist to manage all zombies
    public void addZombie(Zombie z) {
        zombies.add(z);
    }

    //bullet arraylist to manage all bullets
    public void addBullet(Bullet bullet, Pane pane) {
        if (bullet == null) return;

        bullets.add(bullet);
        pane.getChildren().addAll(bullet.getPicture());
    }

    //manages bullets and zombie collisions.
    public List<Bullet> checkBulletStrike() {
        List<Bullet> toRemove = new ArrayList<>();
        for (Bullet b : bullets) {
            if (b.layoutX() > Constants.SCREEN_WIDTH) toRemove.add(b);
            for (Zombie z : zombies) {
                double abs = z.layoutX() - b.layoutX();
                if (b.getRow() == z.getRow() && (-100 < abs && abs < 10)
                        && Constants.aliveZombie(z) && !z.isHypnotized()) {
                    z.damage(b.getType());
                    toRemove.add(b);
                }
            }
        }

        bullets.removeAll(toRemove);
        return toRemove;
    }

    //finds and removes finished plants
    public List<Plant> plantsToRemove() {
        List<Plant> toRemove = new ArrayList<>();

        for (Plant plant : plants) if (plant.getHP() <= 0) toRemove.add(plant);
        plants.removeAll(toRemove);
        return toRemove;
    }

    //sets the state of zombies
    public void setZombieState() {
        for (Zombie zombie : zombies) zombie.updateState(plants, zombies);
    }

    //finds and removes dead zombies
    public List<Zombie> zombieToRemove() {
        List<Zombie> died = new ArrayList<>();
        for (Zombie zombie : zombies) {
            if (zombie.getState() == ZombieState.DEAD) died.add(zombie);
        }

        zombies.removeAll(died);
        return died;
    }

    public List<Plant> plantsAction() {
        List<Plant> plantsList = new ArrayList<>();//gomesh nakoni
        for (Plant plant : plants)
            if (plant.actionHappens(zombies))
                plantsList.add(plant);
        return plantsList;
    }

    public void updateGame() {
        for (Zombie z : zombies) z.action();
        for (Bullet b : bullets) b.move();
        setZombieState();
    }

    //lose logic
    public boolean checkLose() {
        for (Zombie zombie : zombies) {
            if (zombie.getCol() < 0) return true;
        }
        return false;
    }

    //win logic
    public boolean checkWin() {
        for (Zombie z : zombies) if(!z.isHypnotized()) return false;
        return GlobalState.gameTime >= 155_000;
    }

    public void removePlant(int row, int col) {
        plants.remove(getPlant(row, col));
    }

    public void removeGrave(Grave grave, Pane pane) {
        pane.getChildren().remove(grave.getPicture());
        graves.remove(grave);
    }

    //getters
    public Plant getPlant(int row, int col) {
        for (Plant plant : plants) {
            if (plant.getRow() == row && plant.getCol() == col) return plant;
        }
        return null;
    }

    public Grave getGrave(int row, int col) {
        for (Grave grave : graves) {
            if (grave.getRow() == row && grave.getCol() == col) return grave;
        }
        return null;
    }

    public List<Zombie> getZombies() {
        return zombies;
    }

    public List<Plant> getPottedPlants() {
        return plants;
    }

    public List<Grave> getGraves() {
        return graves;
    }
}
