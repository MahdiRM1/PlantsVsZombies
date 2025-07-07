package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.List;

public abstract class PeaPlant extends Plant{

    protected BulletType bulletType;
    protected long lastShoot;
    private boolean isShooting;
    protected int nowPic;

    public PeaPlant(int row, int col) {
        super(row, col);
        updateFrame();
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies){
        boolean nowShooting = false;
        for (Zombie z : zombies)
            if (row == z.getRow() && z.getCol() < 10 && z.getCol() >= col &&
                    z.getState() != ZombieState.DIE && z.getState() != ZombieState.BOOM_DIE && z.getState() != ZombieState.DEAD) {
                if (!isShooting) nowPic = 0;
                nowShooting = true;
                break;
            }
        updateFrame();
        return isShooting = nowShooting;
    }

    protected void updateFrame(){
        Image[] frame = getImage(isShooting);
        nowPic++;
        if (frame == null) {
            nowPic %= 60;
            return;
        }
        nowPic %= frame.length;
        gif.setImage(frame[nowPic]);
    }

    public Bullet action() {
        if (GlobalState.gameTime % 20 != 0) return null;

        if(nowPic == 30) {
            lastShoot = GlobalState.gameTime;
            return new Bullet(row, col, bulletType);
        }
        return null;
    }

    protected Image[] getImage(boolean isShooting){
        return null;
    };
}
