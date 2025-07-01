package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.List;

public class Jalapeno extends BombPlant{

    public static final int recharge = 15;

    public Jalapeno(int row, int col){
        super(row, col);
        price = 125;
        HP = 100;
    }

    @Override
    public boolean explosion(List<Zombie> zombies) {
        if(Math.abs(GlobalState.gameTime - timeCreated) == 700){
            gif.setImage(new Image("file:Pictures/plantsGifs/JalapenoAttack.gif"));
            gif.setFitWidth(Constants.TILE_SIZE * 9);
            gif.setLayoutX(Constants.SCREEN_WIDTH/4.9);
            for (Zombie z : zombies){
                if(z.getRow() == row) {
                    z.setState(ZombieState.BOOM_DIE);
                }
            }
        }
        else return Math.abs(GlobalState.gameTime - timeCreated) == 2200;
        return false;
    }
}