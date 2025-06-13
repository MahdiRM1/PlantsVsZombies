package main.plantsvszombies;

public class SunFlower extends Plant{

    private long lastSunTime;

    public SunFlower(int row, int col, long timeCreated){
        super(row, col, timeCreated);
        price = 50;
        recharge = 5;
        HP = 100;
        lastSunTime = timeCreated;
        gif = Constants.setPlantPicture("SunFlower", row, col);
    }

    public Sun givenSun(long time) {
        if(Math.abs(time - lastSunTime) >= 10000) {
            lastSunTime = time;
            return new Sun(time, SunType.STABLE.setCoordination(row, col));
        }
        return null;
    }
}
