package main.plantsvszombies;

public class WallNut extends NutPlant {

    private static long lastSelection;
    public static final int recharge = 20;

    public WallNut(int row, int col){
        super(row, col);
        price = 50;
        HP = maxHP =250;
        lastSelection = GlobalState.gameTime;
    }

    @Override
    public long getLastSelection() {
        return lastSelection;
    }

    @Override
    public void setLastSelection(long lastSelection) {
        WallNut.lastSelection = lastSelection;
    }

    public static double rechargeCheck(){
        return ((double)GlobalState.gameTime - lastSelection) / recharge * 1000;
    }
}
