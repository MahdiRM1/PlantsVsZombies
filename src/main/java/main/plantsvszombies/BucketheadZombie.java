package main.plantsvszombies;

import javafx.scene.image.Image;

public class BucketheadZombie extends Zombie{

    public BucketheadZombie(int row){
        super(row);
        HP = 200;
        speed = 4;
    }

    {
        walkPictureNum = 22;
        attackPictureNum = 11;
        walkZombie = new Image[walkPictureNum];
        walkFrozenZombie = new Image[walkPictureNum];
        attackZombie = new Image[attackPictureNum];
        attackFrozenZombie = new Image[attackPictureNum];
        for (int i = 0; i < walkPictureNum; i++) {
            walkZombie[i] = new Image("file:Pictures/ZombiePicture/BucketheadZombie/" +
                    "BucketheadZombie/BucketheadZombie_" + i + ".png");
            walkFrozenZombie[i] = new Image("file:Pictures/ZombiePicture/BucketheadZombie/" +
                    "FrozenBucketheadZombie/BucketheadZombie_" + i + ".png");
        }
        for (int i = 0; i < attackPictureNum; i++) {
            attackZombie[i] = new Image("file:Pictures/ZombiePicture/BucketheadZombie/" +
                    "BucketheadZombieAttack/BucketheadZombieAttack_" + i + ".png");

            attackFrozenZombie[i] = new Image("file:Pictures/ZombiePicture/BucketheadZombie/" +
                    "FrozenBucketheadZombieAttack/BucketheadZombieAttack_" + i + ".png");
        }
    }
}
