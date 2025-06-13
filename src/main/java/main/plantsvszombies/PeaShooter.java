package main.plantsvszombies;

public class PeaShooter extends PeaPlant{

    public PeaShooter(int row, int col, long timeCreated) {
        super(row, col, timeCreated);
       price = 100;
       recharge = 5;
       hp = 100;
       gif = Constants.setPlantPicture("Pea");
    }

}
