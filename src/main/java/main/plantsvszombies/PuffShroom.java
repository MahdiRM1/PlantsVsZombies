package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.List;

public class PuffShroom extends PeaPlant implements Shroom{

    private boolean isSleep;
    private static final int imagesNum = 17;
    private static final Image[] sleepImages;
    private static final Image[] normalImages;

    static {
        sleepImages = Constants.getArrayImage("Pictures/plantsGifs/PuffShroom/sleep/frame_", imagesNum);
        normalImages = Constants.getArrayImage("Pictures/plantsGifs/PuffShroom/normal/frame_", imagesNum);
    }

    public PuffShroom(int row, int col, GameMode mode){
        super(row, col);
        price = 0;
        HP = 100;
        rechargeTime = 10;
        bulletType = BulletType.SHROOM_BULLET;
        isSleep = setIsSleep(mode);
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies){
        updateFrame();
        if(isSleep) return false;

        for (Zombie z : zombies)
            if (row == z.getRow() && z.getCol() - col <= 4 && z.getCol() < 10 && z.getCol() >= col)
                if(z.getState() != ZombieState.DIE && z.getState() != ZombieState.BOOM_DIE)
                    return true;
        return false;
    }

    @Override
    protected Image[] getImage() {
        if (isSleep) return sleepImages;
        return normalImages;
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
