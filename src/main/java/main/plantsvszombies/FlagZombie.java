package main.plantsvszombies;

import javafx.scene.image.Image;

public class FlagZombie extends OriginalZombie{

    private final static int walkPictureNum = 47;
    private final static int attackPictureNum = 40;
    private final static int diePicturesNum = 39;
    private final static Image[] walkZombie;
    private final static Image[] attackZombie;
    private final static Image[] dieZombie;

    static{
        walkZombie = Constants.getArrayImage("Pictures/ZombiePicture/FlagZombie/Walk/frame_", walkPictureNum);
        attackZombie = Constants.getArrayImage("Pictures/ZombiePicture/FlagZombie/Attack/frame_", attackPictureNum);
        dieZombie = Constants.getArrayImage("Pictures/ZombiePicture/OriginalZombie/Die/frame_", diePicturesNum);
    }

    public FlagZombie(ZombieData data){
        super(data);
    }

    public  FlagZombie(int row){
        super(row);
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
