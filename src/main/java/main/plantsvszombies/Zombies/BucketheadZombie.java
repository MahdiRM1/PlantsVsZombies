package main.plantsvszombies.Zombies;

import javafx.scene.image.Image;
import main.plantsvszombies.Enums.ZombieState;
import main.plantsvszombies.Game.Constants;
import main.plantsvszombies.GameState.ZombieData;

public class BucketheadZombie extends Zombie {

    private final static int WALK_FRAME_COUNT = 47;
    private final static int ATTACK_FRAME_COUNT = 40;
    private static final int BOOM_DIE_FRAME_COUNT = 37;
    private static final int BUCKET_FRAME_COUNT = 14;
    private final static Image[] WALK_FRAMES;
    private final static Image[] ATTACK_FRAMES;
    private static final Image[] BOOM_DIE_FRAMES;
    private static final Image[] BUCKET_FRAMES;


    static {
        WALK_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/BucketheadZombie/Walk/frame_", WALK_FRAME_COUNT);
        ATTACK_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/BucketheadZombie/Attack/frame_", ATTACK_FRAME_COUNT);
        BUCKET_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/BucketheadZombie/bucket/frame_", BUCKET_FRAME_COUNT);
        BOOM_DIE_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/BoomDie/frame_", BOOM_DIE_FRAME_COUNT);
    }

    public BucketheadZombie(ZombieData data) {
        super(data);
        speed = 5;
    }

    public BucketheadZombie(int row, int col) {
        super(row, col);
        HP = 360;
        speed = 5;
    }

    @Override
    protected Image[] getZombiePictures(){
        if (HP <= 100) return OriginalZombie.getimages(state);
        return switch (state){
            case WALKING -> WALK_FRAMES;
            case EATING -> ATTACK_FRAMES;
            case BOOM_DIE -> BOOM_DIE_FRAMES;
            default -> null;
        };
    }

    @Override
    protected Image[] getPictures() {
        if (state == ZombieState.DIE) return HEAD_FRAMES;
        return BUCKET_FRAMES;
    }

}
