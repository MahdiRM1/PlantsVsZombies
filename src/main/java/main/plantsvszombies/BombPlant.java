package main.plantsvszombies;

import java.util.ArrayList;

public abstract class BombPlant extends Plant{

    public BombPlant(int row, int col){
        super(row, col);
    }

    public abstract boolean boooooom(ArrayList<Zombie> zombies);
}
