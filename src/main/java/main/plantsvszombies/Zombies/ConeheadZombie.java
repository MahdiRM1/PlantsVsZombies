package main.plantsvszombies.Zombies;

import javafx.scene.image.Image;
import main.plantsvszombies.Enums.ZombieState;
import main.plantsvszombies.Game.Tools.ImageFactory;
import main.plantsvszombies.GameState.ZombieData;

public class ConeheadZombie extends Zombie {

    private final static int WALK_FRAME_COUNT = 47;
    private final static int ATTACK_FRAME_COUNT = 40;
    private static final int BOOM_DIE_FRAME_COUNT = 37;
    private static final int CONE_FRAME_COUNT = 14;
    private final static Image[] WALK_FRAMES;
    private final static Image[] BOOM_DIE_FRAMES;
    private final static Image[] ATTACK_FRAMES;
    private final static Image[] CONE_FRAMES;

    static {
        WALK_FRAMES = ImageFactory.arrayImage("Pictures/ZombiePicture/ConeheadZombie/Walk/frame_", WALK_FRAME_COUNT);
        ATTACK_FRAMES = ImageFactory.arrayImage("Pictures/ZombiePicture/ConeheadZombie/Attack/frame_", ATTACK_FRAME_COUNT);
        CONE_FRAMES = ImageFactory.arrayImage("Pictures/ZombiePicture/ConeheadZombie/cone/frame_", CONE_FRAME_COUNT);
        BOOM_DIE_FRAMES = ImageFactory.arrayImage("Pictures/ZombiePicture/BoomDie/frame_", BOOM_DIE_FRAME_COUNT);
    }

    public ConeheadZombie(ZombieData data) {
        super(data);
        HP = 200;
        speed = 5;
    }

    public ConeheadZombie(int row, int col) {
        super(row, col);
        HP = 140;
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
        return CONE_FRAMES;
    }
}
