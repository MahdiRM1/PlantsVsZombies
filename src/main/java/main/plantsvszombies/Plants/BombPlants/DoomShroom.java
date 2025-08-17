package main.plantsvszombies.Plants.BombPlants;

import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import main.plantsvszombies.Enums.ZombieState;
import main.plantsvszombies.Game.Tools.Constants;
import main.plantsvszombies.Game.Tools.ImageFactory;
import main.plantsvszombies.Game.Tools.SoundManager;
import main.plantsvszombies.Plants.Shroom;
import main.plantsvszombies.Zombies.Zombie;

public class DoomShroom extends BombPlant implements Shroom {

    private long wakeUpTime;
    private boolean isSleep;
    private boolean finishAnimation;
    private static final int NORMAL_FRAME_COUNT = 22;
    private static final int SLEEP_FRAME_COUNT = 25;
    private static final int DOOM_FRAME_COUNT = 10;
    private static final Image[] SLEEP_FRAMES;
    private static final Image[] NORMAL_FRAMES;
    private static final Image[] DOOM_FRAMES;

    static {
        SLEEP_FRAMES = ImageFactory.arrayImage("plantPictures/DoomShroom/sleep/frame_", SLEEP_FRAME_COUNT);
        NORMAL_FRAMES = ImageFactory.arrayImage("plantPictures/DoomShroom/normal/frame_", NORMAL_FRAME_COUNT);
        DOOM_FRAMES = ImageFactory.arrayImage("plantPictures/DoomShroom/doom/frame_", DOOM_FRAME_COUNT);
    }

    public DoomShroom(int row, int col, boolean isSleep) {
        super(row, col);
        price = 125;
        HP = 100;
        rechargeTime = 15;
        this.isSleep = isSleep;
        wakeUpTime = timeCreated;
        finishAnimation = false;
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        if (isSleep) {
            updateFrame();
            wakeUpTime = Constants.gameTime;
            return false;
        }

        if (!finishAnimation) {
            updateFrame();
            if (!isExploded && nowPic >= getImage().length - 1) {
                nowPic = 0;
                frameUpdateTime = 80;
                return isExploded = true;
            } else if (nowPic >= getImage().length - 1) {
                finishAnimation = true;
                String str = timeCreated != wakeUpTime ? "Day" : "Night";
                picture.setImage(new Image(getClass().getResource("/Pictures/plantPictures/DoomShroom/" + str + "Hole1.png").toExternalForm()));
                picture.setLayoutY(picture.getLayoutY() + picture.getFitHeight() / 4);
                ImageFactory.changeScale(picture, 1);
            }
        } else if (Math.abs(Constants.gameTime - wakeUpTime) == 12000) {
            String time = timeCreated != wakeUpTime ? "Day" : "Night";
            picture.setImage(new Image(getClass().getResource("/Pictures/plantPictures/DoomShroom/" + time + "Hole2.png").toExternalForm()));
        } else if (Math.abs(Constants.gameTime - wakeUpTime) == 22000) die();
        return false;
    }

    @Override
    public void action(List<Zombie> zombies) {
        SoundManager.playSound("doomshroom");
        ImageFactory.changeScale(picture, 2);
        picture.setLayoutY(picture.getLayoutY() - picture.getFitHeight() / 4);
        for (Zombie z : zombies) {
            if (Math.abs(z.getRow() - row) <= 2 && Math.abs(z.getCol() - col) <= 2 && z.alive() && !z.isHypnotized())
                z.setState(ZombieState.BOOM_DIE);
        }
    }

    @Override
    public void wakeUp() {
        isSleep = false;
        wakeUpSound.play();
    }

    @Override
    public boolean isSleep() {
        return isSleep;
    }

    @Override
    protected Image[] getImage() {
        if (isSleep) {
            return SLEEP_FRAMES;
        }
        if (isExploded) {
            return DOOM_FRAMES;
        }
        return NORMAL_FRAMES;
    }
}
