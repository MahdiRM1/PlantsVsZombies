package main.plantsvszombies;

import java.util.ArrayList;

public class Jalapeno extends BombPlant{

    private static long lastSelection;
    public static final int recharge = 15;

    public Jalapeno(int row, int col){
        super(row, col);
        price = 125;
        HP = 100;
    }

    @Override
    public boolean boooooom(ArrayList<Zombie> zombies) {
        if(Math.abs(GlobalState.gameTime - timeCreated) <= 1000) return false;
        for (Zombie z : zombies){
            if(z.getRow() == row) {
                z.setState(ZombieState.BOOM_DIE);
            }
        }
        return true;
    }

    @Override
    public long getLastSelection() {
        return lastSelection;
    }

    @Override
    public void setLastSelection(long lastSelection) {
        Jalapeno.lastSelection = lastSelection;
    }

    public static double rechargeCheck(){
        return ((double)GlobalState.gameTime - lastSelection) / recharge * 1000;
    }
}
