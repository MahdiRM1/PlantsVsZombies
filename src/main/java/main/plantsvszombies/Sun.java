package main.plantsvszombies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

enum SunType{
    FALLEN,
    STABLE();

    private int row, col;

    public SunType setCoordination(int row, int col){
        SunType type = STABLE;
        this.row = row;
        this.col = col;
        return type;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}

public class Sun {

    private ImageView picture;
    private long timeCreated;
    private SunType type;
    private double maxY;

    public Sun(long timeCreated, SunType type) {
        this.timeCreated = timeCreated;
        this.type = type;
        picture = new ImageView(new Image("file:Pictures/sun/sun.png"));
        picture.setFitWidth(Constants.SUN_SIZE);
        picture.setFitHeight(Constants.SUN_SIZE);
        if(type == SunType.FALLEN){
            picture.setLayoutX(Constants.height/2.62 + Constants.TILE_SIZE * 9 * Math.random());
            picture.setLayoutY(0);
            maxY = Constants.height - Constants.width/24 - Constants.TILE_SIZE * 5 * Math.random();
        }
        else {
            picture.setLayoutX(type.getCol() * Constants.TILE_SIZE + Constants.TILE_SIZE / 1.3 + Constants.height / 2.62);
            picture.setLayoutY(Constants.height - (6 - type.getRow()) * Constants.TILE_SIZE + Constants.TILE_SIZE / 10 + Constants.width / 24);
        }
    }

    public void moveSun(long time){
        if(picture.getLayoutY() < maxY) {
            picture.setLayoutY(picture.getLayoutY() + 5);
            timeCreated = time;
        }
    }

    public ImageView getPicture() {
        return picture;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public SunType getType() {
        return type;
    }
}
