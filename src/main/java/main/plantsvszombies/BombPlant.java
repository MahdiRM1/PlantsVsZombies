package main.plantsvszombies;

import java.util.List;

public abstract class BombPlant extends Plant{

    public BombPlant(int row, int col){
        super(row, col);
    }

    //manage explosion caused by bomb plants
    public abstract boolean explosion(List<Zombie> zombies);
}
