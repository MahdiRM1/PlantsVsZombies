package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.List;
import java.util.Objects;

public class IceShroom extends BombPlant implements Shroom{

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
        rechargeTime = 15;
        isSleep = setIsSleep(mode);
        gif.setImage((isSleep) ? sleepImage : normalImage);
        wakeUpTime = timeCreated;
        explosionTime = 1500;
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        if(isSleep){
            wakeUpTime = GlobalState.gameTime;
            return false;
        }
        if(Math.abs(GlobalState.gameTime - wakeUpTime) >= explosionTime) {
            HP = 0;
            return true;
        }
        return false;
    }

    @Override
    public void action(List<Zombie> zombies){
        for (Zombie z : zombies){
            z.setState(ZombieState.FREEZE);
            z.updateFreezeTime();
        }
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
