package main.plantsvszombies;

import javafx.scene.image.Image;

public class OriginalZombie extends Zombie{

    private final static int walkPictureNum = 47;
    private final static int attackPictureNum = 40;
    private final static int diePicturesNum = 39;
    private final static Image[] walkZombie;
    private final static Image[] attackZombie;
    private final static Image[] dieZombie;

    static{
        walkZombie = Constants.getArrayImage("Pictures/ZombiePicture/OriginalZombie/Zombie/frame_", walkPictureNum);
        attackZombie = Constants.getArrayImage("Pictures/ZombiePicture/OriginalZombie/ZombieAttack/frame_", attackPictureNum);
        dieZombie = Constants.getArrayImage("Pictures/ZombiePicture/OriginalZombie/ZombieDie/frame_", diePicturesNum);
    }

    public OriginalZombie(ZombieData data){
        super(data);
        HP = 100;
        speed = 5;
    }

    public OriginalZombie(int row){
        super(row);
        HP = 100;
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
