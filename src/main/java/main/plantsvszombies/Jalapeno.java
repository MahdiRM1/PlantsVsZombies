package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.List;

public class Jalapeno extends BombPlant{

    public Jalapeno(int row, int col){
        super(row, col);
        price = 125;
        HP = 100;
        rechargeTime = 15;
        explosionTime = 700;
        endOfAction = 2200;
    }

    @Override
    public void action(List<Zombie> zombies){
        gif.setImage(new Image("file:Pictures/plantsGifs/JalapenoAttack.gif"));
        gif.setFitWidth(Constants.TILE_SIZE * 9);
        gif.setLayoutX(Constants.SCREEN_WIDTH/4.9);
        for (Zombie z : zombies){
            if(z.getRow() == row) {
                z.setState(ZombieState.BOOM_DIE);
            }
        }
    }
}