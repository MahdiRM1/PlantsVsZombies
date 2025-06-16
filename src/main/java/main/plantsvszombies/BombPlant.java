package main.plantsvszombies;

import java.util.ArrayList;

public abstract class BombPlant extends Plant{

    public BombPlant(int row, int col, long timeCreated){
        super(row, col, timeCreated);
    }

    public abstract boolean boooooom(long time, ArrayList<Zombie> zombies);
}
