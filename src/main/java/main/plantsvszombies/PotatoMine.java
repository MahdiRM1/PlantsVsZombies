package main.plantsvszombies;

import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

enum MineState {NOT_READY, IS_READY, READY, EXPLODING, EXPLODED}

public class PotatoMine extends BombPlant {

    private static final Image[] NORMAL_FRAMES;
    private static final Image[] ATTACK_FRAMES;
    private static final Image[] READY_FRAMES;
    private static final int FRAME_COUNT = 30;
    private static final int ATTACK_FRAME_COUNT = 21;
    private static final int READY_FRAME_COUNT = 16;
    private static final AudioClip sound;
    private long explosionTime;
    private MineState state;

    static {
        NORMAL_FRAMES = Constants.getArrayImage("Pictures/plantPictures/PotatoMine/normal/frame_", FRAME_COUNT);
        ATTACK_FRAMES = Constants.getArrayImage("Pictures/plantPictures/PotatoMine/attack/frame_", ATTACK_FRAME_COUNT);
        READY_FRAMES = Constants.getArrayImage("Pictures/plantPictures/PotatoMine/ready/frame_", READY_FRAME_COUNT);
        sound = Constants.setSound("potato_mine", false);
    }

    public PotatoMine(int row, int col) {
        super(row, col);
        price = 25;
        HP = 100;
        rechargeTime = 20;
        state = MineState.NOT_READY;
        picture.setImage(READY_FRAMES[0]);
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        if (Math.abs(GlobalState.gameTime - timeCreated) < 10_000) return false;
        else if (Math.abs(GlobalState.gameTime - timeCreated) == 10_000) setState(MineState.IS_READY);
        else if (state == MineState.IS_READY && nowPic == getImage().length - 1) setState(MineState.READY);
        else if (state == MineState.READY && zombieCollision(zombies)) setState(MineState.EXPLODING);
        else if (state == MineState.EXPLODING && nowPic == getImage().length - 1) {
            setState(MineState.EXPLODED);
            return true;
        }
        else if (state == MineState.EXPLODED) {
            if (GlobalState.gameTime - explosionTime > 1000) HP = 0;
            return false;
        }
        updateFrame();
        return false;
    }

    private boolean zombieCollision(List<Zombie> zombies){
        for (Zombie z : zombies)
            if (Constants.checkCollision(layoutX(), z.layoutX(), row, z.getRow()) && !z.isHypnotized())
                return true;
        return false;
    }

    private void setState(MineState state){
        this.state = state;
        nowPic = 0;
    }

    @Override
    protected Image[] getImage() {
        return switch (state){
            case NOT_READY, IS_READY -> READY_FRAMES;
            case READY -> NORMAL_FRAMES;
            default -> ATTACK_FRAMES;
        };
    }

    @Override
    public void action(List<Zombie> zombies) {
        sound.play();
        Constants.changeScale(picture, 1.5);
        explosionTime = GlobalState.gameTime;
        for (Zombie z : zombies)
            if (row == z.getRow() && col == z.getCol() && !z.isHypnotized())
                z.setState(ZombieState.DEAD);
    }
}