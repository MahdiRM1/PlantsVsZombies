package main.plantsvszombies;

import javafx.scene.image.Image;

public class BucketheadZombie extends Zombie{

    private final static int WALK_FRAME_COUNT = 47;
    private final static int ATTACK_FRAME_COUNT = 40;
    private final static int DIE_FRANE_COUNT = 39;
    private final static Image[] WALK_FRAMES;
    private final static Image[] ATTACK_FRAMES;
    private final static Image[] DIE_FRAMES;

    static {
        WALK_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/BucketheadZombie/Walk/frame_", WALK_FRAME_COUNT);
        ATTACK_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/BucketheadZombie/Attack/frame_", ATTACK_FRAME_COUNT);
        DIE_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/OriginalZombie/Die/frame_", DIE_FRANE_COUNT);
    }

    public BucketheadZombie(ZombieData data){
        super(data);
        speed = 5;
    }

    public BucketheadZombie(int row, int col){
        super(row, col);
        HP = 240;
        speed = 5;
    }

    @Override
    protected Image[] getWalkImage() {
        return WALK_FRAMES;
    }

    @Override
    protected Image[] getEatImage() {
        return ATTACK_FRAMES;
    }

    @Override
    protected Image[] getDieImage(){
        return DIE_FRAMES;
    }

}
