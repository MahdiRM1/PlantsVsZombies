package main.plantsvszombies;

import javafx.scene.image.ImageView;

public class Bullet {

    private final int row;
    private final ImageView picture;
    private final BulletType type;

    public Bullet(int row, int col, BulletType type) {
        this.row = row;
        picture = Constants.setBulletPicture(row, col, type);
        this.type = type;
    }

    //manage bullet movement
    public void move() {
        if (picture != null) {
            picture.setLayoutX(picture.getLayoutX() + Constants.TILE_SIZE / (25));
        }
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
