package main.plantsvszombies;

import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

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
    private static final AudioClip sound;

    static {
        SLEEP_FRAMES = Constants.getArrayImage("Pictures/plantPictures/DoomShroom/sleep/frame_", SLEEP_FRAME_COUNT);
        NORMAL_FRAMES = Constants.getArrayImage("Pictures/plantPictures/DoomShroom/normal/frame_", NORMAL_FRAME_COUNT);
        DOOM_FRAMES = Constants.getArrayImage("Pictures/plantPictures/DoomShroom/doom/frame_", DOOM_FRAME_COUNT);
        sound = new AudioClip("file:Audio/doomshroom.mp3");
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
            wakeUpTime = GlobalState.gameTime;
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
                picture.setImage(new Image("file:Pictures/plantPictures/DoomShroom/" + str + "Hole1.png"));
                picture.setLayoutY(picture.getLayoutY() + picture.getFitHeight() / 4);
                Constants.changeScale(picture, 1);
            }
        } else if (Math.abs(GlobalState.gameTime - wakeUpTime) == 12000) {
            String time = timeCreated != wakeUpTime ? "Day" : "Night";
            picture.setImage(new Image("file:Pictures/plantPictures/DoomShroom/" + time + "Hole2.png"));
        } else if (Math.abs(GlobalState.gameTime - wakeUpTime) == 22000) HP = 0;
        return false;
    }

    @Override
    public void action(List<Zombie> zombies) {
        sound.play();
        Constants.changeScale(picture, 2);
        picture.setLayoutY(picture.getLayoutY() - picture.getFitHeight() / 4);
        for (Zombie z : zombies) {
            if (Math.abs(z.getRow() - row) <= 2 && Math.abs(z.getCol() - col) <= 2 &&
                    Constants.aliveZombie(z) && !z.isHypnotized())
                z.setState(ZombieState.BOOM_DIE);
        }
    }

    @Override
    public void wakeUp() {
        isSleep = false;
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
