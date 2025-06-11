package main.plantsvszombies;

import javafx.scene.image.Image;

public class OriginalZombie extends Zombie{

    private static final Image[] walkZombie = new Image[22];
    private static final Image[] eatPlant = new Image[22];

    static{
        for (int i = 0; i < 22; i++) {
            walkZombie[i] = new Image("file:Pictures/ZombiePicture/originalZombie/ZombieWalk/Zombie_" + i + ".png");
            eatPlant[i] = new Image("file:Pictures/ZombiePicture/originalZombie/ZombieAttack/ZombieAttack_" + i + ".png");
        }

    }

    public OriginalZombie(int row){
        super(row);
        hp = 100;
        speed = 4;
    }

    @Override
    public void action(){
        switch (state){
            case WALKING -> move(walkZombie);
            case EATING -> eatPlant(state.getPlant(), eatPlant);
        }
    }
}
