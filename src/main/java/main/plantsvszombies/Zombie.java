package main.plantsvszombies;

import javafx.scene.image.ImageView;

public abstract class Zombie {

    protected int hp;
    protected int speed;
    protected ImageView gif;

    public void eatPlant(){}

    public int getHp() {
        return hp;
    }

    public ImageView getGif() {
        return gif;
    }
}
