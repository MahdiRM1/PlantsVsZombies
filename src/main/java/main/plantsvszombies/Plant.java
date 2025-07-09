package main.plantsvszombies;

import javafx.scene.image.ImageView;

import java.util.List;

public abstract class Plant {
    protected final int row, col;
    protected final long timeCreated;
    protected int price;
    protected double HP;
    protected ImageView gif;
    private double damageCaused = 0;
    protected int rechargeTime;

    public Plant(int row, int col){
        this.row = row;
        this.col = col;
        this.timeCreated = GlobalState.gameTime;
        gif = Constants.setPlantPicture(this.getClass().getSimpleName(), row, col);
    }

    public void damage(){
        damageCaused += 2.5;
        if(damageCaused == 25) {
            HP -= 25;
            damageCaused = 0;
        }
    }

    public abstract boolean actionHappens(List<Zombie> zombies);

    public void setHP(double HP){
        this.HP = HP;
    }

    public void resetDamageCaused(){
        damageCaused = 0;
    }

    public ImageView getGif() {
        return gif;
    }

    public double getHP() {
        return HP;
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

    public int getRechargeTime(){
        return rechargeTime;
    }
}
