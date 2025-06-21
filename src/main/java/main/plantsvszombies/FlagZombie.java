package main.plantsvszombies;

import javafx.scene.image.Image;

public class FlagZombie extends OriginalZombie{

    private final static int walkPictureNum = 24;
    private final static int attackPictureNum = 11;
    private final static int dieImagesNum = 14;
    private final static Image[] walkZombie = new Image[walkPictureNum];
    private final static Image[] attackZombie = new Image[attackPictureNum];
    private final static Image[] dieImages = new Image[dieImagesNum];

    static{
        for (int i = 0; i < walkPictureNum; i++) {
            walkZombie[i] = new Image("file:Pictures/ZombiePicture/FlagZombie/ZombieWalk/FlagZombie_" + i + ".png");
        }
        for (int i = 0; i < attackPictureNum; i++) {
            attackZombie[i] = new Image("file:Pictures/ZombiePicture/FlagZombie/ZombieAttack/FlagZombieAttack_" + i + ".png");
        }
        for (int i = 0; i < dieImagesNum; i++) {
            dieImages[i] = new Image("file:Pictures/ZombiePicture/FlagZombie/ZombieDie/ZombieDie_" + i +".png");
        }
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
        return dieImages;
    }
}
