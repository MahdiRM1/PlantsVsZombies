package main.plantsvszombies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PeaShooter extends PeaPlant{

    public PeaShooter(int row, int col, long timeCreated) {
        super(row, col, timeCreated);
       price = 100;
       recharge = 5;
       hp = 100;
       gif = new ImageView(new Image("file:Pictures/platnsGifs/DayTime/Pea.gif"));
       gif.setFitHeight(Constants.TILE_SIZE * 0.8);
       gif.setFitWidth(Constants.TILE_SIZE * 0.8);
    }

}
