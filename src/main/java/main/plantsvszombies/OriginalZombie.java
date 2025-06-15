package main.plantsvszombies;

import javafx.scene.image.Image;

public class OriginalZombie extends Zombie{

    private final static int walkPictureNum = 22;
    private final static int attackPictureNum = 21;
    private final static Image[] walkZombie = new Image[walkPictureNum];
    private final static Image[] walkFrozenZombie = new Image[walkPictureNum];
    private final static Image[] attackZombie = new Image[attackPictureNum];
    private final static Image[] attackFrozenZombie = new Image[attackPictureNum];

    static{
        for (int i = 0; i < walkPictureNum; i++) {
            walkZombie[i] = new Image("file:Pictures/ZombiePicture/originalZombie/ZombieWalk/Zombie_" + i + ".png");
            walkFrozenZombie[i] = new Image("file:Pictures/ZombiePicture/originalZombie/FrozenZombieWalk/Zombie_" + i + ".png");
        }
        for (int i = 0; i < attackPictureNum; i++) {
            attackZombie[i] = new Image("file:Pictures/ZombiePicture/originalZombie/ZombieAttack/ZombieAttack_" + i + ".png");
            attackFrozenZombie[i] = new Image("file:Pictures/ZombiePicture/originalZombie/FrozenZombieAttack/ZombieAttack_" + i + ".png");
        }
    }

    public OriginalZombie(int row){
        super(row);
        HP = 100;
        speed = 4;
    }

    @Override
    protected Image[] getWalkImage(boolean isFrozen) {
        return (isFrozen) ? walkFrozenZombie : walkZombie;
    }

    @Override
    protected Image[] getEatImage(boolean isFrozen) {
        return (isFrozen) ? attackFrozenZombie : attackZombie;
    }

}
