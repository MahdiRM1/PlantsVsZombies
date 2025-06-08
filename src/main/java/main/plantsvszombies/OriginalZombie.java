package main.plantsvszombies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OriginalZombie extends Zombie{

    private final ImageView[] zombiepic = new ImageView[22];
    private int nowPic;

    public OriginalZombie(int row){
        super(row);
        hp = 100;
        speed = 4;
        for (int i = 0; i < 22; i++) {
            zombiepic[i] = new ImageView(new Image("file:Pictures/normalZombie/Zombie_" + i + ".png"));
            zombiepic[i].setFitWidth(Constants.ZOMBIE_GIF_WEIGHT);
            zombiepic[i].setFitHeight(Constants.ZOMBIE_GIF_HEIGHT);
            zombiepic[i].setLayoutY(Constants.height - zombiepic[i].getFitHeight() - (4-row) * Constants.TILE_HEIGHT);
            zombiepic[i].setLayoutX(Constants.width - zombiepic[i].getFitHeight());
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
