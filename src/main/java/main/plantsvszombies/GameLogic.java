package main.plantsvszombies;

import javafx.scene.image.ImageView;

import javax.swing.plaf.IconUIResource;
import java.util.ArrayList;

public class GameLogic {
    private final Plant[][] pottedPlants = new Plant[5][9];
    private final ArrayList<Zombie> zombies = new ArrayList<>();
    private final ArrayList<Bullet> bullets = new ArrayList<>();

    public GameLogic(){
    }

    public void setPlant(int i, int j, Plant plant){
        if(pottedPlants[i][j] == null) pottedPlants[i][j] = plant;
    }//doroste

    public Zombie addZombie(Zombie z){
        zombies.add(z);
        return z;
    }//doroste

    public void addBullet(Bullet b){
        bullets.add(b);
    }

    public ArrayList<ImageView> checkBulletStrike(){
        ArrayList<ImageView> bulletsImage = new ArrayList<>();
        for(int i = 0; i < bullets.size(); i++){
            if (bullets.get(i).getPicture().getLayoutX() > Constants.width - bullets.get(i).getPicture().getFitWidth()) {
                bulletsImage.add(bullets.get(i).getPicture());
                bullets.remove(i);
                continue;
            }
            for (Zombie z : zombies){
                if(z.getRow() == bullets.get(i).getRow()){
                    if(Math.abs(bullets.get(i).getPicture().getLayoutX() - bullets.get(i).getPicture().getFitHeight() - z.getPicture().getLayoutX()) < 20) {
                        z.damage();
                        bulletsImage.add(bullets.get(i).getPicture());
                        bullets.remove(i);
                    }
                }
            }
        }
        return bulletsImage;
    }


    public void checkCorrespondence(){
        for(Zombie zombie: zombies) {
            if(pottedPlants[zombie.getRow()][zombie.getCol()] != null) zombie.eatPlant();
        }
    }

    public ArrayList<ImageView> checkDied(){
        ArrayList<ImageView> died = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    if(pottedPlants[i][j].getHp() <= 0) pottedPlants[i][j] = null;
                } catch (NullPointerException e) {}
            }
        }
        for (int i = 0; i < zombies.size(); i++) {
            if(zombies.get(i).getHp() <= 0) {
                died.add(zombies.get(i).getPicture());
                zombies.remove(i);
            }
        }
        return died;
    }

    public ArrayList<Integer[]> plantsAligned() {
        ArrayList<Integer[]> shooterCoordination = new ArrayList<>();
        for (Zombie z : zombies){
            ArrayList<Integer> plantsAhead = checkRow(z.getRow(), z.getCol());
            for(Integer integer : plantsAhead) shooterCoordination.add(new Integer[]{z.getRow(), integer});
        }
        return shooterCoordination;
    }//doroste

    private ArrayList<Integer> checkRow(int row, int col){
        ArrayList<Integer> plantsX = new ArrayList<>();
        if(col == 9) col--;
        for (int i = 0; i <= col; i++) {
            if(pottedPlants[row][i] instanceof PeaPlant) plantsX.add(i);
        }
        return plantsX;
    }//doroste

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
