package main.plantsvszombies;

import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.List;

public abstract class Zombie {

    protected int HP;
    protected int speed;
    protected ImageView picture;
    protected final int row;
    protected int col;
    protected ZombieState state;
    protected long freezeTime;
    private static final int boomDiePictureNum = 32;
    private static final Image[] boomDiePictures;
    private int nowPic;
    private Plant plantToEat;

    static {boomDiePictures = Constants.getArrayImage("Pictures/ZombiePicture/BoomDie/frame_", boomDiePictureNum);}

    public Zombie(ZombieData data) {
        this.row = data.getRow();
        picture = new ImageView();
        Constants.setZombiePicture(picture, row);
        picture.setLayoutX(data.getPicLayoutX());
        col = Constants.getColumnZombie(picture);
        state = ZombieState.WALKING;
        HP = data.getHP();
        freezeTime = -5000;
    }

    public Zombie(int row) {
        this.row = row;
        picture = new ImageView();
        Constants.setZombiePicture(picture, row);
        col = Constants.getColumnZombie(picture);
        state = ZombieState.WALKING;
        freezeTime = -5000;
    }

    //name change for boolean
    public void damage(BulletType bulletType){
        if(bulletType == BulletType.ICE_BULLET) updateFreezeTime();
        HP -= 20;
    }

    public void updateFreezeTime(){
        freezeTime = GlobalState.gameTime;
    }

    public void action(){
        if(GlobalState.gameTime % 40 != 0) return;

        if(state == ZombieState.BOOM_DIE) dieAnimation();
        else if(Math.abs(GlobalState.gameTime - freezeTime) <= 5000){
            if(GlobalState.gameTime % 80 != 0) return;
            picture.setEffect(iceEffect());
            switch (state) {
                case WALKING -> walk();
                case EATING -> eatPlant();
                case DIE -> dieAnimation();
                case FREEZE -> {
                    if (Math.abs(GlobalState.gameTime - freezeTime) >= 4950) {
                        freezeTime = GlobalState.gameTime;
                        state = ZombieState.WALKING;
                    }
                }
            }
        }

        else{
            picture.setEffect(null);
            switch (state) {
                case WALKING -> walk();
                case EATING -> eatPlant();
                case DIE -> dieAnimation();
            }
        }
    }

    private Effect iceEffect(){
        ColorAdjust blueTone = new ColorAdjust();
        blueTone.setHue(0.6);
        blueTone.setSaturation(0.3);
        blueTone.setBrightness(0.2);
        blueTone.setContrast(0.1);

        DropShadow iceGlow = new DropShadow();
        iceGlow.setColor(Color.CORNFLOWERBLUE);
        iceGlow.setRadius(15);
        iceGlow.setInput(blueTone);

        return iceGlow;
    }

    private void dieAnimation(){
        Image[] images;
        if(state == ZombieState.DIE) images = getDieImage();
        else {
            if (GlobalState.gameTime % 40 != 0) return;
            images = boomDiePictures;
        }
        if(nowPic >= images.length - 1) {
            state = ZombieState.DEAD;
            return;
        }
        changePicture(images);
    }

    private void changePicture(Image[] images){
        nowPic = (nowPic + 1) % images.length;
        picture.setImage(images[nowPic]);
    }

    public void walk(){
        changePicture(getWalkImage());
        picture.setLayoutX(picture.getLayoutX() - (Constants.TILE_SIZE/(speed*1000.0/40)));
        col = Constants.getColumnZombie(picture);
    }

    public void eatPlant(){
        plantToEat.damage();
        changePicture(getEatImage());
        if(plantToEat.getHP() <= 0) {
            plantToEat = null;
            state = ZombieState.WALKING;
        }
    }

    protected abstract Image[] getWalkImage();
    protected abstract Image[] getEatImage();
    protected abstract Image[] getDieImage();

    public Plant getPlant(){
        return plantToEat;
    }

    //checks if a zombie has reached a plant
    private Plant plantCollision(List<Plant> plants){
        for (Plant plant : plants)
            if (plant.getRow() == row && plant.getCol() == col) return plant;
        return null;
    }

    public void updateState(List<Plant> plants){

        if(state == ZombieState.DIE || state == ZombieState.DEAD || state == ZombieState.BOOM_DIE) return;

        if(HP <= 0) {
            if(state == ZombieState.EATING) plantToEat.resetDamageCaused();
            state = ZombieState.DIE;
            nowPic = 0;
            return;
        }

        Plant plant = plantCollision(plants);
        if(plant != null) {
            if (plant instanceof DoomShroom ds && !ds.isSleep()) return;
            state = ZombieState.EATING;
            plantToEat = plant;
        }
        else {
            if (state != ZombieState.FREEZE)
                state = ZombieState.WALKING;
        }
    }

    public void setState(ZombieState state) {
        if(this.state == state) return;
        this.state = state;
        nowPic = 0;
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
