package main.plantsvszombies.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.layout.Pane;
import main.plantsvszombies.Enums.GameMode;
import main.plantsvszombies.Game.Tools.Constants;
import main.plantsvszombies.Game.Tools.Utils;
import main.plantsvszombies.GameState.GameState;
import main.plantsvszombies.GameState.GraveData;
import main.plantsvszombies.GameState.PlantData;
import main.plantsvszombies.GameState.ZombieData;
import main.plantsvszombies.Items.*;
import main.plantsvszombies.Plants.*;
import main.plantsvszombies.Zombies.*;

public class GameLogic {

    private final List<Plant> plants = new ArrayList<>();
    private final List<Zombie> zombies = new ArrayList<>();
    private final List<Bullet> bullets = new ArrayList<>();
    private final List<Grave> graves = new ArrayList<>();
    private final List<LawnCleaner> lawnCleaners = new ArrayList<>();

    // constructor: to load the previously saved game
    public GameLogic(GameState state) {
        loadPlants(state.getPlants());
        loadZombies(state.getZombies());
        loadGraves(state.getGraves());
        loadLawnCleaners(state.getLawnCleaners());
    }

    // constructor: to start a new game
    public GameLogic(GameMode mode) {
        makeLawnCLeaners();
        if (mode == GameMode.NIGHT) {
            makeGraves();
        }
    }

    private void makeLawnCLeaners(){
        ArrayList<Integer> rows = new ArrayList<>();
        while(rows.size() < 3){
            int row = (int)(Math.random() * 5);
            if (rows.contains(row)) continue;
            lawnCleaners.add(new LawnCleaner(row));
            rows.add(row);
        }
    }

    // generates graves for night
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

    // generates the plants list to reload a saved game
    private void loadPlants(List<PlantData> plantDataList) {
        for (PlantData data : plantDataList) {
            if (data.getType().equals("CoffeeBean")|| data.getType().equals("GraveBuster")) continue;
            Plant plant = Utils.buildPlant(data.getRow(), data.getCol(), data.getType(), data.isSleep());
            plant.setHP(data.getHP());
            plant.setTimeCreated(data.getTimeCreated());
            plants.add(plant);
        }
    }

    // generates the zombie list to reload a saved game
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

    // generates the graves list to reload a saved game
    private void loadGraves(List<GraveData> graveDataList) {
        for (GraveData data : graveDataList) graves.add(new Grave(data));
    }

    private void loadLawnCleaners(List<Integer> lcData){
        for (Integer row: lcData) lawnCleaners.add(new LawnCleaner(row));
    }

    // checks if the plant is plantable
    public boolean isPlantable(int row, int col) {
        return getPlant(row, col) == null && getGrave(row, col) == null;
    }

    // plant arraylist to manage all plants
    public void setPlant(Plant plant) {
        plants.add(plant);
    }

    // bullet arraylist to manage all bullets
    public void addBullet(Bullet bullet, Pane pane) {
        if (bullet == null) return;

        bullets.add(bullet);
        pane.getChildren().addAll(bullet.getPicture());
    }

    // manages bullets and zombie collisions.
    public List<Bullet> checkBulletStrike() {
        List<Bullet> toRemove = new ArrayList<>();
        for (Bullet b : bullets) 
            if (b.checkStrike(zombies)) toRemove.add(b);

        bullets.removeAll(toRemove);
        return toRemove;
    }

    // finds and removes finished plants
    public List<Plant> plantsToRemove() {
        List<Plant> toRemove = new ArrayList<>();

        for (Plant plant : plants) if (plant.checkDied()) toRemove.add(plant);
        plants.removeAll(toRemove);
        return toRemove;
    }

    // sets the state of zombies
    public void setZombieState() {
        for (Zombie zombie : zombies) zombie.updateState(plants, zombies);
    }

    // finds and removes dead zombies
    public List<Zombie> zombieToRemove() {
        List<Zombie> died = new ArrayList<>();
        for (Zombie zombie : zombies) {
            if (zombie.checkDied()) died.add(zombie);
        }

        zombies.removeAll(died);
        return died;
    }

    // finds and removes dead lawnCleaners
    public List<LawnCleaner> LCToRemove() {
        List<LawnCleaner> died = new ArrayList<>();
        for (LawnCleaner lc : lawnCleaners) {
            if (lc.getPicture().getLayoutX() > Constants.SCREEN_WIDTH) {
                lawnCleaners.remove(lc);
                died.add(lc);
            }
        }

        zombies.removeAll(died);
        return died;
    }

    // updates the plant actions
    public List<Plant> updatePlantActions() {
        List<Plant> plantsList = new ArrayList<>();// !gomesh nakoni
        for (Plant plant : plants)
            if (plant.actionHappens(zombies))
                plantsList.add(plant);
        return plantsList;
    }

    // updates the game logic
    public void updateGame() {
        for (Zombie z : zombies) z.action();
        for (Bullet b : bullets) b.move();
        for (LawnCleaner lc : lawnCleaners) lc.action(zombies);
        setZombieState();
    }

    // removes the plant
    public void removePlant(int row, int col, Pane pane) {
        Plant plant = getPlant(row, col);
        plants.remove(plant);
        pane.getChildren().remove(plant.getPicture());
    }

    // removes the grave
    public void removeGrave(Grave grave, Pane pane) {
        pane.getChildren().remove(grave.getPicture());
        graves.remove(grave);
    }

    // getters
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

    public List<LawnCleaner> getLawnCleaners() {
        return lawnCleaners;
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
