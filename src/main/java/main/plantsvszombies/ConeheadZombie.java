package main.plantsvszombies;

import javafx.scene.image.Image;

public class ConeheadZombie extends Zombie{

    private final static int walkPictureNum = 47;
    private final static int attackPictureNum = 40;
    private final static int diePictureNum = 39;
    private final static Image[] walkZombie;
    private final static Image[] attackZombie;
    private final static Image[] dieZombie;

    static {
        walkZombie = Constants.getArrayImage("Pictures/ZombiePicture/ConeheadZombie/Zombie/frame_", walkPictureNum);
        attackZombie = Constants.getArrayImage("Pictures/ZombiePicture/ConeheadZombie/ZombieAttack/frame_", attackPictureNum);
        dieZombie = Constants.getArrayImage("Pictures/ZombiePicture/OriginalZombie/ZombieDie/frame_", diePictureNum);
    }

    public ConeheadZombie (ZombieData data) {
        super(data);
        HP = 140;
        speed = 5;
    }

    public ConeheadZombie(int row){
        super(row);
        HP = 140;
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
