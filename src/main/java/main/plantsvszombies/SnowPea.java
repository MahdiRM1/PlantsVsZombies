package main.plantsvszombies;

import javafx.scene.image.Image;

public class SnowPea extends PeaPlant{

    private static final int imageNum = 60;
    private static final Image[] shootImage;
    private static final Image[] normalImage;

    static {
        normalImage = Constants.getArrayImage("Pictures/plantsGifs/SnowPea/normal/frame_", imageNum);
        shootImage = Constants.getArrayImage("Pictures/plantsGifs/SnowPea/shoot/frame_", imageNum);
    }

    public SnowPea(int row, int col){
        super(row, col);
        price = 175;
        HP = 100;
        rechargeTime = 10;
        bulletType = BulletType.ICE_BULLET;;
    }

    @Override
    protected Image[] getImage(boolean isShooting) {
        return isShooting ? shootImage : normalImage;
    }
}
