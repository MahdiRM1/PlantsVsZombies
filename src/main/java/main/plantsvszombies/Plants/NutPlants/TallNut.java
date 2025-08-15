package main.plantsvszombies.Plants.NutPlants;

import javafx.scene.image.Image;
import main.plantsvszombies.Game.Tools.ImageFactory;

public class TallNut extends NutPlant {

    private static final int FRAME_COUNT = 17;
    private static final Image[] FULL_LIFE_FRAMES;
    private static final Image[] HALF_LIFE_FRAMES;
    private static final Image[] END_LIFE_FRAMES;

    static {
        FULL_LIFE_FRAMES = ImageFactory.arrayImage("plantPictures/TallNut/normal/frame_", FRAME_COUNT);
        HALF_LIFE_FRAMES = ImageFactory.arrayImage("plantPictures/TallNut/HalfLife/frame_", FRAME_COUNT);
        END_LIFE_FRAMES = ImageFactory.arrayImage("plantPictures/TallNut/EndLife/frame_", FRAME_COUNT);
    }

    public TallNut(int row, int col) {
        super(row, col);
        price = 125;
        HP = maxHP = 500;
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
