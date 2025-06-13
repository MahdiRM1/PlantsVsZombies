package main.plantsvszombies;

public class TallNut extends Nut{

    public TallNut(int row, int col, long timeCreated){
        super(row, col, timeCreated);
        price = 150;
        HP = 400;
        recharge = 5;
        gif = Constants.setPlantPicture("TallNut", row, col);
    }

}
