package main.plantsvszombies;

import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bullet {

    private int row;
    private ImageView picture;

    public Bullet(int row, int col) {
        this.row = row;
        picture = new ImageView(new Image("file:Pictures/bullets/normalBullet.png"));
        picture.setFitWidth(Constants.BULLET_SIZE);
        picture.setFitHeight(Constants.BULLET_SIZE);
        picture.setLayoutX(col*Constants.TILE_SIZE + Constants.TILE_SIZE/1.3 + Constants.height/2.62);
        picture.setLayoutY(Constants.height - (6-row)*Constants.TILE_SIZE + Constants.TILE_SIZE / 10 + Constants.width/24);
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
