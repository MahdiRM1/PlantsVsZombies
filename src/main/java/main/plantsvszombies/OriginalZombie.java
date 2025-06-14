package main.plantsvszombies;

import javafx.scene.image.Image;

public class OriginalZombie extends Zombie{

    public OriginalZombie(int row){
        super(row);
        HP = 100;
        speed = 4;
    }

    {
        walkPictureNum = 22;
        attackPictureNum = 21;
        walkZombie = new Image[walkPictureNum];
        walkFrozenZombie = new Image[walkPictureNum];
        attackZombie = new Image[attackPictureNum];
        attackFrozenZombie = new Image[attackPictureNum];
        for (int i = 0; i < walkPictureNum; i++) {
            walkZombie[i] = new Image("file:Pictures/ZombiePicture/originalZombie/ZombieWalk/Zombie_" + i + ".png");
            walkFrozenZombie[i] = new Image("file:Pictures/ZombiePicture/originalZombie/FrozenZombieWalk/Zombie_" + i + ".png");
        }
        for (int i = 0; i < attackPictureNum; i++) {
            attackZombie[i] = new Image("file:Pictures/ZombiePicture/originalZombie/ZombieAttack/ZombieAttack_" + i + ".png");
            attackFrozenZombie[i] = new Image("file:Pictures/ZombiePicture/originalZombie/FrozenZombieAttack/ZombieAttack_" + i + ".png");
        }
    }
}
