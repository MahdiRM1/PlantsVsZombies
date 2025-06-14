package main.plantsvszombies;

import javafx.scene.image.Image;

public class ConeheadZombie extends Zombie{

    private static final int walkPictureNum = 21;
    private static final int attackPictureNum = 11;
    private static final Image[] walkZombie = new Image[walkPictureNum];
    private static final Image[] eatPlant = new Image[attackPictureNum];

    static{
        for (int i = 0; i < walkPictureNum; i++)
            walkZombie[i] = new Image("file:Pictures/ZombiePicture/ConeheadZombie/" +
                    "ConeheadZombie/ConeheadZombie_" + i +".png");
        for (int i = 0; i < attackPictureNum; i++)
            eatPlant[i] = new Image("file:Pictures/ZombiePicture/ConeheadZombie/" +
                    "ConeheadZombieAttack/ConeheadZombieAttack_" + i + ".png");
    }

    public ConeheadZombie(int row){
        super(row);
        HP = 140;
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
