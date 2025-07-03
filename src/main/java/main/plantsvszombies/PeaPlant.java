package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.List;

public abstract class PeaPlant extends Plant{

    protected BulletType bulletType;
    private long lastShoot;
    private Image shootGif;
    private  Image normalGif;
    private final String shootStr = "file:Pictures/plantsGifs/" + this.getClass().getSimpleName() + "_shoot.gif";
    private final String normalStr = "file:Pictures/plantsGifs/" + this.getClass().getSimpleName() + ".gif";

    {
        shootGif = new Image(shootStr);
        normalGif = new Image(normalStr);
    }

    public PeaPlant(int row, int col) {
        super(row, col);
        lastShoot = timeCreated - 350;
    }

    public boolean canShoot(List<Zombie> zombies){
        for (Zombie z : zombies)
            if (row == z.getRow() && z.getCol() < 10)
                if(z.getState() != ZombieState.DIE && z.getState() != ZombieState.BOOM_DIE && z.getState() != ZombieState.DEAD){
                    if(!gif.getImage().equals(shootGif)) {
                        shootGif = new Image(shootStr);
                        gif.setImage(shootGif);
                    }
                    return true;
                }
        if(!gif.getImage().equals(normalGif)) {
            normalGif = new Image(normalStr);
            gif.setImage(normalGif);
        }
        lastShoot = GlobalState.gameTime - 400;
        return false;
    }

    public Bullet shoot(int row, int col) {
        if(Math.abs(GlobalState.gameTime - lastShoot) >= 1200) {
            lastShoot = GlobalState.gameTime;
            return new Bullet(row, col, bulletType);
        }
        return null;
    }
}
