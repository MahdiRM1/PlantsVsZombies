package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class CherryBomb extends BombPlant{

    public static final int recharge = 15;

    public CherryBomb(int row, int col){
        super(row, col);
        price = 150;
        HP = 100;
    }

    @Override
//    public boolean explosion(ArrayList<Zombie> zombies) {
//        if(Math.abs(GlobalState.gameTime - timeCreated) <= 1000) return false;
//        for (Zombie z : zombies){
//            if(z.getRow() >= row - 1 && z.getRow() <= row + 1
//            &&  z.getCol() >= col - 1 && z.getCol() <= col + 1) {
//                z.setState(ZombieState.BOOM_DIE);
//            }
//        }
//        return true;
//    }

    public boolean explosion(ArrayList<Zombie> zombies) {
        if(Math.abs(GlobalState.gameTime - timeCreated) == 800){
            gif.setImage(new Image("file:Pictures/plantsGifs/Boom.gif"));
            for (Zombie z : zombies){
                if(z.getRow() >= row - 1 && z.getRow() <= row + 1
                        &&  z.getCol() >= col - 1 && z.getCol() <= col + 1) {
                    z.setState(ZombieState.BOOM_DIE);
                }
            }
        }
        else return Math.abs(GlobalState.gameTime - timeCreated) == 1700;
        return false;
    }
}
