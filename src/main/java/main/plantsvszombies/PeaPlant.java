package main.plantsvszombies;

public abstract class PeaPlant extends Plant{

    private long lastShoot;
    protected boolean freezeShoot;

    public PeaPlant(int row, int col) {
        super(row, col);
        lastShoot = timeCreated;
    }

    public Bullet shoot(int row, int col) {
        if(Math.abs(GlobalState.gameTime - lastShoot) >= 1000) {
            lastShoot = GlobalState.gameTime;
            return new Bullet(row, col, freezeShoot);
        }
        return null;
    }
}
