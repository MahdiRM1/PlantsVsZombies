package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.List;

public class PuffShroom extends PeaPlant implements Shroom{

    private boolean isSleep;
    private static final Image sleepImage;
    private static final Image normalImage;

    static {
        sleepImage = new Image("file:Pictures/plantsGifs/PuffShroomSleep.gif");
        normalImage = new Image("file:Pictures/plantsGifs/PuffShroom.gif");
    }

    public PuffShroom(int row, int col, GameMode mode){
        super(row, col);
        price = 0;
        HP = 100;
        rechargeTime = 10;
        bulletType = BulletType.SHROOM_BULLET;
        isSleep = setIsSleep(mode);
        gif.setImage((isSleep) ? sleepImage : normalImage);
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies){
        if(isSleep) return false;

        updateFrame();
        for (Zombie z : zombies)
            if (row == z.getRow() && z.getCol() - col <= 4 && z.getCol() < 10 && z.getCol() >= col)
                if(z.getState() != ZombieState.DIE && z.getState() != ZombieState.BOOM_DIE)
                    return true;
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
