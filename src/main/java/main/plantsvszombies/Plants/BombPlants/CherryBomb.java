package main.plantsvszombies.Plants.BombPlants;

import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import main.plantsvszombies.Enums.ZombieState;
import main.plantsvszombies.Game.Constants;
import main.plantsvszombies.Zombies.Zombie;

public class CherryBomb extends BombPlant {

    private static final Image[] NORMAL_FRAMES;
    private static final Image[] EXPLOSION_FRAMES;
    private static final int NORMAL_FRAME_COUNT = 50;
    private static final int EXPLOSION_FRAME_COUNT = 13;
    private static final AudioClip sound;

    static {
        NORMAL_FRAMES = Constants.getArrayImage("Pictures/plantPictures/CherryBomb/normal/frame_", NORMAL_FRAME_COUNT);
        EXPLOSION_FRAMES = Constants.getArrayImage("Pictures/plantPictures/CherryBomb/boom/frame_", EXPLOSION_FRAME_COUNT);
        sound = Constants.setSound("cherrybomb", false);
    }

    public CherryBomb(int row, int col) {
        super(row, col);
        price = 150;
        HP = 100;
        rechargeTime = 15;
        frameUpdateTime = 20;
    }

    @Override
    protected Image[] getImage() {
        return isExploded ? EXPLOSION_FRAMES : NORMAL_FRAMES;
    }

    @Override
    public void action(List<Zombie> zombies) {
        sound.play();
        frameUpdateTime = 40;
        picture.setImage(EXPLOSION_FRAMES[0]);
        Constants.changeScale(picture, 2.5);
        for (Zombie z : zombies)
            if (Math.abs(z.getRow() - row) <= 1 && Math.abs(z.getCol() - col) <= 1 && z.alive() && !z.isHypnotized())
                z.setState(ZombieState.BOOM_DIE);
    }
}