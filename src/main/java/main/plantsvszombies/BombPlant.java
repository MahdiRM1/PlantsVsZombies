package main.plantsvszombies;

import java.util.ArrayList;

public abstract class BombPlant extends Plant{

    public BombPlant(int row, int col){
        super(row, col);
    }

    //manage explosion caused by bomb plants
    public abstract boolean explosion(ArrayList<Zombie> zombies);
}
