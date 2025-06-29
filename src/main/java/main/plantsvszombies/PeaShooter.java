package main.plantsvszombies;

public class PeaShooter extends PeaPlant{

    public static final int recharge = 10;

    public PeaShooter(int row, int col){
        super(row, col);
       price = 100;
       HP = 100;
        bulletType = BulletType.NORMAL_BULLET;;
    }
}
