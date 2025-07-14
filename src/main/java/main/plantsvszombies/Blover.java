package main.plantsvszombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.List;

public class Blover extends Plant implements Shroom{

    private static final int CLEAR_DURATION = 2000;
    private static final int imagesNum = 58;
    private static final Image[] sleepImages;
    private static final Image[] normalImages;
    private boolean isSleep;

    static {
        sleepImages = Constants.getArrayImage("Pictures/plantsGifs/Blover/sleep/frame_", imagesNum);
        normalImages = Constants.getArrayImage("Pictures/plantsGifs/Blover/normal/frame_", imagesNum);
    }

    public Blover(int row, int col, GameMode mode) {
        super(row, col);
        price = 100;
        HP = 100;
        rechargeTime = 30;
        isSleep = setIsSleep(mode);
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        return false;
    }

    public void action(GameUI gameui) {
        if(gameui.getFog() != null) {
            gameui.getFog().clearAllFog();

            Timeline fogRestore = new Timeline(new KeyFrame(Duration.millis(CLEAR_DURATION), e -> {
                gameui.getFog().restoreFog();
                })
            );
            fogRestore.setCycleCount(1);
            fogRestore.play();
        }
    }

    @Override
    protected Image[] getImage() {
        if (isSleep) return sleepImages;
        return normalImages;
    }

    @Override
    public void wakeUp() {

    }

    @Override
    public boolean isSleep() {
        return false;
    }
}
