package main.plantsvszombies;

import java.util.ArrayList;

public class Jalapeno extends BombPlant{

    public Jalapeno(int row, int col, long timeCreated){
        super(row, col, timeCreated);
        price = 125;
        HP = 100;
        recharge = 5;
        gif = Constants.setPlantPicture("Jalapeno", row, col);
    }

    @Override
    public boolean boooooom(long time, ArrayList<Zombie> zombies) {
        if(Math.abs(time - timeCreated) <= 1000) return false;
        for (Zombie z : zombies){
            if(z.getRow() == row) {
                z.setState(ZombieState.BOOM_DIE);
            }
        }
        return true;
    }
}
