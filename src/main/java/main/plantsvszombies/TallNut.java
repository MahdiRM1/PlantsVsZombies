package main.plantsvszombies;

import javafx.scene.image.Image;

public class TallNut extends NutPlant {

    private static final int imagesNum = 17;
    private static final Image[] fullLife;
    private static final Image[] halfLife;
    private static final Image[] endLife;


    static {
        fullLife = Constants.getArrayImage("Pictures/plantsGifs/TallNut/FullLife/frame_", imagesNum);
        halfLife = Constants.getArrayImage("Pictures/plantsGifs/TallNut/HalfLife/frame_", imagesNum);
        endLife = Constants.getArrayImage("Pictures/plantsGifs/TallNut/EndLife/frame_", imagesNum);
    }

    public TallNut(int row, int col){
        super(row, col);
        price = 125;
        HP = maxHP = 400;
        rechargeTime = 20;
    }

    @Override
    protected Image[] getImage() {
        return switch (state){
            case FULL_LIFE -> fullLife;
            case HALF_LIFE -> halfLife;
            case END_LIFE -> endLife;
        };
    }
}
