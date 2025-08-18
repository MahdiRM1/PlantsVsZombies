package main.plantsvszombies.Items;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.plantsvszombies.Enums.ZombieState;
import main.plantsvszombies.Game.Tools.Constants;
import main.plantsvszombies.Game.Tools.ImageFactory;
import main.plantsvszombies.Game.Tools.Utils;
import main.plantsvszombies.Zombies.Zombie;

import java.util.List;

public class LawnCleaner {
    private final static Image STABLE_FRAME;
    private final static Image[] MOVE_FRAMES;
    private final static int MOVE_FRAME_COUNT = 17;
    private final int row;
    private ImageView picture;
    private boolean isStable = true;
    private int nowPic;

    static {
        STABLE_FRAME = new Image(LawnCleaner.class.getResource("/Pictures/LawnCleaner/LawnCleaner.png").toExternalForm());
        MOVE_FRAMES = ImageFactory.arrayImage("LawnCleaner/move/frame_", MOVE_FRAME_COUNT);
    }

    public LawnCleaner(int row){
        this.row = row;
        picture = new ImageView(STABLE_FRAME);
        ImageFactory.setNodeSize(picture, Constants.BULLET_SIZE * 2, Constants.BULLET_SIZE * 2);
        ImageFactory.setNodePosition(picture, Constants.BOARD_X - Constants.TILE_SIZE/1.5, Constants.BOARD_Y + Constants.TILE_SIZE*(row + 0.3));
    }

    public void action(List<Zombie> zombies){
        if (!isStable) updateFrame();
        for (Zombie zombie : zombies) {
            if (Utils.checkCollision(layoutX(), zombie.layoutX(), row, zombie.getRow()))
                if (zombie.alive() && !zombie.isHypnotized()) {
                    zombie.setState(ZombieState.DIE);
                    isStable = false;
                }
        }
    }

    private void updateFrame() {
        nowPic = (nowPic + 1) % MOVE_FRAMES.length;
        picture.setImage(MOVE_FRAMES[nowPic]);
        picture.setLayoutX(picture.getLayoutX() + Constants.TILE_SIZE / 25);
    }

    public final double layoutX(){
        return picture.getLayoutX() + picture.getFitWidth() * 0.5;
    }

    public ImageView getPicture() {
        return picture;
    }

    public int getRow() {
        return row;
    }
}
