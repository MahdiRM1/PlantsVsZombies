package main.plantsvszombies;

import java.util.ArrayList;

public class GameLogic {
    private final Plant[][] pottedPlants = new Plant[5][9];
    private final ArrayList<Zombie> zombies = new ArrayList<>();
    private final ArrayList<Bullet> bullets = new ArrayList<>();

    public boolean setPlant(int i, int j, Plant plant){
        if(pottedPlants[i][j] == null) {
            pottedPlants[i][j] = plant;
            return true;
        }
        return false;
    }

    public void addZombie(Zombie z) {
        zombies.add(z);
    }

    public void addBullet(Bullet b){
        bullets.add(b);
    }

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


    private Plant checkCorrespondence(Zombie z){
        try{
            if (pottedPlants[z.getRow()][z.getCol()] != null) return pottedPlants[z.getRow()][z.getCol()];
        } catch (ArrayIndexOutOfBoundsException e) {}
        return null;
    }

    public ArrayList<Plant> plantsToRemove() {
        ArrayList<Plant> plantsToRemove = new ArrayList<>();
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 9; col++) {
                try{
                    if(pottedPlants[row][col] instanceof BombPlant bomb){
                        if(bomb.boooooom(zombies)) {
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

    public void setZombieState(){
        for(Zombie zombie : zombies){
            Plant plant = checkCorrespondence(zombie);
            if(zombie.getState() == ZombieState.DIE || zombie.getState() == ZombieState.DEAD || zombie.getState() == ZombieState.BOOM_DIE) continue;
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

    public ArrayList<SunFlower> sunFlowers(){
        ArrayList<SunFlower> sunFlowers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if(pottedPlants[i][j] instanceof SunFlower) sunFlowers.add((SunFlower) pottedPlants[i][j]);
            }
        }
        return sunFlowers;
    }

    public boolean checkLose() {
        for(Zombie zombie : zombies) {
            if(zombie.getCol()  < 0) {
                return true;
            }
        }
        return false;
    }

    public boolean checkWin() {
        return zombies.isEmpty() && GlobalState.gameTime >= 140000;
    }

    public ArrayList<Zombie> getZombies() {
        return zombies;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}
