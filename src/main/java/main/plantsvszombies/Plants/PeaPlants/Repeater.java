package main.plantsvszombies.Plants.PeaPlants;

import javafx.scene.image.Image;
import main.plantsvszombies.Enums.BulletType;
import main.plantsvszombies.Game.Tools.ImageFactory;
import main.plantsvszombies.Items.Bullet;

public class Repeater extends PeaPlant {

    private static final int NORMAL_FRAME_COUNT = 60;
    private static final int SHOOT_FRAME_COUNT = 70;
    private static final Image[] SHOOT_FRAMES;
    private static final Image[] NORMAL_FRAMES;

    static {
        NORMAL_FRAMES = ImageFactory.arrayImage("plantPictures/Repeater/normal/frame_", NORMAL_FRAME_COUNT);
        SHOOT_FRAMES = ImageFactory.arrayImage("plantPictures/Repeater/shoot/frame_", SHOOT_FRAME_COUNT);
    }

    public Repeater(int row, int col) {
        super(row, col);
        price = 200;
        HP = 100;
        rechargeTime = 10;
        bulletType = BulletType.NORMAL_BULLET;
        frameUpdateTime = 20;
    }

    @Override
    public Bullet action() {
        return (nowPic == 30 || nowPic == 40) ? shoot() : null;
    }

    @Override
    protected Image[] getImage() {
        return isShooting ? SHOOT_FRAMES : NORMAL_FRAMES;
    }
}
