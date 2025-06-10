package main.plantsvszombies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

enum ZombieState{WALKING, DEAD, EATING}

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
        picture.setLayoutY(Constants.height - picture.getFitHeight() - (4-row) * Constants.TILE_HEIGHT);
        picture.setLayoutX(Constants.width);
    }

    public void eatPlant(Plant plant){
        plant.damage();
        state = ZombieState.EATING;
    }

    public void damage(){
        hp -= 20;
    }

    public abstract void action();

    public void move(Image[] images){
        nowPic = (nowPic + 1) % 22;
        picture.setImage(images[nowPic]);
        picture.setLayoutX(picture.getLayoutX() - Constants.TILE_WIDTH/(speed*20));
        col = (int)((picture.getLayoutX() + picture.getFitWidth() / 1.5) / Constants.TILE_WIDTH);
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
}
