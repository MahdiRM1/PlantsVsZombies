package main.plantsvszombies;

import javafx.scene.image.Image;

public class BucketheadZombie extends Zombie{

    private static final int walkPictureNum = 22;
    private static final int attackPictureNum = 11;
    private static final Image[] walkZombie = new Image[walkPictureNum];
    private static final Image[] eatPlant = new Image[attackPictureNum];

    static{
        for (int i = 0; i < walkPictureNum; i++)
            walkZombie[i] = new Image("file:Pictures/ZombiePicture/BucketheadZombie/" +
                    "BucketheadZombie/BucketheadZombie_" + i +".png");
        for (int i = 0; i < attackPictureNum; i++)
            eatPlant[i] = new Image("file:Pictures/ZombiePicture/BucketheadZombie/" +
                    "BucketheadZombieAttack/BucketheadZombieAttack_" + i + ".png");
    }

    public BucketheadZombie(int row){
        super(row);
        HP = 200;
        speed = 4;
    }

    @Override
    public void action(){
        switch (state){
            case WALKING -> move(walkZombie);
            case EATING -> eatPlant(eatPlant);
        }
    }
}
