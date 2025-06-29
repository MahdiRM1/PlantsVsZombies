package main.plantsvszombies;

import java.util.ArrayList;

public class Jalapeno extends BombPlant{

    public static final int recharge = 15;

    public Jalapeno(int row, int col){
        super(row, col);
        price = 125;
        HP = 100;
    }

    @Override
    public boolean explosion(ArrayList<Zombie> zombies) {
        if(Math.abs(GlobalState.gameTime - timeCreated) <= 1000) return false;
        for (Zombie z : zombies){
            if(z.getRow() == row) {
                z.setState(ZombieState.BOOM_DIE);
            }
        }
        return true;
    }
}
