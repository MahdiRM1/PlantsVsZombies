package main.plantsvszombies;

import javafx.scene.image.ImageView;

public abstract class Plant {
    protected int price;
    protected int recharge;
    protected double hp;
    protected ImageView gif;

    public void damage(){
        hp -= 2.5;
    }

    public ImageView getGif() {
        return gif;
    }

    public double getHp() {
        return hp;
    }
}
