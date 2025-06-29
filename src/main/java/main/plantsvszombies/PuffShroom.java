package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class PuffShroom extends PeaPlant implements Shroom{

    public static final int recharge = 10;
    private boolean isSleep;

    public PuffShroom(int row, int col, GameMode mode){
        super(row, col);
        price = 0;
        HP = 100;
        bulletType = BulletType.SHROOM_BULLET;
        if(mode == GameMode.DAY) {
            gif.setImage(new Image("file:Pictures/plantsGifs/PuffShroomSleep.gif"));
            isSleep = true;
        }
        else isSleep = false;
    }

    @Override
    public boolean canShoot(ArrayList<Zombie> zombies){
        if(!isSleep)
            for (Zombie z : zombies)
                if (row == z.getRow() && z.getCol() - col < 4) return true;
        return false;
    }

    @Override
    public void wakeUp() {
        isSleep = false;
        gif.setImage(new Image("file:Pictures/plantsGifs/PuffShroom.gif"));
    }

    @Override
    public boolean isSleep(){
        return isSleep;
    }
}
