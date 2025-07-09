package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.List;

public class CherryBomb extends BombPlant{

    public CherryBomb(int row, int col){
        super(row, col);
        price = 150;
        HP = 100;
        rechargeTime = 15;
        explosionTime = 700;
        endOfAction = 2000;
    }

    @Override
    public void action(List<Zombie> zombies){
        gif.setImage(new Image("file:Pictures/plantsGifs/Boom.gif"));
        Constants.changeScale(gif, 2.5);
        for (Zombie z : zombies){
            if(Math.abs(z.getRow() - row) <= 1 &&  Math.abs(z.getCol() - col) <= 1)
                z.setState(ZombieState.BOOM_DIE);
        }
    }
}
