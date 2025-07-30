package main.plantsvszombies;

import java.util.List;

import javafx.scene.image.Image;

public class HypnoShroom extends BombPlant implements Shroom {

    private boolean isSleep;
    private static final int FRAME_COUNT = 20;
    private static final Image[] SLEEP_FRAMES;
    private static final Image[] NORMAL_FRAMES;

    static {
        SLEEP_FRAMES = Constants.getArrayImage("Pictures/plantPictures/HypnoShroom/sleep/frame_", FRAME_COUNT);
        NORMAL_FRAMES = Constants.getArrayImage("Pictures/plantPictures/HypnoShroom/normal/frame_", FRAME_COUNT);
    }

    public HypnoShroom(int row, int col, boolean isSleep) {
        super(row, col);
        price = 75;
        HP = 25;
        rechargeTime = 1;
        this.isSleep = isSleep;
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        updateFrame();
        if (isSleep) return false;
        return HP <= 0;
    }

    public void action(List<Zombie> zombies) {
        for (Zombie z : zombies)
            if (Constants.checkCollision(layoutX(), z.layoutX(), row, z.getRow())){
                z.hypnosis();
                break;
            }
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

    @Override
    protected Image[] getImage() {
        return isSleep ? SLEEP_FRAMES :  NORMAL_FRAMES;
    }
}
