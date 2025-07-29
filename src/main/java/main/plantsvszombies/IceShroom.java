package main.plantsvszombies;

import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class IceShroom extends BombPlant implements Shroom {

    private long wakeUpTime;
    private boolean isSleep;
    private static final int FRAME_COUNT = 17;
    private static final Image[] SLEEP_FRAMES;
    private static final Image[] NORMAL_FRAMES;
    private static final AudioClip sound;

    static {
        SLEEP_FRAMES = Constants.getArrayImage("Pictures/plantPictures/IceShroom/sleep/frame_", FRAME_COUNT);
        NORMAL_FRAMES = Constants.getArrayImage("Pictures/plantPictures/IceShroom/normal/frame_", FRAME_COUNT);
        sound = new AudioClip("file:Audio/frozen.mp3");
    }

    public IceShroom(int row, int col, boolean isSleep) {
        super(row, col);
        price = 75;
        HP = 100;
        rechargeTime = 15;
        this.isSleep = isSleep;
        wakeUpTime = timeCreated;
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        updateFrame();
        if (isSleep) wakeUpTime = GlobalState.gameTime;
        else if (Math.abs(GlobalState.gameTime - wakeUpTime) >= 1500) {
            HP = 0;
            return isExploded = true;
        }
        return false;
    }

    @Override
    public void action(List<Zombie> zombies) {
        sound.play();
        for (Zombie z : zombies) {
            if (Constants.aliveZombie(z) && !z.isHypnotized()){
                z.setState(ZombieState.FREEZE);
                z.updateFreezeTime();
            }
        }
    }

    @Override
    public void wakeUp() {
        isSleep = false;
    }

    @Override
    public boolean isSleep() {
        return isSleep;
    }

    @Override
    protected Image[] getImage() {
        if (isSleep) {
            return SLEEP_FRAMES;
        }
        return NORMAL_FRAMES;
    }

}
