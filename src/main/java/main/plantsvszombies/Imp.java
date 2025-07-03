package main.plantsvszombies;

import javafx.scene.image.Image;

public class Imp extends Zombie{

    private final static int walkPictureNum = 33;
    private final static int attackPictureNum = 27;
    private final static int dieImagesNum = 22;
    private final static Image[] walkZombie = new Image[walkPictureNum];
    private final static Image[] attackZombie = new Image[attackPictureNum];
    private final static Image[] dieImages = new Image[dieImagesNum];

    static {
        for (int i = 0; i < walkPictureNum; i++) {
            walkZombie[i] = new Image("file:Pictures/ZombiePicture/Imp/" +
                    "ZombieWalk/" + i + ".png");
        }
        for (int i = 0; i < attackPictureNum; i++) {
            attackZombie[i] = new Image("file:Pictures/ZombiePicture/Imp/" +
                    "ZombieAttack/" + i + ".png");
        }
        for (int i = 0; i < dieImagesNum; i++) {
            dieImages[i] = new Image("file:Pictures/ZombiePicture/Imp/ZombieDie/" + i +".png");
        }
    }

    public Imp (ZombieData data) {
        super(data);
        HP = 50;
        speed = 3;
    }

    public Imp(int row){
        super(row);
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
        return dieImages;
    }
}
