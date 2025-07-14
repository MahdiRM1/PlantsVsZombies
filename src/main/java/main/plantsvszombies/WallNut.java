package main.plantsvszombies;

import javafx.scene.image.Image;

public class WallNut extends NutPlant {

    private static final int FRAME_COUNT = 17;
    private static final Image[] FULL_LIFE;
    private static final Image[] HALF_LIFE;
    private static final Image[] END_LIFE;

    static {
        FULL_LIFE = Constants.getArrayImage("Pictures/plantsGifs/WallNut/FullLife/frame_", FRAME_COUNT);
        HALF_LIFE = Constants.getArrayImage("Pictures/plantsGifs/WallNut/HalfLife/frame_", FRAME_COUNT);
        END_LIFE = Constants.getArrayImage("Pictures/plantsGifs/WallNut/EndLife/frame_", FRAME_COUNT);
    }

    public WallNut(int row, int col) {
        super(row, col);
        price = 50;
        HP = maxHP = 250;
        rechargeTime = 20;
    }

    @Override
    protected Image[] getImage() {
        return switch (state) {
            case FULL_LIFE ->
                FULL_LIFE;
            case HALF_LIFE ->
                HALF_LIFE;
            case END_LIFE ->
                END_LIFE;
        };
    }
}
