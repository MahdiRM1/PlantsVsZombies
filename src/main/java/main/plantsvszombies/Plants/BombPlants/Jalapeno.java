package main.plantsvszombies.Plants.BombPlants;

import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import main.plantsvszombies.Enums.ZombieState;
import main.plantsvszombies.Game.Constants;
import main.plantsvszombies.Zombies.Zombie;

public class Jalapeno extends BombPlant {

    private static final Image[] NORMAL_FRAMES;
    private static final Image[] EXPLOSION_FRAMES;
    private static final int FRAME_COUNT = 13;
    private static final AudioClip sound;

    static {
        NORMAL_FRAMES = Constants.getArrayImage("Pictures/plantPictures/Jalapeno/normal/frame_", FRAME_COUNT);
        EXPLOSION_FRAMES = Constants.getArrayImage("Pictures/plantPictures/Jalapeno/attack/frame_", FRAME_COUNT);
        sound = Constants.setSound("jalapeno", false);
    }

    public Jalapeno(int row, int col) {
        super(row, col);
        price = 125;
        HP = 100;
        rechargeTime = 15;
    }

    @Override
    protected Image[] getImage() {
        return isExploded ? EXPLOSION_FRAMES : NORMAL_FRAMES;
    }

    @Override
    public void action(List<Zombie> zombies) {
        sound.play();
        picture.setImage(EXPLOSION_FRAMES[0]);
        Constants.sizeNode(picture, Constants.TILE_SIZE * 9, picture.getFitHeight());
        Constants.positionNode(picture, Constants.SCREEN_WIDTH / 4.9, picture.getLayoutY());
        for (Zombie z : zombies)
            if (z.getRow() == row && z.alive() && !z.isHypnotized()) z.setState(ZombieState.BOOM_DIE);
    }
}
