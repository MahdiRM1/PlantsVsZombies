package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.List;

public class IceShroom extends BombPlant implements Shroom{

    private long wakeUpTime;
    private boolean isSleep;
    private static final int FRAME_COUNT = 17;
    private static final Image[] SLEEP_FRAMES;
    private static final Image[] NORMAL_FRAMES;

    static {
        SLEEP_FRAMES = Constants.getArrayImage("Pictures/plantsGifs/IceShroom/sleep/frame_", FRAME_COUNT);
        NORMAL_FRAMES = Constants.getArrayImage("Pictures/plantsGifs/IceShroom/normal/frame_", FRAME_COUNT);
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
        if (isSleep) wakeUpTime = GlobalState.gameTime;
        else if(Math.abs(GlobalState.gameTime - wakeUpTime) >= 1500) {
            HP = 0;
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
        if (isSleep) return SLEEP_FRAMES;
        return NORMAL_FRAMES;
    }

}
