package main.plantsvszombies;

import javafx.scene.image.Image;
import java.util.List;

public class SunFlower extends Plant{

    private long lastSunTime;
    private static final Image[] images;
    private static final int imageNum = 60;

    static {
        images = Constants.getArrayImage("Pictures/plantsGifs/SunFlower/frame_", imageNum);
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
        return Math.abs(GlobalState.gameTime - lastSunTime) >= 10000;
    }

    @Override
    protected Image[] getImage() {
        return images;
    }

    public Sun action(){
        lastSunTime = GlobalState.gameTime;
        return new Sun(SunType.RISEN.setCoordination(row, col));
    }


}
