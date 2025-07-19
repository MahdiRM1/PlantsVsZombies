package main.plantsvszombies;

import java.util.List;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Zombie {

    protected int HP;
    protected int speed;
    private final ImageView picture;
    private final int row;
    private int col;
    private ZombieState state;
    private long freezeTime;
    private static final int BOOM_DIE_FRAME_COUNTER = 32;
    private static final Image[] BOOM_DIE_FRAMES;
    private int nowPic;
    private Plant plantToEat;

    static {
        BOOM_DIE_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/BoomDie/frame_", BOOM_DIE_FRAME_COUNTER);
    }

    public Zombie(ZombieData data) {
        this.row = data.getRow();
        picture = new ImageView();
        Constants.setZombiePicture(picture, row, col);
        picture.setLayoutX(data.getPicLayoutX());
        col = Constants.getColumnZombie(picture);
        state = ZombieState.WALKING;
        HP = data.getHP();
        freezeTime = -5000;
    }

    public Zombie(int row, int col) {
        this.row = row;
        this.col = col;
        picture = new ImageView();
        Constants.setZombiePicture(picture, row, col);
        state = ZombieState.WALKING;
        freezeTime = -5000;
    }

    //name change for boolean
    public void damage(BulletType bulletType) {
        if (bulletType == BulletType.ICE_BULLET) {
            updateFreezeTime();
        }
        HP -= 20;
    }

    public void updateFreezeTime() {
        freezeTime = GlobalState.gameTime;
    }

    public void action() {
        boolean iceCondition = Math.abs(GlobalState.gameTime - freezeTime) <= 5000;
        int updateFrameTime = iceCondition ? 80 : 40;
        if (GlobalState.gameTime % updateFrameTime != 0) return;

        if(state != ZombieState.HYPNOTIZED)
            picture.setEffect(iceCondition ? effect(0.6, 0.3, 0.2, 0.1) : null);
        switch (state) {
            case WALKING, HYPNOTIZED -> walk(state == ZombieState.HYPNOTIZED);
            case EATING -> eatPlant();
            case DIE, BOOM_DIE -> dieAnimation();
            case FREEZE -> {
                if (Math.abs(GlobalState.gameTime - freezeTime) >= 4950) {
                    freezeTime = GlobalState.gameTime;
                    state = ZombieState.WALKING;
                }
            }
        }
    }

    private Effect effect(double hue, double saturation, double brightness, double contrast) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(hue);
        colorAdjust.setSaturation(saturation);
        colorAdjust.setBrightness(brightness);
        colorAdjust.setContrast(contrast);
        return colorAdjust;
    }

    private void dieAnimation() {
        Image[] images = (state == ZombieState.DIE) ? getDieImage() : BOOM_DIE_FRAMES;
        if (nowPic >= images.length - 1) {
            state = ZombieState.DEAD;
            return;
        }
        changePicture(images);
    }

    private void changePicture(Image[] images) {
        nowPic = (nowPic + 1) % images.length;
        picture.setImage(images[nowPic]);
    }

    public void walk(boolean hypnotized) {
        changePicture(getWalkImage());
        int sign = hypnotized ? -1 : 1;
        picture.setLayoutX(picture.getLayoutX() - (sign)*(Constants.TILE_SIZE / (speed * 1000.0 / 40)));
        col = Constants.getColumnZombie(picture);
    }

    public void eatPlant() {
        plantToEat.damage();
        changePicture(getEatImage());
        if (plantToEat.getHP() <= 0) {
            plantToEat = null;
            state = ZombieState.WALKING;
        }
    }

    protected abstract Image[] getWalkImage();

    protected abstract Image[] getEatImage();

    protected abstract Image[] getDieImage();

    //checks if a zombie has reached a plant
    private Plant plantCollision(List<Plant> plants) {
        for (Plant plant : plants) {
            if (plant.getRow() == row && plant.getCol() == col) {
                return plant;
            }
        }
        return null;
    }

    public void updateState(List<Plant> plants) {

        if (state == ZombieState.DIE || state == ZombieState.DEAD
                || state == ZombieState.BOOM_DIE || state == ZombieState.HYPNOTIZED) return;

        if (HP <= 0) {
            if (state == ZombieState.EATING) {
                plantToEat.resetDamageCaused();
            }
            state = ZombieState.DIE;
            nowPic = 0;
            return;
        }

        Plant plant = plantCollision(plants);
        if (plant != null) {
            if (plant instanceof DoomShroom ds && !ds.isSleep()) return;
            state = ZombieState.EATING;
            plantToEat = plant;
        } else {
            if (state != ZombieState.FREEZE) state = ZombieState.WALKING;
        }
    }

    public void setState(ZombieState state) {
        if (this.state == state) {
            return;
        }
        this.state = state;
        nowPic = 0;
    }

    public void hypnosis(){
        picture.setScaleX(-1);
        picture.setEffect(effect(0.9, 0.4, 0.4, 0.2));
        state = ZombieState.HYPNOTIZED;
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
