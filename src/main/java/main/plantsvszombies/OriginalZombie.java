package main.plantsvszombies;

import javafx.scene.image.Image;

public class OriginalZombie extends Zombie {

    private final static int WALK_FRAME_COUNT = 47;
    private final static int ATTACK_FRAME_COUNT = 40;
    private final static int DIE_FRAME_COUNT = 39;
    private final static int BOOM_DIE_FRAME_COUNT = 37;
    private final static Image[] WALK_FRAMES;
    private final static Image[] ATTACK_FRAMES;
    private final static Image[] DIE_FRAMES;
    private final static Image[] BOOM_DIE_FRAMES;

    static {
        WALK_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/OriginalZombie/Walk/frame_", WALK_FRAME_COUNT);
        ATTACK_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/OriginalZombie/Attack/frame_", ATTACK_FRAME_COUNT);
        DIE_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/OriginalZombie/Die/frame_", DIE_FRAME_COUNT);
        BOOM_DIE_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/BoomDie/frame_", BOOM_DIE_FRAME_COUNT);
    }

    public OriginalZombie(ZombieData data) {
        super(data);
        HP = 100;
        speed = 5;
    }

    public OriginalZombie(int row, int col) {
        super(row, col);
        HP = 100;
        speed = 5;
    }

    public static Image[] getimages(ZombieState state) {
        return switch (state) {
            case WALKING ->
                WALK_FRAMES;
            case EATING ->
                ATTACK_FRAMES;
            case DIE ->
                DIE_FRAMES;
            case BOOM_DIE ->
                BOOM_DIE_FRAMES;
            default ->
                null;
        };
    }

    @Override
    protected Image[] getZombiePictures(){
        return OriginalZombie.getimages(state);
    }

    @Override
    protected Image[] getPictures() {
        return HEAD_FRAMES;
    }
}
