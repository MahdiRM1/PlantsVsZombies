package main.plantsvszombies;

import java.util.ArrayList;
public class GameLogic {
    private final Plant[][] pottedPlants;
    private final ArrayList<Zombie> zombies = new ArrayList<>();
    private final ArrayList<Bullet> bullets = new ArrayList<>();
// constructor: to load the previously saved game
    public GameLogic(Plant[][] pottedPlants, ArrayList<ZombieData> zombieData){
        this.pottedPlants = pottedPlants;
        for (ZombieData data : zombieData) {
            Zombie zombie = zombieReload(data);
            zombies.add(zombie);
        }
    }
//constructor: to start a new game
    public GameLogic(){
        pottedPlants = new Plant[5][9];
    }

    //read zombies to reload a saved game
    private Zombie zombieReload(ZombieData data){
        switch (data.getType()){
            case "OriginalZombie" -> {
                return new OriginalZombie(data);
            }
            case "ConeheadZombie" -> {
                return new ConeheadZombie(data);
            }
            case "BucketheadZombie" -> {
                return new BucketheadZombie(data);
            }
            case "Imp" -> {
                return new Imp(data);
            }
        }
        return null;
    }
<<<<<<< HEAD
    //form the plants matrix
    public boolean setPlant(int i, int j, Plant plant){
        if(pottedPlants[i][j] == null) {
            pottedPlants[i][j] = plant;
            return true;
        }
        return false;
=======

    public boolean isPlantable(int i, int j){
        return pottedPlants[i][j] == null;
    }

    public void setPlant(int i, int j, Plant plant){
        pottedPlants[i][j] = plant;
>>>>>>> 5f5dd381175d8f2abc224f2d17494cdf19fbc2a9
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
    public ArrayList<Bullet> checkBulletStrike(){
        ArrayList<Bullet> bulletToRemove = new ArrayList<>();
        for(int i = 0; i < bullets.size(); i++){
            if (bullets.get(i).getPicture().getLayoutX() > Constants.width) {
                bulletToRemove.add(bullets.get(i));
                bullets.remove(i);
                continue;
            }
            for (Zombie z : zombies){
                if(z.getRow() == bullets.get(i).getRow()){
                    if(Math.abs(bullets.get(i).getPicture().getLayoutX() - 2 * bullets.get(i).getPicture().getFitHeight() - z.getPicture().getLayoutX()) < 20) {
                        z.damage(bullets.get(i).isIceBullet());
                        bulletToRemove.add(bullets.get(i));
                        bullets.remove(i);
                        break;
                    }
                }
            }
        }
        return bulletToRemove;
    }

    //checks if a zombie has reached a plant
    private Plant plantZombieCollision(Zombie z){
        try{
            if (pottedPlants[z.getRow()][z.getCol()] != null) return pottedPlants[z.getRow()][z.getCol()];
        } catch (ArrayIndexOutOfBoundsException e) {}
        return null;
    }
    //finds and removes finished plants
    public ArrayList<Plant> plantsToRemove() {
        ArrayList<Plant> plantsToRemove = new ArrayList<>();
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 9; col++) {
                try{
                    if(pottedPlants[row][col] instanceof BombPlant bomb){
                        if(bomb.explosion(zombies)) {
                            plantsToRemove.add(bomb);
                            pottedPlants[row][col] = null;
                        }
                    }
                    else if (pottedPlants[row][col].getHP() <= 0) {
                        plantsToRemove.add(pottedPlants[row][col]);
                        pottedPlants[row][col] = null;
                    }
                    else if (pottedPlants[row][col] instanceof NutPlant nut) nut.updateState();
                }catch (NullPointerException e) {}
            }
        }
        return plantsToRemove;
    }
    //sets the state of zombies
    public void setZombieState(){
        for(Zombie zombie : zombies){
<<<<<<< HEAD
            Plant plant = plantZombieCollision(zombie);
            if(zombie.getState() == ZombieState.DIE || zombie.getState() == ZombieState.DEAD || zombie.getState() == ZombieState.BOOM_DIE) continue;
=======
            Plant plant = checkCorrespondence(zombie);
            if(zombie.getState() == ZombieState.DIE || zombie.getState() == ZombieState.DEAD || zombie.getState() == ZombieState.BOOM_DIE);
>>>>>>> 5f5dd381175d8f2abc224f2d17494cdf19fbc2a9
            else if(zombie.getHP() <= 0) {
                if(zombie.getState() == ZombieState.EATING)
                    zombie.getPlant().resetDamageCaused();
                zombie.setState(ZombieState.DIE);
            }
            else if(plant != null) {
                zombie.setState(ZombieState.EATING);
                zombie.setPlantToEat(plant);
            }
            else zombie.setState(ZombieState.WALKING);
        }
    }
    //finds and removes dead zombies
    public ArrayList<Zombie> zombieToRemove(){
        ArrayList<Zombie> died = new ArrayList<>();
        for (int i = 0; i < zombies.size(); i++) {
            if(zombies.get(i).getState() == ZombieState.DEAD) {
                died.add(zombies.get(i));
                zombies.remove(i);
            }
        }
        return died;
    }

    public ArrayList<PeaPlant> plantsAligned() {
        ArrayList<PeaPlant> peaPlants = new ArrayList<>();
        for (Zombie z : zombies){
            if(z.getCol() > 9) break;
            for (int i = 0; i <= z.getCol(); i++) {
                try {
                    if(pottedPlants[z.getRow()][i] instanceof PeaPlant pea) peaPlants.add(pea);
                }catch (ArrayIndexOutOfBoundsException e){}
            }
        }
        return peaPlants;
    }
    //add sun flowers
    public ArrayList<SunFlower> sunFlowers(){
        ArrayList<SunFlower> sunFlowers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if(pottedPlants[i][j] instanceof SunFlower) sunFlowers.add((SunFlower) pottedPlants[i][j]);
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
        return zombies.isEmpty() && GlobalState.gameTime >= 140000;
    }

    public void removePlant(int row , int col) {
        pottedPlants[row][col] = null;
    }

    //getters
    public ArrayList<Zombie> getZombies() {
        return zombies;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public Plant[][] getPottedPlants() {
        return pottedPlants;
    }
}
