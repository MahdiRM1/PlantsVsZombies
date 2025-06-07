package main.plantsvszombies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OriginalZombie extends Zombie{

    public OriginalZombie(){
        hp = 100;
        speed = 4;
        gif = new ImageView(new Image("file:Pictures/originalzombie.gif"));
//        gif.setFitWidth(Constants.TILE_WIDTH * 0.75);
//        gif.setFitHeight(Constants.TILE_HEIGHT * 1.2);
    }
}
