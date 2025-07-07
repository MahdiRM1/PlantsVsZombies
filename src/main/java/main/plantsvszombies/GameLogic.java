package main.plantsvszombies;

import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    private final List<Plant> plants = new ArrayList<>();
    private final List<Zombie> zombies = new ArrayList<>();
    private final List<Bullet> bullets = new ArrayList<>();

    //constructor: to load the previously saved game
    public GameLogic(GameState state){
        loadPlants(state);
        loadZombies(state.getZombies());
    }

    //constructor: to start a new game
    public GameLogic(){}

    //generates the plants list to reload a saved game
    private void loadPlants(GameState state){
        for (PlantData data : state.getPlants()){
            Plant plant = Constants.getPlant(data.getRow(), data.getCol(), data.getType(), state.getMode());
            plants.add(plant);
        }
    }

    //generates the zombie list to reload a saved game
    private void loadZombies(List<ZombieData> zombieDataList){
        for (ZombieData data : zombieDataList) {
            Zombie zombie = switch (data.getType()){
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

    public boolean isPlantable(int row, int col){
        return getPlant(row, col) == null;
    }

    //plant arraylist to manage all plants
    public void setPlant(Plant plant){
        plants.add(plant);
    }

    //zombie arraylist to manage all zombies
    public void addZombie(Zombie z) {
        zombies.add(z);
    }
    //bullet arraylist to manage all bullets
    public void addBullet(Bullet b){
        bullets.add(b);
    }

    //manages bullets and zombie collisions.
    public List<Bullet> checkBulletStrike(){
        List<Bullet> toRemove = new ArrayList<>();
        for(Bullet bullet : bullets){
            if (bullet.getPicture().getLayoutX() > Constants.SCREEN_WIDTH) {
                toRemove.add(bullet);
                continue;
            }
            for (Zombie z : zombies){
                if(z.getRow() == bullet.getRow() &&
                        Math.abs(bullet.getPicture().getLayoutX() - 2 * bullet.getPicture().getFitHeight() - z.getPicture().getLayoutX()) < 20
                        && z.getState() != ZombieState.DIE && z.getState() != ZombieState.BOOM_DIE) {
                    z.damage(bullet.getType());
                    toRemove.add(bullet);
                    break;
                }
            }
        }

        bullets.removeAll(toRemove);
        return toRemove;
    }

    //finds and removes finished plants
    public List<Plant> plantsToRemove() {
        List<Plant> toRemove = new ArrayList<>();

        for (Plant plant : plants) {
            if(plant instanceof BombPlant bomb && bomb.explosion(zombies)) toRemove.add(bomb);
            else if (plant instanceof CoffeeBean coffeeBean && coffeeBean.action()) toRemove.add(coffeeBean);
            else if (plant.getHP() <= 0) toRemove.add(plant);
            else if (plant instanceof NutPlant nut) nut.updateState();
        }

        plants.removeAll(toRemove);
        return toRemove;
    }

    //sets the state of zombies
    public void setZombieState(){
        for(Zombie zombie : zombies)
            zombie.updateState(plants);
    }

    //finds and removes dead zombies
    public List<Zombie> zombieToRemove(){
        List<Zombie> died = new ArrayList<>();
        for (Zombie zombie : zombies)
            if(zombie.getState() == ZombieState.DEAD) died.add(zombie);

        zombies.removeAll(died);
        return died;
    }

    public List<PeaPlant> plantsAligned() {
        List<PeaPlant> peaPlants = new ArrayList<>();
        for (Plant plant : plants) {
            if (plant instanceof PeaPlant peaPlant && peaPlant.canShoot(zombies))
                peaPlants.add(peaPlant);
        }
        return peaPlants;
    }

    //add sunFlowers
    public List<SunFlower> sunFlowers(){
        List<SunFlower> sunFlowers = new ArrayList<>();
        for (Plant plant : plants)
            if(plant instanceof SunFlower sunFlower)
                sunFlowers.add(sunFlower);
        return sunFlowers;
    }

    public void updateGame(){
        for(Zombie z : zombies) z.action();
        for(Bullet b : bullets) b.move();
        setZombieState();
    }

    //lose logic
    public boolean checkLose() {
        for(Zombie zombie : zombies) {
            if(zombie.getCol()  < 0) {
                return true;
            }
        }
        return false;
    }

    //win logic
    public boolean checkWin() {
        return zombies.isEmpty() && GlobalState.gameTime >= 150_000;
    }

    public void removePlant(int row , int col) {
        plants.remove(getPlant(row, col));
    }

    //getters
    public Plant getPlant(int row, int col){
        for (Plant plant : plants)
            if(plant.getRow() == row && plant.getCol() == col) return plant;
        return null;
    }

    public List<Zombie> getZombies() {
        return zombies;
    }

    public List<Plant> getPottedPlants() {
        return plants;
    }
}
