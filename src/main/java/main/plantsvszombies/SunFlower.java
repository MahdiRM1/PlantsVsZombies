package main.plantsvszombies;

public class SunFlower extends Plant{

    private static long lastSelection;
    public static final int recharge = 10;
    private long lastSunTime;

    public SunFlower(int row, int col){
        super(row, col);
        price = 50;
        HP = 100;
        lastSunTime = timeCreated;
        lastSelection = GlobalState.gameTime;
    }

    public Sun givenSun() {
        if(Math.abs(GlobalState.gameTime - lastSunTime) >= 10000) {
            lastSunTime = GlobalState.gameTime;
            return new Sun(SunType.STABLE.setCoordination(row, col));
        }
        return null;
    }

    @Override
    public long getLastSelection() {
        return lastSelection;
    }

    @Override
    public void setLastSelection(long lastSelection) {
        SunFlower.lastSelection = lastSelection;
    }

    public static double rechargeCheck(){
        return ((double)GlobalState.gameTime - lastSelection) / recharge * 1000;
    }
}
