package main.plantsvszombies;

public class TallNut extends NutPlant {

    public static final int recharge = 20;

    public TallNut(int row, int col){
        super(row, col);
        price = 125;
        HP = maxHP = 400;
    }
}
