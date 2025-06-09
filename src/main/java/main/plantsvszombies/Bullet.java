package main.plantsvszombies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bullet {

    private int row;
    private ImageView picture;

    public Bullet(int row, int col) {
        this.row = row;
        picture = new ImageView(new Image("file:Pictures/normalBullet.png"));
        picture.setFitWidth(100);
        picture.setFitHeight(50);
        picture.setLayoutX(col*Constants.TILE_WIDTH + Constants.TILE_WIDTH/1.8);
        picture.setLayoutY(Constants.height - (5-row)*Constants.TILE_HEIGHT);
    }

    public void move(){
        if(picture != null) {
            picture.setLayoutX(picture.getLayoutX() + Constants.TILE_WIDTH / (10));
//            if (picture.getLayoutX() > Constants.width - Constants.TILE_WIDTH) picture = null;
        }
    }

    public ImageView getPicture() {
        return picture;
    }

    public int getRow() {
        return row;
    }
}
