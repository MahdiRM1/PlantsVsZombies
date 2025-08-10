package main.plantsvszombies.Items;

import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import main.plantsvszombies.Enums.SunType;
import main.plantsvszombies.Game.Tools.Constants;
import main.plantsvszombies.Game.Tools.ImageFactory;
import main.plantsvszombies.Game.Tools.SoundManager;
import main.plantsvszombies.Game.Tools.Utils;

public class Sun {

    private final ImageView picture;
    private long timeCreated;
    private SunType type;
    private final double maxY;
    private final double minY;
    public static final AudioClip sound;

    static {
        sound = SoundManager.setSound("points", false);
    }

    public Sun(SunType type) {
        timeCreated = Constants.gameTime;
        this.type = type;
        picture = ImageFactory.createSunPicture(type);
        maxY = Utils.sunMaxY(type);
        minY = maxY - 1.2 * Constants.TILE_SIZE;
    }

    //manages fallen sun movements
    public void moveSun() {
        switch (type) {
            case COLLECTED -> {
                double diffX = Constants.SCREEN_WIDTH / 40 - picture.getLayoutX();
                double diffY = Constants.SCREEN_HEIGHT / 40 - picture.getLayoutY();
                ImageFactory.setNodePosition(picture, picture.getLayoutX() + diffX / 7, picture.getLayoutY() + diffY / 7);
            }
            case BASE_FALLEN -> {
                if (picture.getLayoutY() < maxY) {
                    picture.setLayoutY(picture.getLayoutY() + Constants.TILE_SIZE / 100);
                    timeCreated = Constants.gameTime;
                }
            }
            case FLOWER_FALLEN -> {
                if (picture.getLayoutY() < maxY) {
                    double diffY = Math.abs(minY - picture.getLayoutY());
                    ImageFactory.setNodePosition(picture, picture.getLayoutX() + 0.5, picture.getLayoutY() + diffY / 5);
                    timeCreated = Constants.gameTime;
                }
            }
            case RISEN -> {
                double diffY = Math.abs(minY - picture.getLayoutY());
                ImageFactory.setNodePosition(picture, picture.getLayoutX() + 1, picture.getLayoutY() - diffY / 5);
                if (diffY < 1) type = SunType.FLOWER_FALLEN;
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
