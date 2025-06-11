package main.plantsvszombies;

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
        picture.setLayoutX(col*Constants.TILE_WIDTH + Constants.TILE_WIDTH/1.3);
        picture.setLayoutY(Constants.height - (5-row)*Constants.TILE_HEIGHT + Constants.TILE_HEIGHT / 10);
    }

    public void move(){
        if(picture != null) {
            picture.setLayoutX(picture.getLayoutX() + Constants.TILE_WIDTH / (10));
        }
    }

    public ImageView getPicture() {
        return picture;
    }

    public int getRow() {
        return row;
    }
}
