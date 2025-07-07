package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.List;

public class ScaredyShroom extends PeaPlant implements Shroom{

    private boolean isSleep;
    private static final Image scareImage;
    private static final Image sleepImage;
    private static final Image normalImage;

    static {
        scareImage = new Image("file:Pictures/plantsGifs/ScaredyShroomCry.gif");
        sleepImage = new Image("file:Pictures/plantsGifs/ScaredyShroomSleep.gif");
        normalImage = new Image("file:Pictures/plantsGifs/ScaredyShroom.gif");
    }

    public ScaredyShroom(int row, int col, GameMode mode){
        super(row, col);
        price = 25;
        HP = 100;
        rechargeTime = 10;
        bulletType = BulletType.SHROOM_BULLET;
        isSleep = setIsSleep(mode);
        gif.setImage((isSleep) ? sleepImage : normalImage);
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies){
        if(isSleep || isScare(zombies)) return false;

        updateFrame();
        for (Zombie z : zombies)
            if (row == z.getRow() && z.getCol() < 10 && z.getCol() >= col)
                if(z.getState() != ZombieState.DIE && z.getState() != ZombieState.BOOM_DIE)
                    return true;
        return false;
    }

    private boolean isScare(List<Zombie> zombies){
        for (Zombie z : zombies)
            if (Math.abs(z.getRow() - row) < 2 && Math.abs(z.getCol() - col) < 2) {
                if(!gif.getImage().equals(scareImage)) gif.setImage(scareImage);
                return true;
            }
        if(!gif.getImage().equals(normalImage)) gif.setImage(normalImage);
        return false;
    }

    @Override
    public void wakeUp() {
        isSleep = false;
        gif.setImage(normalImage);
    }

    @Override
    public boolean isSleep(){
        return isSleep;
    }
}
