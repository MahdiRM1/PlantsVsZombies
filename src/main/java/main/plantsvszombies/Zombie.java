package main.plantsvszombies;

import javafx.scene.image.ImageView;

public abstract class Zombie {

    protected int hp;
    protected int speed;
    protected ImageView gif;
    protected int i , j;

    public Zombie(int i) {
        this.i = i;
        j = 9;
    }

    public void eatPlant(){}

    public int getHp() {
        return hp;
    }

    public ImageView getGif() {
        return gif;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
