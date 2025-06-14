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
    protected long freezeTime;
    protected int walkPictureNum;
    protected int attackPictureNum;
    protected Image[] walkZombie;
    protected Image[] walkFrozenZombie;
    protected Image[] attackZombie;
    protected Image[] attackFrozenZombie;


    public Zombie(int row) {
        this.row = row;
        col = 9;
        state = ZombieState.WALKING;
        picture = new ImageView();
        Constants.setZombiePicture(picture, row);
        freezeTime = -5000;
    }

    public void damage(boolean isFreezing, long time){
        if(isFreezing) freezeTime = time;
        HP -= 20;
    }

    public void action(long time){
        if(Math.abs(time - freezeTime) <= 5000){
            if(time % 100 != 0) return;
            switch (state) {
                case WALKING -> walk(true);
                case EATING -> eatPlant(true);
            }
        }
        else{
            switch (state) {
                case WALKING -> walk(false);
                case EATING -> eatPlant(false);
            }
        }
    }

    private void changePicture(Image[] images){
        nowPic = (nowPic + 1) % images.length;
        picture.setImage(images[nowPic]);
    }

    public void walk(boolean isFrozen){
        changePicture((isFrozen) ? walkFrozenZombie : walkZombie);
        picture.setLayoutX(picture.getLayoutX() - (Constants.TILE_SIZE/(speed*20)));
        col = Constants.getColumnZombie(picture);
    }

    public void eatPlant(boolean isFrozen){
        plantToEat.damage();
        changePicture((isFrozen) ? attackFrozenZombie : attackZombie);
        if(plantToEat.getHP() <= 0) {
            plantToEat = null;
            state = ZombieState.WALKING;
        }
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
