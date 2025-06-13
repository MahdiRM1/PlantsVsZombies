package main.plantsvszombies;

import javafx.scene.image.ImageView;

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

    private final ImageView picture;
    private long timeCreated;
    private final SunType type;
    private final double maxY;

    public Sun(long timeCreated, SunType type) {
        this.timeCreated = timeCreated;
        this.type = type;
        picture = Constants.setSunPicture(type);
        maxY = Constants.sunMaxY(type);
    }

    public void moveSun(long time){
        if(picture.getLayoutY() < maxY) {
            picture.setLayoutY(picture.getLayoutY() + 3);
            timeCreated = time;
        }
    }

    public ImageView getPicture() {
        return picture;
    }

    public long getTimeCreated() {
        return timeCreated;
    }
}
