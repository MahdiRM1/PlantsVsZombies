package main.plantsvszombies;

public class SunFlower extends Plant{

    public static final int recharge = 10;
    private long lastSunTime;

    public SunFlower(int row, int col){
        super(row, col);
        price = 50;
        HP = 100;
        lastSunTime = timeCreated;
    }

    //manages time to produce sun by sunFlowers
    public Sun givenSun() {
        if(Math.abs(GlobalState.gameTime - lastSunTime) >= 10000) {
            lastSunTime = GlobalState.gameTime;
            return new Sun(SunType.STABLE.setCoordination(row, col));
        }
        return null;
    }
}
