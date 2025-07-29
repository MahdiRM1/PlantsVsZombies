package main.plantsvszombies;

import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class CoffeeBean extends Plant {

    private boolean isEaten;
    private final Shroom shroom;
    private static final Image[] NORMAL_FRAMES;
    private static final Image[] EAT_FRAMES;
    private static final int NORMAL_FRAMES_COUNT = 9;
    private static final int EAT_FRAMES_COUNT = 14;
    private static final AudioClip sound;

    static {
        NORMAL_FRAMES = Constants.getArrayImage("Pictures/plantPictures/CoffeeBean/normal/frame_", NORMAL_FRAMES_COUNT);
        EAT_FRAMES = Constants.getArrayImage("Pictures/plantPictures/CoffeeBean/eat/frame_", EAT_FRAMES_COUNT);
        sound = new AudioClip("file:Audio/coffee.mp3");
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
            sound.play();
        } else if (isEaten && nowPic >= getImage().length - 1) {
            HP = 0;
            return true;
        }
        return false;
    }

    @Override
    protected Image[] getImage() {
        return isEaten ? EAT_FRAMES : NORMAL_FRAMES;
    }

    public void action() {
        shroom.wakeUp();
    }
}
