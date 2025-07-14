package main.plantsvszombies;

import java.util.List;

import javafx.scene.image.Image;

public class Plantern extends Plant {

    private static final int FRAME_COUNT = 20;
    private static final Image[] FRAMES;

    static {
        FRAMES = Constants.getArrayImage("Pictures/plantsGifs/Plantern/normal/frame_", FRAME_COUNT);
    }

    public Plantern(int row, int col) {
        super(row, col);
        price = 25;
        HP = 100;
        rechargeTime = 10;
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        updateFrame();
        return true;
    }

    public void action(Fog fog) {
        fog.clearFog(row, col);
    }

    @Override
    public Image[] getImage() {
        return FRAMES;
    }
}
