package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.List;

public class CherryBomb extends BombPlant{

    public static final int recharge = 15;

    public CherryBomb(int row, int col){
        super(row, col);
        price = 150;
        HP = 100;
    }

    @Override
    public boolean explosion(List<Zombie> zombies) {
        if(Math.abs(GlobalState.gameTime - timeCreated) == 700){
            gif.setImage(new Image("file:Pictures/plantsGifs/Boom.gif"));
            for (Zombie z : zombies){
                if(Math.abs(z.getRow() - row) <= 1 &&  Math.abs(z.getCol() - col) <= 1) {
                    z.setState(ZombieState.BOOM_DIE);
                }
            }
        }
        else return Math.abs(GlobalState.gameTime - timeCreated) == 2000;
        return false;
    }
}
