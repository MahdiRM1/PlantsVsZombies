package main.plantsvszombies.Plants.OtherPlants;

import javafx.scene.image.Image;
import main.plantsvszombies.Enums.ChomperState;
import main.plantsvszombies.Enums.ZombieState;
import main.plantsvszombies.Game.Tools.Constants;
import main.plantsvszombies.Game.Tools.ImageFactory;
import main.plantsvszombies.Plants.Plant;
import main.plantsvszombies.Zombies.Zombie;

import java.util.List;

public class Chomper extends Plant {
    private static final Image[] NORMAL_FRAMES;
    private static final Image[] ATTACK_FRAMES;
    private static final Image[] DIGEST_FRAMES;
    private static final int FRAME_COUNT = 25;
    private static final int ATTACK_FRAME_COUNT = 25;
    private static final int DIGEST_FRAME_COUNT = 16;
    private ChomperState state = ChomperState.READY;
    private long chompTime = -1;

    static {
        NORMAL_FRAMES = ImageFactory.arrayImage("plantPictures/Chomper/normal/frame_", FRAME_COUNT);
        ATTACK_FRAMES = ImageFactory.arrayImage("plantPictures/Chomper/attack/frame_", ATTACK_FRAME_COUNT);
        DIGEST_FRAMES = ImageFactory.arrayImage("plantPictures/Chomper/digest/frame_", DIGEST_FRAME_COUNT);
    }

    public Chomper(int row, int col) {
        super(row, col);
        ImageFactory.changeScale(picture, 1.5);
        price = 150;
        HP = 100;
        rechargeTime = 15;
    }

    private void zombieCollision(List<Zombie> zombies){
        for (Zombie z : zombies)
            if (this.getRow() == z.getRow() && this.col - z.getCol() < 2 && this.col - z.getCol() > -1 && !z.isHypnotized()) {
                setState(ChomperState.CHOMP);
                return;
            }
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        updateFrame();
        switch (state){
            case READY -> {
                zombieCollision(zombies);
            }
            case CHOMP -> {
                if (nowPic == getImage().length - 1) {
                    setState(ChomperState.DIGEST);
                    return true;
                }
            }
            case DIGEST -> {
                if (Math.abs(Constants.gameTime - chompTime) > 10_000) setState(ChomperState.READY);
            }
        }
        return false;
    }

    public void action(List<Zombie> zombies) {
        chompTime = Constants.gameTime;
        for (Zombie z : zombies)
            if (row == z.getRow() && col == z.getCol() && !z.isHypnotized()) {
                z.setState(ZombieState.DEAD);
                return;
            }
    }

    private void setState(ChomperState state){
        this.state = state;
        nowPic = 0;
    }

    @Override
    protected Image[] getImage() {
        return switch (state){
            case READY -> NORMAL_FRAMES;
            case CHOMP -> ATTACK_FRAMES;
            case DIGEST -> DIGEST_FRAMES;
        };
    }
}
