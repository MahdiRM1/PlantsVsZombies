package main.plantsvszombies;

import java.util.List;

public abstract class NutPlant extends Plant{

    protected NutState state;
    protected double maxHP;

    public NutPlant(int row, int col){
        super(row, col);
        state = NutState.FULL_LIFE;
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        updateFrame();
        if (HP < maxHP / 4) {
            state = NutState.END_LIFE;
        } else if (HP < maxHP / 1.5 && state == NutState.FULL_LIFE) {
            state = NutState.HALF_LIFE;
        }
        return false;
    }
}
