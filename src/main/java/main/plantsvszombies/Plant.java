package main.plantsvszombies;

import javafx.scene.image.ImageView;

public abstract class Plant {
    protected int price;
    protected int recharge;
    protected double hp;
    protected ImageView gif;
    protected int row, col;
    double damageCaused = 0;

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

    public void setCoordination(int row, int col){
        this.row = row;
        this.col = col;
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
}
