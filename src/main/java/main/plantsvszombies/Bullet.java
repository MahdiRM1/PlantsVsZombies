package main.plantsvszombies;

import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

public class Bullet {

    private static final AudioClip[] hit = new AudioClip[3];
    private static final AudioClip[] shoot = new AudioClip[2];
    private final int row;
    private final ImageView picture;
    private final BulletType type;

    static {
        for (int i = 0; i < 3; i++) hit[i] = Constants.setSound("splat" + i, false);
        for (int i = 0; i < 2; i++) shoot[i] = Constants.setSound("shoot" + i, false);
    }

    public Bullet(int row, int col, BulletType type) {
        this.row = row;
        picture = Constants.setBulletPicture(row, col, type);
        this.type = type;
        if (type == BulletType.SHROOM_BULLET) shoot[1].play();
        else shoot[0].play();
    }

    public double layoutX(){
        return picture.getLayoutX() + picture.getFitWidth() * 0.5;
    }

    //manage bullet movement
    public void move() {
        picture.setLayoutX(picture.getLayoutX() + Constants.TILE_SIZE / (25));
    }

    public void hit(){
        int hitNum = (int)(Math.random() * 3);
        hit[hitNum].play();
    }

    //getters
    public ImageView getPicture() {
        return picture;
    }

    public int getRow() {
        return row;
    }

    public BulletType getType() {
        return type;
    }
}
