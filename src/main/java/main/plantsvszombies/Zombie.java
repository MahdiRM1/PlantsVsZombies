package main.plantsvszombies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

enum ZombieState{ WALKING, DEAD1, DEAD2, EATING }

public abstract class Zombie {

    protected int HP;
    protected int speed;
    protected ImageView picture;
    protected final int row;
    protected int col;
    protected ZombieState state;
    protected long freezeTime;
    private static int dieImagesNum = 14;
    private static Image[] dieImages = new Image[dieImagesNum];
    private static Image[] frozenDieImages = new Image[dieImagesNum];
    private int nowPic;
    private Plant plantToEat;

    static {
        for (int i = 0; i < dieImagesNum; i++) {
            dieImages[i] = new Image("file:Pictures/ZombiePicture/ZombieDie/ZombieDie_" + i +".png");
            frozenDieImages[i] = new Image("file:Pictures/ZombiePicture/FrozenZombieDie/ZombieDie_" + i + ".png");
        }
    }

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
                case DEAD1 -> dieAnimation(true);
            }
        }
        else{
            switch (state) {
                case WALKING -> walk(false);
                case EATING -> eatPlant(false);
                case DEAD1 -> dieAnimation(false);
            }
        }
    }

    private void dieAnimation(boolean isFrozen){
        if(nowPic >= dieImagesNum-1) {
            state = ZombieState.DEAD2;
            return;
        }
        Image[] images = (isFrozen) ? frozenDieImages : dieImages;
        changePicture(images);
    }

    private void changePicture(Image[] images){
        nowPic = (nowPic + 1) % images.length;
        picture.setImage(images[nowPic]);
    }

    public void walk(boolean isFrozen){
        Image[] images = getWalkImage(isFrozen);
        changePicture(images);
        picture.setLayoutX(picture.getLayoutX() - (Constants.TILE_SIZE/(speed*20)));
        col = Constants.getColumnZombie(picture);
    }

    public void eatPlant(boolean isFrozen){
        plantToEat.damage();
        Image[] images = getEatImage(isFrozen);
        changePicture(images);
        if(plantToEat.getHP() <= 0) {
            plantToEat = null;
            state = ZombieState.WALKING;
        }
    }

    public void setPlantToEat(Plant plantToEat) {
        this.plantToEat = plantToEat;
    }

    protected abstract Image[] getWalkImage(boolean isFrozen);
    protected abstract Image[] getEatImage(boolean isFrozen);

    public Plant getPlant(){
        return plantToEat;
    }

    public void setState(ZombieState state) {
        this.state = state;
    }

    public void resetNowPic(){
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
