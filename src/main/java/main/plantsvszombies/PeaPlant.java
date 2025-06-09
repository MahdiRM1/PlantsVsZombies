package main.plantsvszombies;

public abstract class PeaPlant extends Plant{

    private int agoshoot;
    public PeaPlant() {
        agoshoot = -1000;
    }
    public Bullet shoot(int row, int col, int time) {
        if(Math.abs(time - agoshoot) >= 1000) {
            agoshoot = time;
            return new Bullet(row, col);
        }
        return null;
    }
}
