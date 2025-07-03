package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class ScaredyShroom extends PeaPlant implements Shroom{

    public static final int recharge = 10;
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
        bulletType = BulletType.SHROOM_BULLET;
        isSleep = setIsSleep(mode);
        gif.setImage((isSleep) ? sleepImage : normalImage);
    }

    @Override
    public boolean canShoot(List<Zombie> zombies){
        if(!isSleep) {
            if (isScare(zombies)) return false;

            for (Zombie z : zombies)
                if (row == z.getRow() && z.getCol() < 10)
                    if(z.getState() != ZombieState.DIE && z.getState() != ZombieState.BOOM_DIE)
                        return true;
        }
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
