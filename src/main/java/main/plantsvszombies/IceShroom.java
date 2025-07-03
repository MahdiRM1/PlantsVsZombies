package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.List;

public class IceShroom extends BombPlant implements Shroom{

    public static final int recharge = 15;
    private long wakeUpTime;
    private boolean isSleep;
    private static final Image sleepImage;
    private static final Image normalImage;

    static {
        sleepImage = new Image("file:Pictures/plantsGifs/IceShroomSleep.gif");
        normalImage = new Image("file:Pictures/plantsGifs/IceShroom.gif");
    }

    public IceShroom(int row, int col, GameMode mode){
        super(row, col);
        price = 75;
        HP = 100;
        isSleep = setIsSleep(mode);
        gif.setImage((isSleep) ? sleepImage : normalImage);
        wakeUpTime = timeCreated;
    }

    @Override
    public boolean explosion(List<Zombie> zombies) {
        if(isSleep){
            wakeUpTime = GlobalState.gameTime;
            return false;
        }
        if(Math.abs(GlobalState.gameTime - wakeUpTime) >= 1500){
            for (Zombie z : zombies){
                z.setState(ZombieState.FREEZE);
                z.updateFreezeTime();
            }
            return true;
        }
        return false;
    }

    @Override
    public void wakeUp() {
        isSleep = false;
        gif.setImage(normalImage);
    }

    @Override
    public boolean isSleep(){
        return isSleep;
    }
}
