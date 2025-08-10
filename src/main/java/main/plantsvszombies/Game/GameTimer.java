package main.plantsvszombies.Game;

import main.plantsvszombies.Game.Tools.Constants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class GameTimer {
    private final ImageView empty;
    private final ImageView full;
    private final ImageView zombieHead;
    private final ImageView flag1;
    private final ImageView flag2;

    public GameTimer(){
        empty = timeBar("FlagMeterEmpty");
        full = timeBar("FlagMeterFull");
        zombieHead = headPicture();
        flag1 = flag(1);
        flag2 = flag(2);
    }

    private ImageView timeBar(String str){
        ImageView timeBar = new ImageView(new Image("file:Pictures/ui/"+ str + ".png"));
        timeBar.setLayoutX(Constants.SCREEN_WIDTH / 1.25);
        timeBar.setLayoutY(Constants.SCREEN_HEIGHT / 1.09);
        timeBar.setFitWidth(Constants.SCREEN_WIDTH / 7);
        timeBar.setFitHeight(Constants.SCREEN_HEIGHT / 30);
        if (str.equals("full")) timeBar.setClip(new Rectangle(0, full.getFitHeight()));
        return timeBar;
    }

    private ImageView headPicture(){
        ImageView head = new ImageView(new Image("file:Pictures/ui/flagZombieHead.png"));
        head.setLayoutX(Constants.SCREEN_WIDTH / 1.25);
        head.setLayoutY(Constants.SCREEN_HEIGHT / 1.1);
        head.setFitWidth(Constants.TILE_SIZE / 3);
        head.setFitHeight(Constants.TILE_SIZE / 3);
        return head;
    }

    private ImageView flag(int num){
        ImageView flag = new ImageView(new Image("file:Pictures/ui/FlagMeterParts.png"));
        flag.setLayoutX(Constants.SCREEN_WIDTH / 1.25 + num * (full.getFitWidth() / 2.1));
        flag.setLayoutY(Constants.SCREEN_HEIGHT / 1.12);
        flag.setFitWidth(Constants.TILE_SIZE / 4);
        flag.setFitHeight(Constants.TILE_SIZE / 4);
        return flag;
    }

    public ImageView[] get(){
        return new ImageView[]{flag1, flag2, empty, full, zombieHead};
    }

    public void update() {
        Rectangle clip = new Rectangle(full.getFitWidth() * value(), full.getFitHeight());
        flagsMove();
        full.setClip(clip);
        zombieHead.setLayoutX(full.getLayoutX() + clip.getWidth() - zombieHead.getFitWidth()/2);
    }

    private void flagsMove(){
        double time = Constants.gameTime;
        if (time % 50 != 0) return;

        if (time >= 70_000 && time < 71_000) flag1.setLayoutY(flag1.getLayoutY() - 1);
        else if (time >= 140_000 && time < 141_000) flag2.setLayoutY(flag2.getLayoutY() - 1);
    }

    private double value(){
        if (Constants.gameTime < 20_000) return 0;
        else if (Constants.gameTime < 70_000) return (Constants.gameTime - 20_000.0) / 100_000;
        else if (Constants.gameTime < 80_000) return 1.0 / 2;
        else if (Constants.gameTime < 140_000) return (Constants.gameTime - 20_000.0) / 120_000;
        else return 1.0;
    }
}
