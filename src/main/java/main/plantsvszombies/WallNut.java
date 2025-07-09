package main.plantsvszombies;

import javafx.scene.image.Image;

public class WallNut extends NutPlant {

    private static final int imagesNum = 17;
    private static final Image[] fullLife;
    private static final Image[] halfLife;
    private static final Image[] endLife;


    static {
        fullLife = Constants.getArrayImage("Pictures/plantsGifs/WallNut/FullLife/frame_", imagesNum);
        halfLife = Constants.getArrayImage("Pictures/plantsGifs/WallNut/HalfLife/frame_", imagesNum);
        endLife = Constants.getArrayImage("Pictures/plantsGifs/WallNut/EndLife/frame_", imagesNum);
    }

    public WallNut(int row, int col){
        super(row, col);
        price = 50;
        HP = maxHP =250;
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
