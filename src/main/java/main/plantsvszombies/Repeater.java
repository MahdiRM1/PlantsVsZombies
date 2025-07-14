package main.plantsvszombies;

import javafx.scene.image.Image;

public class Repeater extends PeaPlant{

    private static final int NORMAL_FRAME_COUNT = 60;
    private static final int SHOOT_FRAME_COUNT = 70;
    private static final Image[] SHOOT_FRAMES;
    private static final Image[] NORMAL_FRAMES;

    static {
        NORMAL_FRAMES = Constants.getArrayImage("Pictures/plantsGifs/Repeater/normal/frame_", NORMAL_FRAME_COUNT);
        SHOOT_FRAMES = Constants.getArrayImage("Pictures/plantsGifs/Repeater/shoot/frame_", SHOOT_FRAME_COUNT);
    }


    public Repeater(int row, int col){
        super(row, col);
        price = 200;
        HP = 100;
        rechargeTime = 10;
        bulletType = BulletType.NORMAL_BULLET;
        frameUpdateTime = 20;
    }

    @Override
    public Bullet action() {
        if(nowPic == 30 || nowPic == 40) return shoot();
        return null;
    }

    @Override
    protected Image[] getImage() {
        return isShooting ? SHOOT_FRAMES : NORMAL_FRAMES;
    }
}