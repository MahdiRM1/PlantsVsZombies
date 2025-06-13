package main.plantsvszombies;

public class WallNut extends Nut{

    public WallNut(int row, int col, long timeCreated){
        super(row, col, timeCreated);
        price = 50;
        HP = 250;
        recharge = 5;
        gif = Constants.setPlantPicture("WallNut", row, col);
    }
}
