package main.plantsvszombies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

enum ZombieState{
    WALKING,
    DEAD,
    EATING();

    private Plant plant;

    ZombieState withPlant(Plant plant){
        ZombieState eating = ZombieState.EATING;
        this.plant = plant;
        return eating;
    }

    Plant getPlant(){
        return plant;
    }
}

public abstract class Zombie {

    protected int hp;
    protected int speed;
    protected ImageView picture;
    protected int row , col;
    private int nowPic;
    protected ZombieState state;


    public Zombie(int row) {
        this.row = row;
        col = 9;
        state = ZombieState.WALKING;
        picture = new ImageView();
        Constants.setZombiePicture(picture, row);
    }

    public void eatPlant(Plant plant, Image[] images){
        plant.damage();
        changePicture(images);
    }

    public void damage(){
        hp -= 20;
    }

    public abstract void action();

    private void changePicture(Image[] images){
        nowPic = (nowPic + 1) % 22;
        picture.setImage(images[nowPic]);
    }

    public void move(Image[] images){
        changePicture(images);
        picture.setLayoutX(picture.getLayoutX() - (Constants.TILE_SIZE/(speed*20)));
        col = Constants.getColumnZombie(picture);
    }

    public void setState(ZombieState state) {
        this.state = state;
    }

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

    public ZombieState getState() {
        return state;
    }
}
