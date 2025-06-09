package main.plantsvszombies;

import javafx.scene.image.ImageView;

public abstract class Zombie {

    protected int hp;
    protected int speed;
    protected ImageView picture;
    protected int row , col;

    public Zombie(int row) {
        this.row = row;
        col = 9;
    }

    public void eatPlant(Plant plant){
        plant.damage();
    }

    public void damage(){
        hp -= 20;
    }

    public abstract void move();

    public int getHp() {
        return hp;
    }

    public ImageView getPicture() {
        return picture;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
