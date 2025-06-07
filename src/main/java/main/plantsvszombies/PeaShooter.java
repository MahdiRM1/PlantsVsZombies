package main.plantsvszombies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PeaShooter extends PeaPlant{

    public PeaShooter() {
       price = 100;
       recharge = 5;
       hp = 100;
       gif = new ImageView(new Image("file:Pictures/peashooter.gif"));
       gif.setFitHeight(Constants.TILE_HEIGHT * 0.8);
       gif.setFitWidth(Constants.TILE_WIDTH * 0.8);
    }
}
