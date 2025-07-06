package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.List;

public class DoomShroom extends BombPlant implements Shroom{

    private boolean isSleep;
    private long wakeUpTime;
    private  final Image sleepImage;
    private final Image normalImage;

    {
        sleepImage = new Image("file:Pictures/plantsGifs/DoomShroomSleep.gif");
        normalImage = new Image("file:Pictures/plantsGifs/DoomShroom.gif");
    }

    public DoomShroom(int row, int col, GameMode mode){
        super(row, col);
        price = 125;
        HP = 100;
        rechargeTime = 15;
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
        if(Math.abs(GlobalState.gameTime - wakeUpTime) == 1000){
            gif.setImage(new Image("file:Pictures/plantsGifs/doom.gif"));
            for (Zombie z : zombies){
                if(Math.abs(z.getRow() - row) <= 2 &&  Math.abs(z.getCol() - col) <= 2) {
                    z.setState(ZombieState.BOOM_DIE);
                }
            }
        }
        else if (Math.abs(GlobalState.gameTime - wakeUpTime) == 2000) {
            gif.setImage(new Image("file:Pictures/plantsGifs/DayHole1.png"));
        }
        else return Math.abs(GlobalState.gameTime - wakeUpTime) == 20000;
        return false;
    }

    @Override
    public void wakeUp() {
        isSleep = false;
        gif.setImage(new Image("file:Pictures/plantsGifs/DoomShroom.gif"));
    }

    @Override
    public boolean isSleep(){
        return isSleep;
    }
}
