package main.plantsvszombies;

import javafx.scene.image.ImageView;

public class Bullet {

    private final int row;
    private final ImageView picture;

    public Bullet(int row, int col) {
        this.row = row;
        picture = Constants.setBulletPicture(row, col);
    }

    public void move(){
        if(picture != null) {
            picture.setLayoutX(picture.getLayoutX() + Constants.TILE_SIZE / (10));
        }
    }

    public ImageView getPicture() {
        return picture;
    }

    public int getRow() {
        return row;
    }
}
