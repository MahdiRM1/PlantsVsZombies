package main.plantsvszombies;

import java.util.List;

import javafx.scene.image.Image;

public class CoffeeBean extends Plant {

    private boolean isEaten;
    private final Shroom shroom;
    private static final Image[] NORMAL_FRAMES;
    private static final Image[] EAT_FRAMES;
    private static final int NORMAL_FRAMES_COUNT = 9;
    private static final int EAT_FRAMES_COUNT = 14;

    static {
        NORMAL_FRAMES = Constants.getArrayImage("Pictures/plantsGifs/CoffeeBean/normal/frame_", NORMAL_FRAMES_COUNT);
        EAT_FRAMES = Constants.getArrayImage("Pictures/plantsGifs/CoffeeBean/eat/frame_", EAT_FRAMES_COUNT);
    }

    public CoffeeBean(int row, int col, Shroom shroom) {
        super(row, col);
        price = 75;
        HP = 100;
        rechargeTime = 10;
        this.shroom = shroom;
        isEaten = false;
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        updateFrame();
        if (!isEaten && Math.abs(GlobalState.gameTime - timeCreated) >= 1500) {
            nowPic = 0;
            isEaten = true;
        } else if (isEaten && nowPic >= getImage().length - 1) {
            HP = 0;
            return true;
        }
        return false;
    }

    @Override
    protected Image[] getImage() {
        if (isEaten) {
            return EAT_FRAMES;
        }
        return NORMAL_FRAMES;
    }

    public void action() {
        shroom.wakeUp();
    }
}
