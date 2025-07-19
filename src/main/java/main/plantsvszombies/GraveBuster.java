package main.plantsvszombies;

import java.util.List;

import javafx.scene.image.Image;

public class GraveBuster extends Plant {

    private final Grave grave;
    private static final Image[] FRAMES;
    private static final int FRAME_COUNT = 28;

    static {
        FRAMES = Constants.getArrayImage("Pictures/plantPictures/GraveBuster/normal/frame_", FRAME_COUNT);
    }

    public GraveBuster(int row, int col, Grave grave) {
        super(row, col);
        price = 75;
        HP = 100;
        rechargeTime = 10;
        this.grave = grave;
        Constants.changeScale(picture, 1.3);
        picture.setLayoutY(picture.getLayoutY() - Constants.TILE_SIZE / 2);
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        updateFrame();
        if (Math.abs(GlobalState.gameTime - timeCreated) < 2000)
            picture.setLayoutY(picture.getLayoutY() + Constants.TILE_SIZE / 200);
        else if (Math.abs(GlobalState.gameTime - timeCreated) == 2000) {
            HP = 0;
            return true;
        }
        return false;
    }

    public Grave action() {
        return grave;
    }

    @Override
    protected Image[] getImage() {
        return FRAMES;
    }
}
