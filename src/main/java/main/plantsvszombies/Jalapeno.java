package main.plantsvszombies;

import java.util.List;

import javafx.scene.image.Image;

public class Jalapeno extends BombPlant {

    private static final Image[] NORMAL_FRAMES;
    private static final Image[] EXPLOSION_FRAMES;
    private static final int FRAME_COUNT = 13;

    static {
        NORMAL_FRAMES = Constants.getArrayImage("Pictures/plantPictures/Jalapeno/normal/frame_", FRAME_COUNT);
        EXPLOSION_FRAMES = Constants.getArrayImage("Pictures/plantPictures/Jalapeno/attack/frame_", FRAME_COUNT);
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
        picture.setImage(EXPLOSION_FRAMES[0]);
        Constants.sizeNode(picture, Constants.TILE_SIZE * 9, picture.getFitHeight());
        Constants.positionNode(picture, Constants.SCREEN_WIDTH / 4.9, picture.getLayoutY());
        for (Zombie z : zombies) {
            if (z.getRow() == row && z.getState() != ZombieState.HYPNOTIZED) z.setState(ZombieState.BOOM_DIE);
        }
    }
}
