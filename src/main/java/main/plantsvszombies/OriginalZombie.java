package main.plantsvszombies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OriginalZombie extends Zombie{

    public OriginalZombie(int i){
        super(i);
        hp = 100;
        speed = 4;
        gif = new ImageView(new Image("file:Pictures/originalzombie.gif"));
        gif.setFitWidth(Constants.ZOMBIE_GIF_WEIGHT);
        gif.setFitHeight(Constants.ZOMBIE_GIF_HEIGHT);
        gif.setLayoutX(Constants.width - gif.getFitWidth());
        gif.setLayoutY(Constants.height - gif.getFitHeight() - (4-i) * Constants.TILE_HEIGHT);
    }
}
