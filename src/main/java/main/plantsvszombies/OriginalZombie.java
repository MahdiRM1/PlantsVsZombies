package main.plantsvszombies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OriginalZombie extends Zombie{

    private static final Image[] walkZombie = new Image[22];

    static{
        for (int i = 0; i < 22; i++)
            walkZombie[i] = new Image("file:Pictures/normalZombie/Zombie_" + i + ".png");
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
        }
    }
}
