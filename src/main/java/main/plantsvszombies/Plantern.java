package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.List;


public class Plantern extends Plant{

    private static final int imagesNum = 20;
    private static final Image[] normalImages;

    static {
        normalImages = Constants.getArrayImage("Pictures/plantsGifs/Plantern/normal/frame_", imagesNum);
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
        return normalImages;
    }
}
