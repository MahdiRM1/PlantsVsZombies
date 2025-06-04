package main.plantsvszombies;

import javafx.scene.image.ImageView;

public abstract class Plant {
    protected int price;
    protected int recharge;
    protected int hp;
    protected ImageView picture;
    protected ImageView gif;

    public ImageView getGif() {
        return gif;
    }

    public int getHp() {
        return hp;
    }
}
