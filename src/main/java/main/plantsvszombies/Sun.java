package main.plantsvszombies;

import javafx.scene.image.ImageView;

public class Sun {

    private final ImageView picture;
    private long timeCreated;
    private SunType type;
    private final double maxY;
    private final double minY;

    public Sun(SunType type) {
        timeCreated = GlobalState.gameTime;
        this.type = type;
        picture = Constants.setSunPicture(type);
        maxY = Constants.sunMaxY(type);
        minY = maxY - 1.2 * Constants.TILE_SIZE;
    }

    //manages fallen sun movements
    public void moveSun() {
        switch (type) {
            case COLLECTED -> {
                double diffX = Constants.SCREEN_WIDTH / 40 - picture.getLayoutX();
                double diffY = Constants.SCREEN_HEIGHT / 40 - picture.getLayoutY();
                picture.setLayoutX(picture.getLayoutX() + diffX / 7);
                picture.setLayoutY(picture.getLayoutY() + diffY / 7);
            }
            case BASE_FALLEN -> {
                if (picture.getLayoutY() < maxY) {
                    picture.setLayoutY(picture.getLayoutY() + Constants.TILE_SIZE / 100);
                    timeCreated = GlobalState.gameTime;
                }
            }
            case FLOWER_FALLEN -> {
                if (picture.getLayoutY() < maxY) {
                    double diffY = Math.abs(minY - picture.getLayoutY());
                    picture.setLayoutY(picture.getLayoutY() + diffY / 5);
                    picture.setLayoutX(picture.getLayoutX() + 0.5);
                    timeCreated = GlobalState.gameTime;
                }
            }
            case RISEN -> {
                double diffY = Math.abs(minY - picture.getLayoutY());
                picture.setLayoutY(picture.getLayoutY() - diffY / 5);
                picture.setLayoutX(picture.getLayoutX() + 1);
                if (diffY < 1) {
                    type = SunType.FLOWER_FALLEN;
                }
            }
        }
    }

    public void setType(SunType type) {
        this.type = type;
    }

    //getters
    public ImageView getPicture() {
        return picture;
    }

    public long getTimeCreated() {
        return timeCreated;
    }
}
