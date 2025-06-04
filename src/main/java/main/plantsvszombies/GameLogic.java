package main.plantsvszombies;

import javafx.scene.image.ImageView;

public class GameLogic {
    private final Plant[][] pottedPlants = new Plant[5][9];
    private final Zombie[][] zombies = new Zombie[5][10];

    public GameLogic(){
    }

    public void setPlant(int i, int j, Plant plant){
        pottedPlants[i][j] = plant;
    }

    public void addZombie(int i){
        zombies[i][9] = new OriginalZombie();
    }

    public void checkCorrespondence(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if(pottedPlants[i][j] != null && zombies[i][j] != null) zombies[i][j].eatPlant();
            }
        }
    }

    public void checkDied(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    if(pottedPlants[i][j].getHp() == 0) pottedPlants[i][j] = null;
                    if(zombies[i][j].getHp() == 0) zombies[i][j] = null;
                } catch (NullPointerException e) {
                }
            }
        }
    }
}
