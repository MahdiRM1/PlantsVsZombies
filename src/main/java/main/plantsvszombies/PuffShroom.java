package main.plantsvszombies;

import java.util.List;

import javafx.scene.image.Image;

public class PuffShroom extends PeaPlant implements Shroom {

    private boolean isSleep;
    private static final int FRAME_COUNT = 17;
    private static final Image[] SLEEP_FRAMES;
    private static final Image[] NORMAL_FRAMES;

    static {
        SLEEP_FRAMES = Constants.getArrayImage("Pictures/plantPictures/PuffShroom/sleep/frame_", FRAME_COUNT);
        NORMAL_FRAMES = Constants.getArrayImage("Pictures/plantPictures/PuffShroom/normal/frame_", FRAME_COUNT);
    }

    public PuffShroom(int row, int col, boolean isSleep) {
        super(row, col);
        price = 0;
        HP = 100;
        rechargeTime = 10;
        bulletType = BulletType.SHROOM_BULLET;
        this.isSleep = isSleep;
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        updateFrame();
        if (isSleep) return false;

        for (Zombie z : zombies) {
            if (row == z.getRow() && z.getCol() - col <= 4 && z.getCol() < 10 && z.getCol() >= col) {
                if (z.getState() != ZombieState.DIE && z.getState() != ZombieState.BOOM_DIE &&
                        z.getState() != ZombieState.HYPNOTIZED) return true;
            }
        }
        return false;
    }

    @Override
    protected Image[] getImage() {
        return isSleep ? SLEEP_FRAMES : NORMAL_FRAMES;
    }

    @Override
    public void wakeUp() {
        isSleep = false;
    }

    @Override
    public boolean isSleep() {
        return isSleep;
    }
}
