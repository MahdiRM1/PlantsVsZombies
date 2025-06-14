package main.plantsvszombies;

public class Repeater extends PeaPlant{

    private long firstShoot;
    private long lastShoot;

    public Repeater(int row, int col, long timeCreated) {
        super(row, col, timeCreated);
        price = 100;
        recharge = 5;
        HP = 100;
        gif = Constants.setPlantPicture("Repeater", row, col);
        firstShoot = timeCreated - 200;
        lastShoot = timeCreated;
    }

    @Override
    public Bullet shoot(int row, int col, long time) {
        if(Math.abs(time - lastShoot) >= 1000 && Math.abs(time - firstShoot) >= 1200) {
            firstShoot = time;
            return new Bullet(row, col);
        }
        else if(Math.abs(time - firstShoot) >= 200 && Math.abs(time - lastShoot) >= 1200){
            lastShoot = time;
            return new Bullet(row, col);
        }
        return null;
    }
}