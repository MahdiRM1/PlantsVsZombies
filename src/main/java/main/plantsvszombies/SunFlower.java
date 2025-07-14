package main.plantsvszombies;

import javafx.scene.image.Image;
import java.util.List;

public class SunFlower extends Plant{

    private long lastSunTime;
    private boolean sunGiving;
    private static final Image[] NORMAL_FRAMES;
    private static final Image[] GIVE_SUN_FRAMES;
    private static final int NORMAL_FRAME_COUNT = 60;
    private static final int GIVE_SUN_FRAME_COUNT = 26;

    static {
        NORMAL_FRAMES = Constants.getArrayImage("Pictures/plantsGifs/SunFlower/normal/frame_", NORMAL_FRAME_COUNT);
        GIVE_SUN_FRAMES = Constants.getArrayImage("Pictures/plantsGifs/SunFlower/giveSun/frame_", GIVE_SUN_FRAME_COUNT);
    }

    public SunFlower(int row, int col){
        super(row, col);
        price = 50;
        HP = 100;
        rechargeTime = 10;
        lastSunTime = timeCreated;
        frameUpdateTime = 20;
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies){
        updateFrame();
        if (Math.abs(GlobalState.gameTime - lastSunTime) == 8800) {
            nowPic = 0;
            frameUpdateTime = 60;
            sunGiving = true;
        }
        else if (sunGiving && nowPic >= GIVE_SUN_FRAMES.length-1) {
            nowPic = 0;
            frameUpdateTime = 20;
            sunGiving = false;
        }
        return sunGiving && nowPic == 20 && GlobalState.gameTime % 60 == 0;
    }

    @Override
    protected Image[] getImage() {
        return sunGiving ? GIVE_SUN_FRAMES : NORMAL_FRAMES;
    }

    public Sun action(){
        lastSunTime = GlobalState.gameTime;
        return new Sun(SunType.RISEN.setCoordination(row, col));
    }


}
