package main.plantsvszombies;

public abstract class PeaPlant extends Plant{

    private long lastShoot;
    protected boolean freezeShoot;

    public PeaPlant(int row, int col, long timeCreated) {
        super(row, col, timeCreated);
        lastShoot = timeCreated;
    }

    public Bullet shoot(int row, int col, long time) {
        if(Math.abs(time - lastShoot) >= 1000) {
            lastShoot = time;
            return new Bullet(row, col, freezeShoot);
        }
        return null;
    }
}
