package main.plantsvszombies;

import java.util.ArrayList;

public class CherryBomb extends BombPlant{

    public CherryBomb(int row, int col, long timeCreated){
        super(row, col, timeCreated);
        price = 150;
        HP = 100;
        recharge = 5;
        gif = Constants.setPlantPicture("CherryBomb", row, col);
    }

    @Override
    public boolean boooooom(long time, ArrayList<Zombie> zombies) {
        if(Math.abs(time - timeCreated) <= 1000) return false;
        for (Zombie z : zombies){
            if(z.getRow() >= row - 1 && z.getRow() <= row + 1
            &&  z.getCol() >= col - 1 && z.getCol() <= col + 1) {
                z.setState(ZombieState.BOOM_DIE);
            }
        }
        return true;
    }
}
