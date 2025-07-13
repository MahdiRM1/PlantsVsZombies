package main.plantsvszombies;

import javafx.scene.image.Image;

public class Imp extends Zombie{

    private final static int walkPictureNum = 33;
    private final static int attackPictureNum = 27;
    private final static int diePicturesNum = 22;
    private final static Image[] walkZombie;
    private final static Image[] attackZombie;
    private final static Image[] dieZombie;

    static{
        walkZombie = Constants.getArrayImage("Pictures/ZombiePicture/Imp/Walk/frame_", walkPictureNum);
        attackZombie = Constants.getArrayImage("Pictures/ZombiePicture/Imp/Attack/frame_", attackPictureNum);
        dieZombie = Constants.getArrayImage("Pictures/ZombiePicture/Imp/Die/frame_", diePicturesNum);
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
