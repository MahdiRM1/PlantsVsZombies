package main.plantsvszombies;

import javafx.scene.image.ImageView;
import java.util.ArrayList;

public class GameLogic {
    private final Plant[][] pottedPlants = new Plant[5][9];
    private final ArrayList<Zombie> zombies = new ArrayList<>();
    private final ArrayList<Bullet> bullets = new ArrayList<>();

    public GameLogic(){
    }

    public void setPlant(int i, int j, Plant plant){
        if(pottedPlants[i][j] == null) {
            pottedPlants[i][j] = plant;
        }
    }

    public Zombie addZombie(Zombie z) {
        zombies.add(z);
        return z;
    }

    public void addBullet(Bullet b){
        bullets.add(b);
    }

    public ArrayList<Bullet> checkBulletStrike(){
        ArrayList<Bullet> bulletsImage = new ArrayList<>();
        for(int i = 0; i < bullets.size(); i++){
            if (bullets.get(i).getPicture().getLayoutX() > Constants.width - bullets.get(i).getPicture().getFitWidth()) {
                bulletsImage.add(bullets.get(i));
                bullets.remove(i);
                continue;
            }
            for (Zombie z : zombies){
                if(z.getRow() == bullets.get(i).getRow()){
                    if(Math.abs(bullets.get(i).getPicture().getLayoutX() - 2 * bullets.get(i).getPicture().getFitHeight() - z.getPicture().getLayoutX()) < 20) {
                        z.damage();
                        bulletsImage.add(bullets.get(i));
                        bullets.remove(i);
                        break;
                    }
                }
            }
        }
        return bulletsImage;
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
                    if (pottedPlants[row][col].getHp() <= 0) {
                        pottedPlants[row][col] = null;
                        plantsToRemove.add(pottedPlants[row][col]);
                    }
                }catch (NullPointerException e) {}
            }
        }
        return plantsToRemove;
    }

    public void setZombieState(){
        for(Zombie zombie : zombies){
            if(zombie.getHp() <= 0) {
                if(zombie.getState() == ZombieState.EATING)
                    zombie.getState().getPlant().resetDamageCaused();
                zombie.setState(ZombieState.DEAD);
            }
            else if(checkCorrespondence(zombie) != null) {
                ZombieState state = ZombieState.EATING.withPlant(checkCorrespondence(zombie));
                zombie.setState(state);
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
            for (int i = 0; i <= z.getCol(); i++) {
                try {
                    if(pottedPlants[z.getRow()][i] instanceof PeaPlant) peaPlants.add((PeaPlant) pottedPlants[z.getRow()][i]);
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

    public ArrayList<Zombie> getZombies() {
        return zombies;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public Plant getPlant(int row, int col){
        return pottedPlants[row][col];
    }
}
