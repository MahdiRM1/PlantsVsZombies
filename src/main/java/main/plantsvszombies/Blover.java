package main.plantsvszombies;

import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class Blover extends Plant {

    private static final int FRAME_COUNT = 59;
    private static final Image[] FRAMES;
    private static final AudioClip sound;


    static {
        FRAMES = Constants.getArrayImage("Pictures/plantPictures/Blover/normal/frame_", FRAME_COUNT);
        sound = new AudioClip("file:Audio/blover.mp3");
    }

    public Blover(int row, int col) {
        super(row, col);
        price = 100;
        HP = 100;
        rechargeTime = 30;
        if (row != -1) sound.play();
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        updateFrame();
        if(nowPic == FRAMES.length - 1) HP = 0;
        return nowPic > 13 && nowPic < 49;
    }

    public void action(Fog fog) {
        if (fog == null) return;
        fog.move(true);
        fog.setBloverTime(GlobalState.gameTime);
    }

    @Override
    protected Image[] getImage() {
        return FRAMES;
    }
}
