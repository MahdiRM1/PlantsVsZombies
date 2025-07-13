package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.List;
import java.util.Objects;

public class IceShroom extends BombPlant implements Shroom{

    private long wakeUpTime;
    private boolean isSleep;
    private static final int imagesNum = 17;
    private static final Image[] sleepImages;
    private static final Image[] normalImages;

    static {
        sleepImages = Constants.getArrayImage("Pictures/plantsGifs/IceShroom/sleep/frame_", imagesNum);
        normalImages = Constants.getArrayImage("Pictures/plantsGifs/IceShroom/normal/frame_", imagesNum);
    }

    public IceShroom(int row, int col, GameMode mode){
        super(row, col);
        price = 75;
        HP = 100;
        rechargeTime = 15;
        isSleep = setIsSleep(mode);
        wakeUpTime = timeCreated;
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        updateFrame();
        if(!isExploded && nowPic >= getImage().length - 1) {
            nowPic = 0;
            return isExploded = true;
        }
        return false;
    }

    @Override
    public void action(List<Zombie> zombies){
        for (Zombie z : zombies){
            z.setState(ZombieState.FREEZE);
            z.updateFreezeTime();
        }
    }

    @Override
    public void wakeUp() {
        isSleep = false;
    }

    @Override
    public boolean isSleep(){
        return isSleep;
    }

    @Override
    protected Image[] getImage() {
        if (isSleep) return sleepImages;
        return normalImages;
    }

}
