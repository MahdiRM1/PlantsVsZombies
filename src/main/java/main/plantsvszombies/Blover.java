package main.plantsvszombies;

import java.util.List;

import javafx.scene.image.Image;

public class Blover extends Plant {

    private static final int FRAME_COUNT = 59;
    private static final Image[] FRAMES;

    static {
        FRAMES = Constants.getArrayImage("Pictures/plantPictures/Blover/normal/frame_", FRAME_COUNT);
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
