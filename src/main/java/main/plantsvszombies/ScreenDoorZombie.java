package main.plantsvszombies;

import javafx.scene.image.Image;

public class ScreenDoorZombie extends Zombie {

    private final static int WALK_FRAME_COUNT = 47;
    private final static int ATTACK_FRAME_COUNT = 40;
    private final static int DIE_FRAME_COUNT = 39;
    private final static int BOOM_DIE_FRAME_COUNT = 39;
    private final static Image[] WALK_FRAMES;
    private final static Image[] ATTACK_FRAMES;
    private final static Image[] DIE_FRAMES;
    private final static Image[] BOOM_DIE_FRAMES;

    static {
        WALK_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/ScreenDoorZombie/Walk/frame_", WALK_FRAME_COUNT);
        ATTACK_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/ScreenDoorZombie/Attack/frame_", ATTACK_FRAME_COUNT);
        DIE_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/OriginalZombie/Die/frame_", DIE_FRAME_COUNT);
        BOOM_DIE_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/BoomDie/frame_", BOOM_DIE_FRAME_COUNT);
    }

    public ScreenDoorZombie(ZombieData data) {
        super(data);
        speed = 5;
    }

    public ScreenDoorZombie(int row, int col) {
        super(row, col);
        HP = 200;
        speed = 5;
    }

    @Override
    public void damage(BulletType bulletType) {
        HP -= 20;
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
