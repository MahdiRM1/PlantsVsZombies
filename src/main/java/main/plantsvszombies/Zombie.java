package main.plantsvszombies;

import javafx.scene.image.ImageView;

public abstract class Zombie {

    protected int hp;
    protected int speed;
    protected ImageView picture;
    protected int i , j;

    public Zombie(int i) {
        this.i = i;
        j = 9;
    }

    public void eatPlant(){}

    public abstract void move();

    public int getHp() {
        return hp;
    }

    public ImageView getPicture() {
        return picture;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
