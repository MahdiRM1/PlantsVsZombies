package main.plantsvszombies;

import javafx.scene.image.Image;

public class PeaShooter extends PeaPlant{

    private static final int FRAME_COUNT = 60;
    private static final Image[] SHOOT_FRAMES;
    private static final Image[] NORMAL_FRAMES;

    static {
        NORMAL_FRAMES = Constants.getArrayImage("Pictures/plantsGifs/PeaShooter/normal/frame_", FRAME_COUNT);
        SHOOT_FRAMES = Constants.getArrayImage("Pictures/plantsGifs/PeaShooter/shoot/frame_", FRAME_COUNT);
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
        return isShooting ? SHOOT_FRAMES : NORMAL_FRAMES;
    }
}
