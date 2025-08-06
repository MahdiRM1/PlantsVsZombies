package main.plantsvszombies.Plants.PeaPlants;

import main.plantsvszombies.Enums.BulletType;
import main.plantsvszombies.Game.GlobalState;
import main.plantsvszombies.Items.Bullet;
import main.plantsvszombies.Plants.Plant;
import main.plantsvszombies.Plants.Shroom;
import main.plantsvszombies.Zombies.Zombie;

import java.util.List;

public abstract class PeaPlant extends Plant {

    protected BulletType bulletType;
    protected long lastShoot;
    protected boolean isShooting;

    public PeaPlant(int row, int col) {
        super(row, col);
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        boolean nowShooting = false;
        for (Zombie z : zombies) {
            if (row == z.getRow() && z.getCol() < 10 && z.getCol() >= col
                    && z.alive() && !z.isHypnotized()) {
                if (!isShooting) nowPic = 0;
                nowShooting = true;
                break;
            }
        }
        updateFrame();
        return isShooting = nowShooting;
    }

    public Bullet action() {
        if (this instanceof Shroom && Math.abs(lastShoot - GlobalState.gameTime) >= 1200) return shoot();
        else if (nowPic == 30) return shoot();
        return null;
    }

    protected Bullet shoot() {
        lastShoot = GlobalState.gameTime;
        return new Bullet(row, col, bulletType);
    }
}
