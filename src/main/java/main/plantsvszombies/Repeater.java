package main.plantsvszombies;

import javafx.scene.image.Image;

public class Repeater extends PeaPlant{

    private static final int normalImageNum = 60;
    private static final int shootImageNum = 70;
    private static final Image[] shootImage;
    private static final Image[] normalImage;

    static {
        normalImage = Constants.getArrayImage("Pictures/plantsGifs/Repeater/normal/frame_", normalImageNum);
        shootImage = Constants.getArrayImage("Pictures/plantsGifs/Repeater/shoot/frame_", shootImageNum);
    }


    public Repeater(int row, int col){
        super(row, col);
        price = 200;
        HP = 100;
        rechargeTime = 10;
        bulletType = BulletType.NORMAL_BULLET;
    }

    @Override
    public Bullet shoot(int row, int col) {
        if (GlobalState.gameTime % 20 != 0) return null;
        if(nowPic == 30 || nowPic == 40) {
            lastShoot = GlobalState.gameTime;
            return new Bullet(row, col, bulletType);
        }
        return null;
    }

    @Override
    protected Image[] getImage(boolean isShooting) {
        return isShooting ? shootImage : normalImage;
    }
}