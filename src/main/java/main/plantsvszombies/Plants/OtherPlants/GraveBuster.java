package main.plantsvszombies.Plants.OtherPlants;

import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import main.plantsvszombies.Game.Tools.Constants;
import main.plantsvszombies.Game.Tools.ImageFactory;
import main.plantsvszombies.Game.Tools.SoundManager;
import main.plantsvszombies.Items.Grave;
import main.plantsvszombies.Plants.Plant;
import main.plantsvszombies.Zombies.Zombie;

public class GraveBuster extends Plant {

    private final Grave grave;
    private static final Image[] FRAMES;
    private static final int FRAME_COUNT = 28;
    private static final AudioClip sound;

    static {
        FRAMES = ImageFactory.arrayImage("Pictures/plantPictures/GraveBuster/normal/frame_", FRAME_COUNT);
        sound = SoundManager.setSound("gravebusterchomp", false);
    }

    public GraveBuster(int row, int col, Grave grave) {
        super(row, col);
        price = 75;
        HP = 100;
        rechargeTime = 10;
        this.grave = grave;
        ImageFactory.changeScale(picture, 1.3);
        picture.setLayoutY(picture.getLayoutY() - Constants.TILE_SIZE / 2);
        if (grave != null) sound.play();
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        updateFrame();
        if (Math.abs(Constants.gameTime - timeCreated) < 3000)
            picture.setLayoutY(picture.getLayoutY() + Constants.TILE_SIZE / 300);
        else if (Math.abs(Constants.gameTime - timeCreated) == 3000) {
            die();
            return true;
        }
        return false;
    }

    public Grave action() {
        return grave;
    }

    @Override
    protected Image[] getImage() {
        return FRAMES;
    }
}
