package main.plantsvszombies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SunFlower extends Plant{

    private long lastSunTime;

    public SunFlower(int row, int col, long timeCreated){
        super(row, col, timeCreated);
        price = 50;
        recharge = 5;
        hp = 100;
        lastSunTime = timeCreated;
        gif = new ImageView(new Image("file:Pictures/platnsGifs/DayTime/SunFlower.gif"));
        gif.setFitHeight(Constants.TILE_SIZE * 0.8);
        gif.setFitWidth(Constants.TILE_SIZE * 0.8);
    }

    public Sun givenSun(long time) {
        if(Math.abs(time - lastSunTime) >= 10000) {
            lastSunTime = time;
            return new Sun(time, SunType.STABLE.setCoordination(row, col));
        }
        return null;
    }
}
