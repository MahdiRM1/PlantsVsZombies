package main.plantsvszombies;


import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Fog {

    private final int fogLength;
    private final ImageView picture;
    private final Rectangle full;
    private final double moveValue;
    private final double minPicX;
    private long bloverTime;

    public Fog(Pane fogGrid, int fogLength) {
        this.fogLength = fogLength;
        picture = Constants.setFogPicture(fogLength);
        minPicX = picture.getLayoutX();
        moveValue = (Constants.SCREEN_WIDTH - minPicX)/59;
        full = new Rectangle(picture.getFitWidth(), picture.getFitHeight());
        picture.setClip(full);
        fogGrid.getChildren().add(picture);
    }

    public void clearFog(int centerRow, int centerCol) {
        Shape clip = (Shape)picture.getClip();
        Circle circle = new Circle(Constants.TILE_SIZE*2);
        circle.setLayoutX((centerCol + 0.5 - fogLength) * Constants.TILE_SIZE);
        circle.setLayoutY((centerRow + 1.23) * Constants.TILE_SIZE);
        Shape clipped = Shape.subtract(clip, circle);
        picture.setClip(clipped);
    }

    public void move(boolean blover) {
        if(blover) picture.setLayoutX(picture.getLayoutX() + moveValue);
        else if (picture.getLayoutX() >= minPicX) picture.setLayoutX(picture.getLayoutX() - moveValue/4);
    }

    public void updateFog() {
        long blover = Math.abs(GlobalState.gameTime - bloverTime);
        if (blover < 10_000) return;
        else move(false);

        picture.setClip(full);
    }

    public void setBloverTime(long time) {
        bloverTime = time;
    }
}
