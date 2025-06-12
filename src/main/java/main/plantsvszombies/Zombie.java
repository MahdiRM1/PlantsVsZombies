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
        picture.setFitWidth(Constants.ZOMBIE_PIC_WEIGHT);
        picture.setFitHeight(Constants.ZOMBIE_PIC_HEIGHT);
        picture.setLayoutY(Constants.height - picture.getFitHeight() - (4-row) * Constants.TILE_SIZE - Constants.width/24);
        picture.setLayoutX(Constants.width);
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
        picture.setLayoutX(picture.getLayoutX() - Constants.TILE_SIZE/(speed*20));
        col = (int)((picture.getLayoutX() + picture.getFitWidth() / 1.5 - Constants.height/2.62) / Constants.TILE_SIZE);
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
