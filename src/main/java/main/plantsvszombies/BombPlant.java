package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.List;

public abstract class BombPlant extends Plant{

    protected int explosionTime;
    protected int endOfAction;

    public BombPlant(int row, int col){
        super(row, col);
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        if(Math.abs(GlobalState.gameTime - timeCreated) == 700){
            return true;
        }
        if(Math.abs(GlobalState.gameTime - timeCreated) == 2000) HP = 0;
        return false;
    }

    public abstract void action(List<Zombie> zombies);
}
