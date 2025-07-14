package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.List;


public class Plantern extends Plant implements Shroom{
    private static final int CLEAR_RADIUS = 2;
    private boolean isSleep;
    private static final int imagesNum = 19;
    private static final Image[] sleepImages;
    private static final Image[] normalImages;

    static {
        sleepImages = Constants.getArrayImage("Pictures/plantsGifs/Plantern/sleep/frame_", imagesNum);
        normalImages = Constants.getArrayImage("Pictures/plantsGifs/Plantern/normal/frame_", imagesNum);
    }

    public Plantern(int row, int col, GameMode mode) {
        super(row, col);
        price = 25;
        HP = 100;
        rechargeTime = 10;
        isSleep = setIsSleep(mode);
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        return false;
    }

    public void action(GameUI gameui) {
        if(gameui.getFog() != null) {
            gameui.getFog().clearFogArea(row, col, CLEAR_RADIUS, gameui.getGameLogic().getZombies());
        }
    }

    public void planted(GameUI gameui) {
        action(gameui);
    }

    @Override
    public Image[] getImage() {
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
