package main.plantsvszombies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bullet {
    private int x, y;
    private double speed;
    private ImageView bulletImage;
    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
        bulletImage = new ImageView(new Image("file:Pictures/normalBullet.jpeg"));
    }

}
