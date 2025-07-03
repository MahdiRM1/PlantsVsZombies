package main.plantsvszombies;

public class SnowPea extends PeaPlant{

    public static final int recharge = 10;

    public SnowPea(int row, int col){
        super(row, col);
        price = 175;
        HP = 100;
        bulletType = BulletType.ICE_BULLET;;
    }
}
