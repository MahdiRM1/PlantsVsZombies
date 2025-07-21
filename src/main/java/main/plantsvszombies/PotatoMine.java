package main.plantsvszombies;

import java.util.List;

import javafx.scene.image.Image;

public class PotatoMine extends BombPlant {

    private static final Image[] NORMAL_FRAMES;
    private static final Image EXPLOSION_FRAME;
    private static final Image NOT_READY_FRAME;
    private static final int FRAME_COUNT = 11;
    private long explosionTime;

    static {
        NORMAL_FRAMES = Constants.getArrayImage("Pictures/plantPictures/PotatoMine/normal/frame_", FRAME_COUNT);
        EXPLOSION_FRAME = new Image("file:Pictures/plantPictures/PotatoMine/PotatoMineExploded.png");
        NOT_READY_FRAME = new Image("file:Pictures/plantPictures/PotatoMine/PotatoMineNotReady.png");
    }

    public PotatoMine(int row, int col) {
        super(row, col);
        price = 25;
        HP = 100;
        rechargeTime = 20;
        picture.setImage(NOT_READY_FRAME);
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        if (Math.abs(GlobalState.gameTime - timeCreated) < 10_000) return false;
        else if (explosionTime > 0) {
            if (Math.abs(GlobalState.gameTime - explosionTime) > 1000) HP = 0;
        }
        else{
            updateFrame();
            for (Zombie z : zombies)
                if (Constants.checkCollision(layoutX(), z.layoutX(), row, z.getRow()) && !z.isHypnotized()) return true;
        }
        return false;
    }

    @Override
    protected Image[] getImage() {
        return NORMAL_FRAMES;
    }

    @Override
    public void action(List<Zombie> zombies) {
        picture.setImage(EXPLOSION_FRAME);
        Constants.changeScale(picture, 1.5);
        explosionTime = GlobalState.gameTime;
        for (Zombie z : zombies)
            if (row == z.getRow() && col == z.getCol() && !z.isHypnotized())
                z.setState(ZombieState.DEAD);
    }
}