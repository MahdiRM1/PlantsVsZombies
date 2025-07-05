package main.plantsvszombies;

import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    private final Plant[][] pottedPlants;
    private final List<Zombie> zombies = new ArrayList<>();
    private final List<Bullet> bullets = new ArrayList<>();
    CoffeeBean coffeeBean;

    //constructor: to load the previously saved game
    public GameLogic(Plant[][] pottedPlants, List<ZombieData> zombieData){
        this.pottedPlants = pottedPlants;
        for (ZombieData data : zombieData) zombies.add(zombieReload(data));
    }

    //constructor: to start a new game
    public GameLogic(){
        pottedPlants = new Plant[Constants.ROWS][Constants.COLS];
    }

    //read zombies to reload a saved game
    private Zombie zombieReload(ZombieData data){
        return switch (data.getType()){
            case "OriginalZombie" -> new OriginalZombie(data);
            case "ConeheadZombie" -> new ConeheadZombie(data);
            case "ScreenDoorZombie" -> new ScreenDoorZombie(data);
            case "Imp" -> new Imp(data);
            default -> new FlagZombie(data);
        };
    }

    public boolean isPlantable(int i, int j){
        return pottedPlants[i][j] == null;
    }

    //form the plants matrix
    public void setPlant(int i, int j, Plant plant){
        pottedPlants[i][j] = plant;
    }

    //zombie arraylist to manage all zombies
    public void addZombie(Zombie z) {
        zombies.add(z);
    }
    //zombie arraylist to manage all bullets
    public void addBullet(Bullet b){
        bullets.add(b);
    }

    //manages bullets and zombie collisions.
    public List<Bullet> checkBulletStrike(){
        List<Bullet> bulletToRemove = new ArrayList<>();
        for(int i = 0; i < bullets.size(); i++){
            if (bullets.get(i).getPicture().getLayoutX() > Constants.SCREEN_WIDTH) {
                bulletToRemove.add(bullets.get(i));
                bullets.remove(i--);
                continue;
            }
            for (Zombie z : zombies){
                if(z.getRow() == bullets.get(i).getRow()){
                    if(Math.abs(bullets.get(i).getPicture().getLayoutX() - 2 * bullets.get(i).getPicture().getFitHeight() - z.getPicture().getLayoutX()) < 20
                            && z.getState() != ZombieState.DIE && z.getState() != ZombieState.BOOM_DIE) {
                        z.damage(bullets.get(i).getType());
                        bulletToRemove.add(bullets.get(i));
                        bullets.remove(i--);
                        break;
                    }
                }
            }
        }
        return bulletToRemove;
    }

    //finds and removes finished plants
    public List<Plant> plantsToRemove() {
        List<Plant> plantsToRemove = new ArrayList<>();

        if (coffeeBean != null && coffeeBean.action()) {
            plantsToRemove.add(coffeeBean);
            coffeeBean = null;
        }

        for (int row = 0; row < Constants.ROWS; row++) {
            for (int col = 0; col < Constants.COLS; col++) {
                Plant plant = pottedPlants[row][col];
                if (plant == null) continue;

                if(plant instanceof BombPlant bomb && bomb.explosion(zombies)){
                    plantsToRemove.add(bomb);
                    pottedPlants[row][col] = null;
                }

                else if (plant.getHP() <= 0) {
                    plantsToRemove.add(plant);
                    pottedPlants[row][col] = null;
                }

                else if (plant instanceof NutPlant nut) nut.updateState();
            }
        }
        return plantsToRemove;
    }

    //sets the state of zombies
    public void setZombieState(){
        for(Zombie zombie : zombies)
            zombie.updateState(pottedPlants);
    }

    //finds and removes dead zombies
    public List<Zombie> zombieToRemove(){
        List<Zombie> died = new ArrayList<>();
        for (int i = 0; i < zombies.size(); i++) {
            if(zombies.get(i).getState() == ZombieState.DEAD) {
                died.add(zombies.get(i));
                zombies.remove(i--);
            }
        }
        return died;
    }

    public List<PeaPlant> plantsAligned() {
        List<PeaPlant> peaPlants = new ArrayList<>();
        for (int row = 0; row < Constants.ROWS; row++) {
            for (int col = 0; col < Constants.COLS; col++) {
                Plant plant = pottedPlants[row][col];
                 if (plant instanceof PeaPlant peaPlant && peaPlant.canShoot(zombies))
                     peaPlants.add(peaPlant);
            }
        }
        return peaPlants;
    }

    //add sunFlowers
    public List<SunFlower> sunFlowers(){
        List<SunFlower> sunFlowers = new ArrayList<>();
        for (int i = 0; i < Constants.ROWS; i++) {
            for (int j = 0; j < Constants.COLS; j++) {
                if(pottedPlants[i][j] instanceof SunFlower sunFlower)
                    sunFlowers.add(sunFlower);
            }
        }
        return sunFlowers;
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
        return zombies.isEmpty() && GlobalState.gameTime >= 140_000;
    }

    public void removePlant(int row , int col) {
        pottedPlants[row][col] = null;
    }

    //getters
    public List<Zombie> getZombies() {
        return zombies;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public Plant[][] getPottedPlants() {
        return pottedPlants;
    }
}
