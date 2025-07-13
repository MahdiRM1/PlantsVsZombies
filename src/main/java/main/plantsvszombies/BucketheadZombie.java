package main.plantsvszombies;

import javafx.scene.image.Image;

public class BucketheadZombie extends Zombie{

    private final static int walkPictureNum = 47;
    private final static int attackPictureNum = 40;
    private final static int diePictureNum = 39;
    private final static Image[] walkZombie;
    private final static Image[] attackZombie;
    private final static Image[] dieZombie;

    static {
        walkZombie = Constants.getArrayImage("Pictures/ZombiePicture/BucketheadZombie/Walk/frame_", walkPictureNum);
        attackZombie = Constants.getArrayImage("Pictures/ZombiePicture/BucketheadZombie/Attack/frame_", attackPictureNum);
        dieZombie = Constants.getArrayImage("Pictures/ZombiePicture/OriginalZombie/Die/frame_", diePictureNum);
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
        return walkZombie;
    }

    @Override
    protected Image[] getEatImage() {
        return attackZombie;
    }

    @Override
    protected Image[] getDieImage(){
        return dieZombie;
    }

}
