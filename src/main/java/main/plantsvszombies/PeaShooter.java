package main.plantsvszombies;

public class PeaShooter extends PeaPlant{

    private static long lastSelection;
    public static final int recharge = 10;

    public PeaShooter(int row, int col){
        super(row, col);
       price = 100;
       HP = 100;
       freezeShoot = false;
    }

    @Override
    public long getLastSelection() {
        return lastSelection;
    }

    @Override
    public void setLastSelection(long lastSelection) {
        PeaShooter.lastSelection = lastSelection;
    }

    public static double rechargeCheck(){
        return ((double)GlobalState.gameTime - lastSelection) / recharge * 1000;
    }
}
