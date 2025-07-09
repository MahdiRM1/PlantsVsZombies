package main.plantsvszombies;

import javafx.scene.image.Image;

public class PeaShooter extends PeaPlant{

    private static final int imageNum = 60;
    private static final Image[] shootImage;
    private static final Image[] normalImage;

    static {
        normalImage = Constants.getArrayImage("Pictures/plantsGifs/PeaShooter/normal/frame_", imageNum);
        shootImage = Constants.getArrayImage("Pictures/plantsGifs/PeaShooter/shoot/frame_", imageNum);
    }

    public PeaShooter(int row, int col){
       super(row, col);
       price = 100;
       HP = 100;
       rechargeTime = 10;
       bulletType = BulletType.NORMAL_BULLET;
       frameUpdateTime = 20;
    }

    @Override
    protected Image[] getImage() {
        return isShooting ? shootImage : normalImage;
    }
}
