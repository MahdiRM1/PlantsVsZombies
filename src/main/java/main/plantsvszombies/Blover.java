package main.plantsvszombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.List;

public class Blover extends Plant{

    private static final int imagesNum = 59;
    private static final Image[] normalImages;

    static {
        normalImages = Constants.getArrayImage("Pictures/plantsGifs/Blover/normal/frame_", imagesNum);
    }

    public Blover(int row, int col) {
        super(row, col);
        price = 100;
        HP = 100;
        rechargeTime = 30;
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        updateFrame();
        return nowPic == normalImages.length-1;
    }

    public void action(Fog fog) {
        fog.clearFog();
        fog.setBloverTime(GlobalState.gameTime);
        HP = 0;
    }

    @Override
    protected Image[] getImage() {
        return normalImages;
    }
}
