package main.plantsvszombies;

public class TallNut extends NutPlant {

    private static long lastSelection;
    public static final int recharge = 20;

    public TallNut(int row, int col){
        super(row, col);
        price = 125;
        HP = maxHP = 400;
        lastSelection = GlobalState.gameTime;
    }


    @Override
    public long getLastSelection() {
        return lastSelection;
    }

    @Override
    public void setLastSelection(long lastSelection) {
        TallNut.lastSelection = lastSelection;
    }

    public static double rechargeCheck(){
        return ((double)GlobalState.gameTime - lastSelection) / recharge * 1000;
    }
}
