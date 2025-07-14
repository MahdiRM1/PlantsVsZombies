package main.plantsvszombies;

import java.util.List;

import javafx.scene.image.Image;

public class CherryBomb extends BombPlant {

    private static final Image[] NORMAL_FRAMES;
    private static final Image[] EXPLOSION_FRAMES;
    private static final int NORMAL_FRAME_COUNT = 14;
    private static final int EXPLOSION_FRAME_COUNT = 13;

    static {
        NORMAL_FRAMES = Constants.getArrayImage("Pictures/plantsGifs/CherryBomb/normal/frame_", NORMAL_FRAME_COUNT);
        EXPLOSION_FRAMES = Constants.getArrayImage("Pictures/plantsGifs/CherryBomb/boom/frame_", EXPLOSION_FRAME_COUNT);
    }

    public CherryBomb(int row, int col) {
        super(row, col);
        price = 150;
        HP = 100;
        rechargeTime = 15;
    }

    @Override
    protected Image[] getImage() {
        return isExploded ? EXPLOSION_FRAMES : NORMAL_FRAMES;
    }

    @Override
    public void action(List<Zombie> zombies) {
        gif.setImage(EXPLOSION_FRAMES[0]);
        Constants.changeScale(gif, 2.5);
        for (Zombie z : zombies)
            if (Math.abs(z.getRow() - row) <= 1 && Math.abs(z.getCol() - col) <= 1) z.setState(ZombieState.BOOM_DIE);
    }
}
