package main.plantsvszombies;

public class SnowPea extends PeaPlant{

    public SnowPea(int row, int col, long timeCreated) {
        super(row, col, timeCreated);
        price = 175;
        recharge = 5;
        HP = 100;
        gif = Constants.setPlantPicture("SnowPea", row, col);
        freezeShoot = true;
    }

}
