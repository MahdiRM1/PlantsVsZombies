package main.plantsvszombies;

import java.util.List;

public class SunFlower extends Plant{

    private long lastSunTime;

    public SunFlower(int row, int col){
        super(row, col);
        price = 50;
        HP = 100;
        rechargeTime = 10;
        lastSunTime = timeCreated;
    }
    @Override
    public boolean actionHappens(List<Zombie> zombies){
        return Math.abs(GlobalState.gameTime - lastSunTime) >= 10000;
    }

    public Sun action(){
        lastSunTime = GlobalState.gameTime;
        return new Sun(SunType.RISEN.setCoordination(row, col));
    }
}
