package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.List;

public class ScaredyShroom extends PeaPlant implements Shroom{

    private boolean isSleep;
    private boolean isScare;

    private static final int scareImagesNum = 12;
    private static final int imagesNum = 16;
    private static final Image[] scareImages;
    private static final Image[] sleepImages;
    private static final Image[] normalImages;

    static {
        scareImages = Constants.getArrayImage("Pictures/plantsGifs/ScaredyShroom/cry/frame_", scareImagesNum);
        sleepImages = Constants.getArrayImage("Pictures/plantsGifs/ScaredyShroom/sleep/frame_", imagesNum);
        normalImages = Constants.getArrayImage("Pictures/plantsGifs/ScaredyShroom/normal/frame_", imagesNum);
    }

    public ScaredyShroom(int row, int col, GameMode mode){
        super(row, col);
        price = 25;
        HP = 100;
        rechargeTime = 10;
        bulletType = BulletType.SHROOM_BULLET;
        isSleep = setIsSleep(mode);
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies){
        updateFrame();
        if(isSleep || isScare(zombies)) return false;

        for (Zombie z : zombies)
            if (row == z.getRow() && z.getCol() < 10 && z.getCol() >= col)
                if(z.getState() != ZombieState.DIE && z.getState() != ZombieState.BOOM_DIE)
                    return true;
        return false;
    }

    @Override
    protected Image[] getImage() {
        if (isSleep) return sleepImages;
        if (isScare) return scareImages;
        return normalImages;
    }

    private boolean isScare(List<Zombie> zombies){
        for (Zombie z : zombies)
            if (Math.abs(z.getRow() - row) < 2 && Math.abs(z.getCol() - col) < 2) {
                return isScare = true;
            }
        return isScare = false;
    }

    @Override
    public void wakeUp() {
        isSleep = false;
    }

    @Override
    public boolean isSleep(){
        return isSleep;
    }
}
