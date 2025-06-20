package main.plantsvszombies;

public class Repeater extends PeaPlant{

    private static long lastSelection;
    public static final int recharge = 10;
    private long firstShoot;
    private long lastShoot;


    public Repeater(int row, int col){
        super(row, col);
        price = 200;
        HP = 100;
        freezeShoot = false;
        firstShoot = timeCreated - 200;
        lastShoot = timeCreated;
        lastSelection = GlobalState.gameTime;
    }

    @Override
    public Bullet shoot(int row, int col) {
        long time = GlobalState.gameTime;
        if(Math.abs(time - lastShoot) >= 1500 && Math.abs(time - firstShoot) >= 1700) {
            firstShoot = time;
            return new Bullet(row, col, freezeShoot);
        }
        else if(Math.abs(time - firstShoot) >= 200 && Math.abs(time - lastShoot) >= 1700){
            lastShoot = time;
            return new Bullet(row, col, freezeShoot);
        }
        return null;
    }

    @Override
    public long getLastSelection() {
        return lastSelection;
    }

    @Override
    public void setLastSelection(long lastSelection) {
        Repeater.lastSelection = lastSelection;
    }

    public static double rechargeCheck(){
        return ((double)GlobalState.gameTime - lastSelection) / recharge * 1000;
    }
}