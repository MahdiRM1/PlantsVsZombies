package main.plantsvszombies;

import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Plant {

    protected final int row, col;
    protected final long timeCreated;
    protected int price;
    protected double HP;
    protected ImageView picture;
    protected int rechargeTime;
    protected int frameUpdateTime;
    protected int nowPic = 0;

    public Plant(int row, int col) {
        this.row = row;
        this.col = col;
        this.timeCreated = GlobalState.gameTime;
        picture = Constants.setPlantPicture(this.getClass().getSimpleName(), row, col);
        frameUpdateTime = 40;
    }

    public void damage() {
        HP -= 20;
    }

    protected void updateFrame() {
        if (GlobalState.gameTime % frameUpdateTime != 0) return;

        Image[] frame = getImage();
        nowPic++;
        nowPic %= frame.length;
        picture.setImage(frame[nowPic]);
    }

    public double layoutX(){
        return picture.getLayoutX() + picture.getFitWidth() * 0.5;
    }

    public boolean checkDied(){
        return plant.getHP() <= 0;
    }

    public abstract boolean actionHappens(List<Zombie> zombies);

    protected abstract Image[] getImage();

    public void setHP(double HP) {
        this.HP = HP;
    }

    public ImageView getPicture() {
        return picture;
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

    public int getRechargeTime() {
        return rechargeTime;
    }
}
