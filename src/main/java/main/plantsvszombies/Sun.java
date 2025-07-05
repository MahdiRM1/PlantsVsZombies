package main.plantsvszombies;

import javafx.scene.image.ImageView;

public class Sun {

    private final ImageView picture;
    private long timeCreated;
    private final SunType type;
    private final double maxY;

    public Sun(SunType type) {
        timeCreated = GlobalState.gameTime;
        this.type = type;
        picture = Constants.setSunPicture(type);
        maxY = Constants.sunMaxY(type);
    }

    //manages fallen sun movements
    public void moveSun(){
        if(picture.getLayoutY() < maxY) {
            picture.setLayoutY(picture.getLayoutY() + Constants.TILE_SIZE/100);
            timeCreated = GlobalState.gameTime;
        }
    }

    //getters
    public ImageView getPicture() {
        return picture;
    }

    public long getTimeCreated() {
        return timeCreated;
    }
}
