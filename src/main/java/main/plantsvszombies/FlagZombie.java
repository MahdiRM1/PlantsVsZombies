package main.plantsvszombies;

import javafx.scene.image.Image;

public class FlagZombie extends Zombie{

    private final static int WALK_FRAME_COUNT = 47;
    private final static int ATTACK_FRAME_COUNT = 40;
    private final static int DIE_FRAME_COUNT = 39;
    private final static int BOOM_DIE_FRAME_COUNT = 37;
    private final static Image[] WALK_FRAMES;
    private final static Image[] ATTACK_FRAMES;
    private final static Image[] DIE_FRAMES;
    private final static Image[] BOOM_DIE_FRAMES;

    static{
        WALK_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/FlagZombie/Walk/frame_", WALK_FRAME_COUNT);
        ATTACK_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/FlagZombie/Attack/frame_", ATTACK_FRAME_COUNT);
        DIE_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/OriginalZombie/Die/frame_", DIE_FRAME_COUNT);
        BOOM_DIE_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/BoomDie/frame_", BOOM_DIE_FRAME_COUNT);
    }

    public FlagZombie(ZombieData data){
        super(data);
        speed = 5;
        HP = 100;
    }

    public FlagZombie(int row, int col){
        super(row, col);
        speed = 5;
        HP = 100;
    }

    @Override
    protected Image[] getImages(){
        return switch (state){
            case WALKING -> WALK_FRAMES;
            case EATING -> ATTACK_FRAMES;
            case DIE -> DIE_FRAMES;
            case BOOM_DIE -> BOOM_DIE_FRAMES;
            default -> null;
        };
    }
}
