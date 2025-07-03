package main.plantsvszombies;

public class Repeater extends PeaPlant{

    public static final int recharge = 10;
    private long firstShoot;
    private long lastShoot;


    public Repeater(int row, int col){
        super(row, col);
        price = 200;
        HP = 100;
        bulletType = BulletType.NORMAL_BULLET;
        firstShoot = timeCreated - 350;
        lastShoot = timeCreated - 150;
    }

    @Override
    public Bullet shoot(int row, int col) {
        long time = GlobalState.gameTime;
        if(Math.abs(time - lastShoot) >= 1200 && Math.abs(time - firstShoot) >= 1400) {
            firstShoot = time;
            return new Bullet(row, col, bulletType);
        }
        else if(Math.abs(time - firstShoot) >= 200 && Math.abs(time - lastShoot) >= 1400){
            lastShoot = time;
            return new Bullet(row, col, bulletType);
        }
        return null;
    }
}