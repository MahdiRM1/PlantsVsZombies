package main.plantsvszombies;

import java.util.List;

public abstract class PeaPlant extends Plant{

    private long lastShoot;
    protected BulletType bulletType;

    public PeaPlant(int row, int col) {
        super(row, col);
        lastShoot = timeCreated;
    }

    public boolean canShoot(List<Zombie> zombies){
        for (Zombie z : zombies)
            if (row == z.getRow() && z.getCol() < 10)
                if(z.getState() != ZombieState.DIE && z.getState() != ZombieState.BOOM_DIE)
                    return true;
        return false;
    }

    public Bullet shoot(int row, int col) {
        if(Math.abs(GlobalState.gameTime - lastShoot) >= 1500) {
            lastShoot = GlobalState.gameTime;
            return new Bullet(row, col, bulletType);
        }
        return null;
    }
}
