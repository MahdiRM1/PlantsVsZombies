package main.plantsvszombies.Plants.NutPlants;

import javafx.scene.image.Image;
import main.plantsvszombies.Game.Constants;

public class WallNut extends NutPlant {

    private static final int FRAME_COUNT = 17;
    private static final Image[] FULL_LIFE_FRAMES;
    private static final Image[] HALF_LIFE_FRAMES;
    private static final Image[] END_LIFE_FRAMES;

    static {
        FULL_LIFE_FRAMES = Constants.getArrayImage("Pictures/plantPictures/WallNut/FullLife/frame_", FRAME_COUNT);
        HALF_LIFE_FRAMES = Constants.getArrayImage("Pictures/plantPictures/WallNut/HalfLife/frame_", FRAME_COUNT);
        END_LIFE_FRAMES = Constants.getArrayImage("Pictures/plantPictures/WallNut/EndLife/frame_", FRAME_COUNT);
    }

    public WallNut(int row, int col) {
        super(row, col);
        price = 50;
        HP = maxHP = 300;
        rechargeTime = 20;
    }

    @Override
    protected Image[] getImage() {
        return switch (state) {
            case FULL_LIFE -> FULL_LIFE_FRAMES;
            case HALF_LIFE -> HALF_LIFE_FRAMES;
            case END_LIFE -> END_LIFE_FRAMES;
        };
    }
}
