package main.plantsvszombies;

public class WallNut extends NutPlant {

    public static final int recharge = 20;

    public WallNut(int row, int col){
        super(row, col);
        price = 50;
        HP = maxHP =250;
    }
}
