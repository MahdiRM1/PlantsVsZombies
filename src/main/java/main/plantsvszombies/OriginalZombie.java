package main.plantsvszombies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OriginalZombie extends Zombie{

    private final Image[] zombiepic = new Image[22];
    private int nowPic;

    public OriginalZombie(int row){
        super(row);
        hp = 100;
        speed = 4;
        for (int i = 0; i < 22; i++) {
            zombiepic[i] = new Image("file:Pictures/normalZombie/Zombie_" + i + ".png");
        }
        picture = new ImageView(zombiepic[nowPic]);
        picture.setFitWidth(Constants.ZOMBIE_GIF_WEIGHT);
        picture.setFitHeight(Constants.ZOMBIE_GIF_HEIGHT);
        picture.setLayoutY(Constants.height - picture.getFitHeight() - (4-row) * Constants.TILE_HEIGHT);
        picture.setLayoutX(Constants.width);
    }

    @Override
    public void move(){
        nowPic = (nowPic + 1) % 22;
        picture.setImage(zombiepic[nowPic]);
        picture.setLayoutX(picture.getLayoutX() - Constants.TILE_WIDTH/(speed*20));
        col = (int)((picture.getLayoutX() + picture.getFitWidth() / 1.5) / Constants.TILE_WIDTH);
    }
}
