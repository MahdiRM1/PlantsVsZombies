package main.plantsvszombies;

import javafx.scene.image.ImageView;

public abstract class Plant {
    protected final int row, col;
    protected final long timeCreated;
    protected int price;
    protected int recharge;
    protected double hp;
    protected ImageView gif;
    double damageCaused = 0;

    public Plant(int row, int col, long timeCreated){
        this.row = row;
        this.col = col;
        this.timeCreated = timeCreated;
    }

    public void damage(){
        damageCaused += 2.5;
        if(damageCaused == 25) {
            hp -= 25;
            damageCaused = 0;
        }
    }

    public void resetDamageCaused(){
        damageCaused = 0;
    }

    public ImageView getGif() {
        return gif;
    }

    public double getHp() {
        return hp;
    }

    public int getPrice() {
        return price;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
