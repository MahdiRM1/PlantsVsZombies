package main.plantsvszombies;

import javafx.scene.image.Image;

public class OriginalZombie extends Zombie{

    private final static int walkPictureNum = 22;
    private final static int attackPictureNum = 21;
    private final static int dieImagesNum = 14;
    private final static Image[] walkZombie = new Image[walkPictureNum];
    private final static Image[] attackZombie = new Image[attackPictureNum];
    private final static Image[] dieImages = new Image[dieImagesNum];

    static{
        for (int i = 0; i < walkPictureNum; i++) {
            walkZombie[i] = new Image("file:Pictures/ZombiePicture/originalZombie/ZombieWalk/Zombie_" + i + ".png");
        }
        for (int i = 0; i < attackPictureNum; i++) {
            attackZombie[i] = new Image("file:Pictures/ZombiePicture/originalZombie/ZombieAttack/ZombieAttack_" + i + ".png");
        }
        for (int i = 0; i < dieImagesNum; i++) {
            dieImages[i] = new Image("file:Pictures/ZombiePicture/originalZombie/ZombieDie/ZombieDie_" + i +".png");
        }
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
        return dieImages;
    }

}
