package main.plantsvszombies;

import javafx.scene.image.Image;

public class Imp extends Zombie{

    private final static int WALK_FRAME_COUNT = 33;
    private final static int ATTACK_FRAME_COUNT = 27;
    private final static int DIE_FRAME_COUNT = 22;
    private final static Image[] WALK_FRAMES;
    private final static Image[] ATTACK_FRAMES;
    private final static Image[] DIE_FRAMES;

    static{
        WALK_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/Imp/Walk/frame_", WALK_FRAME_COUNT);
        ATTACK_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/Imp/Attack/frame_", ATTACK_FRAME_COUNT);
        DIE_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/Imp/Die/frame_", DIE_FRAME_COUNT);
    }

    public Imp (ZombieData data) {
        super(data);
        HP = 50;
        speed = 3;
    }

    public Imp(int row, int col){
        super(row, col);
        HP = 50;
        speed = 3;
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
