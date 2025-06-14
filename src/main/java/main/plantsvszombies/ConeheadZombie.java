package main.plantsvszombies;

import javafx.scene.image.Image;

public class ConeheadZombie extends Zombie{

    public ConeheadZombie(int row){
        super(row);
        HP = 140;
        speed = 4;
    }

    {
        walkPictureNum = 21;
        attackPictureNum = 11;
        walkZombie = new Image[walkPictureNum];
        walkFrozenZombie = new Image[walkPictureNum];
        attackZombie = new Image[attackPictureNum];
        attackFrozenZombie = new Image[attackPictureNum];
        for (int i = 0; i < walkPictureNum; i++) {
            walkZombie[i] = new Image("file:Pictures/ZombiePicture/ConeheadZombie/" +
                    "ConeheadZombie/ConeheadZombie_" + i + ".png");
            walkFrozenZombie[i] = new Image("file:Pictures/ZombiePicture/ConeheadZombie/" +
                    "FrozenConeheadZombie/ConeheadZombie_" + i + ".png");
        }
        for (int i = 0; i < attackPictureNum; i++) {
            attackZombie[i] = new Image("file:Pictures/ZombiePicture/ConeheadZombie/" +
                    "ConeheadZombieAttack/ConeheadZombieAttack_" + i + ".png");
            attackFrozenZombie[i] = new Image("file:Pictures/ZombiePicture/ConeheadZombie/" +
                    "FrozenConeheadZombieAttack/ConeheadZombieAttack_" + i + ".png");
        }
    }
}
