package main.plantsvszombies.Plants.OtherPlants;

import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import main.plantsvszombies.Game.Tools.Constants;
import main.plantsvszombies.Game.Tools.ImageFactory;
import main.plantsvszombies.Game.Tools.SoundManager;
import main.plantsvszombies.Items.Fog;
import main.plantsvszombies.Plants.Plant;
import main.plantsvszombies.Zombies.Zombie;

public class Blover extends Plant {

    private static final int FRAME_COUNT = 59;
    private static final Image[] FRAMES;
    private static final AudioClip sound;


    static {
        FRAMES = ImageFactory.arrayImage("Pictures/plantPictures/Blover/normal/frame_", FRAME_COUNT);
        sound = SoundManager.setSound("blover", false);
    }

    public Blover(int row, int col) {
        super(row, col);
        price = 100;
        HP = 100;
        rechargeTime = 30;
        if (row != -1) sound.play();
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        updateFrame();
        if(nowPic == FRAMES.length - 1) die();
        return nowPic > 13 && nowPic < 49;
    }

    public void action(Fog fog) {
        if (fog == null) return;
        fog.move(true);
        fog.setBloverTime(Constants.gameTime);
    }

    @Override
    protected Image[] getImage() {
        return FRAMES;
    }
}
