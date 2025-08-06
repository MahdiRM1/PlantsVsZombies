package main.plantsvszombies.Plants.PeaPlants;

import java.util.List;

import javafx.scene.image.Image;
import main.plantsvszombies.Enums.BulletType;
import main.plantsvszombies.Game.Constants;
import main.plantsvszombies.Plants.Shroom;
import main.plantsvszombies.Zombies.Zombie;

public class ScaredyShroom extends PeaPlant implements Shroom {

    private boolean isSleep;
    private boolean isScare;

    private static final int SCARE_FRAME_COUNT = 12;
    private static final int FRAME_COUNT = 16;
    private static final Image[] SCARE_FRAMES;
    private static final Image[] SLEEP_FRAMES;
    private static final Image[] NORMAL_FRAMES;

    static {
        SCARE_FRAMES = Constants.getArrayImage("Pictures/plantPictures/ScaredyShroom/cry/frame_", SCARE_FRAME_COUNT);
        SLEEP_FRAMES = Constants.getArrayImage("Pictures/plantPictures/ScaredyShroom/sleep/frame_", FRAME_COUNT);
        NORMAL_FRAMES = Constants.getArrayImage("Pictures/plantPictures/ScaredyShroom/normal/frame_", FRAME_COUNT);
    }

    public ScaredyShroom(int row, int col, boolean isSleep) {
        super(row, col);
        price = 25;
        HP = 100;
        rechargeTime = 10;
        bulletType = BulletType.SHROOM_BULLET;
        this.isSleep = isSleep;
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        updateFrame();
        if (isSleep || isScare(zombies)) {
            return false;
        }

        for (Zombie z : zombies) {
            if (row == z.getRow() && z.getCol() < 10 && z.getCol() >= col) {
                if (z.alive() && !z.isHypnotized())
                    return true;
            }
        }
        return false;
    }

    @Override
    protected Image[] getImage() {
        return isSleep ? SLEEP_FRAMES : isScare ? SCARE_FRAMES : NORMAL_FRAMES;
    }

    private boolean isScare(List<Zombie> zombies) {
        for (Zombie z : zombies) {
            if (Math.abs(z.getRow() - row) < 2 && Math.abs(z.getCol() - col) < 2) return isScare = true;
        }
        return isScare = false;
    }

    @Override
    public void wakeUp() {
        isSleep = false;
        wakeUpSound.play();
    }

    @Override
    public boolean isSleep() {
        return isSleep;
    }
}
