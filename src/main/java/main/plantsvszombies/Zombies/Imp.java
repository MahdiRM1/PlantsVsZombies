package main.plantsvszombies.Zombies;

import javafx.scene.image.Image;
import main.plantsvszombies.Game.Constants;
import main.plantsvszombies.GameState.ZombieData;

public class Imp extends Zombie {

    private final static int WALK_FRAME_COUNT = 33;
    private final static int ATTACK_FRAME_COUNT = 27;
    private final static int DIE_FRAME_COUNT = 22;
    private final static int BOOM_DIE_FRAME_COUNT = 37;
    private final static Image[] WALK_FRAMES;
    private final static Image[] ATTACK_FRAMES;
    private final static Image[] DIE_FRAMES;
    private final static Image[] BOOM_DIE_FRAMES;

    static {
        WALK_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/Imp/Walk/frame_", WALK_FRAME_COUNT);
        ATTACK_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/Imp/Attack/frame_", ATTACK_FRAME_COUNT);
        DIE_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/Imp/Die/frame_", DIE_FRAME_COUNT);
        BOOM_DIE_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/BoomDie/frame_", BOOM_DIE_FRAME_COUNT);
    }

    public Imp(ZombieData data) {
        super(data);
        HP = 50;
        speed = 3;
    }

    public Imp(int row, int col) {
        super(row, col);
        HP = 50;
        speed = 3;
    }

    @Override
    protected Image[] getZombiePictures(){
        return switch (state){
            case WALKING -> WALK_FRAMES;
            case EATING -> ATTACK_FRAMES;
            case DIE -> DIE_FRAMES;
            case BOOM_DIE -> BOOM_DIE_FRAMES;
            default -> null;
        };
    }

    @Override
    protected Image[] getPictures() {
        return null;
    }
}
