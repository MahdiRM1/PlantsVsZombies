package main.plantsvszombies;

import java.util.List;

public abstract class BombPlant extends Plant {

    protected boolean isExploded;

    public BombPlant(int row, int col) {
        super(row, col);
        isExploded = false;
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        updateFrame();
        if (!isExploded && nowPic >= getImage().length - 1) {
            nowPic = 0;
            return isExploded = true;
        }
        if (isExploded && nowPic >= getImage().length - 1) die();
        return false;
    }

    public abstract void action(List<Zombie> zombies);
}
