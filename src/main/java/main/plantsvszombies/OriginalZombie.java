package main.plantsvszombies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OriginalZombie extends Zombie{

    ImageView[] zombiepic = new ImageView[22];
    int nowPic;

    public OriginalZombie(int i){
        super(i);
        hp = 100;
        speed = 4;
        for (int j = 0; j < 22; j++) {
            zombiepic[j] = new ImageView(new Image("file:Pictures/normalZombie/Zombie_" + j + ".png"));
            zombiepic[j].setFitWidth(Constants.ZOMBIE_GIF_WEIGHT);
            zombiepic[j].setFitHeight(Constants.ZOMBIE_GIF_HEIGHT);
            zombiepic[j].setLayoutY(Constants.height - zombiepic[j].getFitHeight() - (4-i) * Constants.TILE_HEIGHT);
            zombiepic[j].setLayoutX(Constants.width - zombiepic[j].getFitHeight());
        }
        picture = zombiepic[nowPic];
    }

    @Override
    public void move(){
        nowPic = (nowPic + 1) % 22;
        double lx = picture.getLayoutX();
        picture = zombiepic[nowPic];
        picture.setLayoutX(lx - Constants.TILE_WIDTH/(speed*20));
    }
}
