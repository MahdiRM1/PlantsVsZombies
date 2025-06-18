package main.plantsvszombies;

public class Repeater extends PeaPlant{

    private long firstShoot;
    private long lastShoot;

    public Repeater(int row, int col) {
        super(row, col);
        price = 200;
        recharge = 5;
        HP = 200;
        freezeShoot = false;
        firstShoot = timeCreated - 200;
        lastShoot = timeCreated;
    }

    @Override
    public Bullet shoot(int row, int col) {
        long time = GlobalState.gameTime;
        if(Math.abs(time - lastShoot) >= 1000 && Math.abs(time - firstShoot) >= 1200) {
            firstShoot = time;
            return new Bullet(row, col, freezeShoot);
        }
        else if(Math.abs(time - firstShoot) >= 200 && Math.abs(time - lastShoot) >= 1200){
            lastShoot = time;
            return new Bullet(row, col, freezeShoot);
        }
        return null;
    }
}