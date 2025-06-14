package main.plantsvszombies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

enum ZombieState{ WALKING, DEAD, EATING }

public abstract class Zombie {

    protected int HP;
    protected int speed;
    protected ImageView picture;
    protected int row , col;
    private int nowPic;
    protected ZombieState state;
    private Plant plantToEat;


    public Zombie(int row) {
        this.row = row;
        col = 9;
        state = ZombieState.WALKING;
        picture = new ImageView();
        Constants.setZombiePicture(picture, row);
    }

    public void eatPlant(Image[] images){
        plantToEat.damage();
        changePicture(images);
        if(plantToEat.getHP() <= 0) {
            plantToEat = null;
            state = ZombieState.WALKING;
        }
    }

    public void damage(){
        HP -= 20;
    }

    public abstract void action();

    private void changePicture(Image[] images){
        nowPic = (nowPic + 1) % images.length;
        picture.setImage(images[nowPic]);
    }

    public void move(Image[] images){
        changePicture(images);
        picture.setLayoutX(picture.getLayoutX() - (Constants.TILE_SIZE/(speed*20)));
        col = Constants.getColumnZombie(picture);
    }

    public void setPlantToEat(Plant plantToEat) {
        this.plantToEat = plantToEat;
    }

    public Plant getPlant(){
        return plantToEat;
    }

    public void setState(ZombieState state) {
        this.state = state;
    }

    public int getHP() {
        return HP;
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
