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
        explosionTime = 1000;
        endOfAction = 20000;
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        if(isSleep){
            wakeUpTime = GlobalState.gameTime;
            return false;
        }
        if(Math.abs(GlobalState.gameTime - wakeUpTime) == explosionTime) return true;

        if (Math.abs(GlobalState.gameTime - wakeUpTime) == 2000) {
            gif.setImage(new Image("file:Pictures/plantsGifs/DayHole1.png"));
        }
        else if(Math.abs(GlobalState.gameTime - wakeUpTime) == endOfAction) HP = 0;
        return false;
    }

    @Override
    public void action(List<Zombie> zombies){
        gif.setImage(new Image("file:Pictures/plantsGifs/doom.gif"));
        for (Zombie z : zombies){
            if(Math.abs(z.getRow() - row) <= 2 &&  Math.abs(z.getCol() - col) <= 2) {
                z.setState(ZombieState.BOOM_DIE);
            }
        }
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
