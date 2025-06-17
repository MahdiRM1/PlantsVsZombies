package main.plantsvszombies;

public class SunFlower extends Plant{

    private long lastSunTime;

    public SunFlower(int row, int col){
        super(row, col);
        price = 50;
        recharge = 5;
        HP = 100;
        lastSunTime = timeCreated;
    }

    public Sun givenSun() {
        if(Math.abs(GlobalState.gameTime - lastSunTime) >= 10000) {
            lastSunTime = GlobalState.gameTime;
            return new Sun(SunType.STABLE.setCoordination(row, col));
        }
        return null;
    }
}
