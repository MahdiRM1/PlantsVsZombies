package main.plantsvszombies;

import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public abstract class Zombie {

    protected int HP;
    protected int speed;
    protected ImageView picture;
    protected final int row;
    protected int col;
    protected ZombieState state;
    protected long freezeTime;
    private static final int boomDiePictureNum = 27;
    private static final Image[] boomDiePictures = new Image[boomDiePictureNum];
    private int nowPic;
    private Plant plantToEat;

    static {
        for (int i = 0; i < boomDiePictureNum; i++) {
            boomDiePictures[i] = new Image("file:Pictures/ZombiePicture/BoomDie/BoomDie_" + i + ".png");
        }
    }

    public Zombie(ZombieData data) {
        this.row = data.getRow();
        picture = new ImageView();
        Constants.setZombiePicture(picture, row);
        picture.setLayoutX(data.getPicLayoutX());
        col = Constants.getColumnZombie(picture);
        state = ZombieState.WALKING;
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
        if(state == ZombieState.BOOM_DIE) dieAnimation();
        else if(Math.abs(GlobalState.gameTime - freezeTime) <= 5000){
            if(GlobalState.gameTime % 100 != 0) return;
            picture.setEffect(iceEffect());
            switch (state) {
                case WALKING -> walk();
                case EATING -> eatPlant();
                case DIE -> dieAnimation();
                case FREEZE -> {
                    if (Math.abs(GlobalState.gameTime - freezeTime) == 5000) {
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
        else images = boomDiePictures;
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
        picture.setLayoutX(picture.getLayoutX() - (Constants.TILE_SIZE/(speed*20)));
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

    public void setPlantToEat(Plant plantToEat) {
        this.plantToEat = plantToEat;
    }

    protected abstract Image[] getWalkImage();
    protected abstract Image[] getEatImage();
    protected abstract Image[] getDieImage();

    public Plant getPlant(){
        return plantToEat;
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
