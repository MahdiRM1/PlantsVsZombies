package main.plantsvszombies.Zombies;

import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import main.plantsvszombies.Enums.BulletType;
import main.plantsvszombies.Enums.ZombieState;
import main.plantsvszombies.Game.Tools.*;
import main.plantsvszombies.GameState.ZombieData;
import main.plantsvszombies.Items.Bullet;
import main.plantsvszombies.Plants.Plant;


public abstract class Zombie {

    private static final AudioClip[] chomp = new AudioClip[2];
    private static final AudioClip[] groan = new AudioClip[7];
    private static final AudioClip gulp;
    private static final int HEAD_FRAME_COUNT = 24;
    protected static final Image[] HEAD_FRAMES;
    protected long freezeTime;
    protected double HP;
    protected int speed;
    protected ZombieState state;
    private final ImageView picture;
    private final ImageView secondPicture;
    private final int row;
    private int col;
    private int nowPic;
    private boolean hypnotized;
    private Object toEat;
    private long lastBite;

    static {
        HEAD_FRAMES = ImageFactory.arrayImage("ZombiePicture/OriginalZombie/head/frame_", HEAD_FRAME_COUNT);
        gulp = SoundManager.setSound("gulp");
        chomp[0] = SoundManager.setSound("chomp");
        chomp[1] = SoundManager.setSound("chompsoft");
        for (int soundNum = 0; soundNum < 7; soundNum++)
            groan[soundNum] = SoundManager.setSound("groan" + soundNum);
    }

    public Zombie(ZombieData data) {
        this.row = data.getRow();
        picture = new ImageView();
        secondPicture = new ImageView();
        ImageFactory.createZombiePicture(picture, secondPicture, row, col);
        ImageFactory.setNodePosition(picture, data.getPicLayoutX(), picture.getLayoutY());
        if (data.isHypnotized()) hypnosis();
        else state = ZombieState.WALKING;
        HP = data.getHP();
        freezeTime = -5000;
    }

    public Zombie(int row, int col) {
        this.row = row;
        picture = new ImageView();
        secondPicture = new ImageView();
        ImageFactory.createZombiePicture(picture, secondPicture, row, col);
        state = ZombieState.WALKING;
        freezeTime = -5000;
        zombieSound();
    }

    private void zombieSound(){
        int soundNum = this.getClass().getSimpleName().equals("Imp") ? 6 : (int)(Math.random() * 6);
        groan[soundNum].play();
    }

    private void damage(){
        if (hypnotized) HP -= 15;
        else HP -= 20;

        if (80 < HP && HP <= 100 && !isHypnotized()) zombiePartFall();
    }

    public void damage(Bullet bullet) {
        if (bullet.getType() == BulletType.ICE_BULLET) updateFreezeTime();
        damage();
        bullet.hit(HP > 100 && !this.getClass().getSimpleName().equals("ConeheadZombie"));
    }

    public void action() {
        boolean iceCondition = Math.abs(Constants.gameTime - freezeTime) <= 5000;
        int updateFrameTime = iceCondition ? 80 : 40;
        if (Constants.gameTime % updateFrameTime != 0) return;
        if(!hypnotized)
            picture.setEffect(iceCondition ? Utils.effect(0.6, 0.3, 0.2, 0.1) : null);
        updateFrame();

        switch (state) {
            case WALKING -> walk(hypnotized);
            case EATING -> eat();
            case DIE, BOOM_DIE -> die();
            case FREEZE -> {
                if (Math.abs(Constants.gameTime - freezeTime) >= 4950) {
                    freezeTime = Constants.gameTime;
                    state = ZombieState.WALKING;
                }
            }
            case DEAD -> {}
        }
    }

    private void die() {
        if (nowPic <= 1 && state == ZombieState.DIE) zombiePartFall();
        Image[] images = getZombiePictures();
        if (nowPic >= images.length - 1) state = ZombieState.DEAD;
    }

    private void zombiePartFall(){
        if (this.getClass().getSimpleName().equals("Imp")) return;

        Image[] images = getPictures();
        var ref = new Object() {
            int headPic = 0;
        };
        secondPicture.setLayoutX(picture.getLayoutX());
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(40), event -> {
            if (ref.headPic < images.length - 1) ref.headPic++;
            secondPicture.setImage(images[ref.headPic]);
        }));
        tl.setOnFinished(e -> secondPicture.setImage(null));
        tl.setCycleCount(40);
        tl.play();
    }

    private void updateFrame() {
        if (state == ZombieState.FREEZE) return;
        Image[] images = getZombiePictures();
        nowPic = (nowPic + 1) % images.length;
        picture.setImage(images[nowPic]);
    }

    public void walk(boolean hypnotized) {
        int sign = hypnotized ? -1 : 1;
        picture.setLayoutX(picture.getLayoutX() - (sign)*(Constants.TILE_SIZE / (speed * 1000.0 / 40)));
        col = Utils.getColumnZombie(layoutX());
    }

    private void eat(){
        if (Math.abs(Constants.gameTime - lastBite) < 500) return;

        switch (toEat){
            case Zombie z -> eatZombie(z);
            case Plant p -> eatPlant(p);
            default -> { return; }
        }
        lastBite = Constants.gameTime;
    }

    private void eatZombie(Zombie zombie){
        if (alive()) zombie.damage();

        chomp[nowPic % 2].play();
        if (zombie.getHP() <= 0) {
            toEat = null;
            state = ZombieState.WALKING;
        }
    }

    private void eatPlant(Plant plant) {
        plant.damage();
        if (plant.getHP() < 5) {
            toEat = null;
            state = ZombieState.WALKING;
            gulp.play();
        }
        else chomp[nowPic % 2].play();
    }

    private Object collision(List<Plant> plants, List<Zombie> zombies) {
        if (hypnotized) return zombieCollision(zombies);
        else return plantCollision(plants);
    }

    // ?checks if a zombie has reached a plant
    private Plant plantCollision(List<Plant> plants) {
        for (Plant plant : plants) {
            if (Utils.checkCollision(layoutX(), plant.layoutX(), row, plant.getRow()) && plant.getHP() > 0)
                return plant;
        }
        return null;
    }

    // ?checks if a zombie has reached a zombie
    private Zombie zombieCollision(List<Zombie> zombies){
        for (Zombie zombie : zombies) {
            if (alive() && !zombie.isHypnotized()) {
                if (Utils.checkCollision(layoutX(), zombie.layoutX(), row, zombie.getRow()) && toEat != zombie) {
                    zombie.zombieToEat(this);
                    return zombie;
                }
            }
        }
        return null;
    }

    public void updateState(List<Plant> plants, List<Zombie> zombies) {

        if (!alive() || state == ZombieState.FREEZE) return;

        if (HP <= 0) {
            state = ZombieState.DIE;
            nowPic = 0;
            return;
        }

        Object eat = collision(plants, zombies);
        if (toEat != null) return;

        if (eat != null){
            state = ZombieState.EATING;
            toEat = eat;
        }
        else state = ZombieState.WALKING;
    }

    public boolean alive(){
        return switch (state) {
            case DIE, BOOM_DIE, DEAD -> false;
            default -> true;
        };
    }

    public boolean checkDied(){
        return (isHypnotized() && layoutX() > Constants.SCREEN_WIDTH) ||
                state == ZombieState.DEAD;
    }

    public final void hypnosis(){
        picture.setScaleX(-1);
        picture.setLayoutX(picture.getLayoutX() + Constants.ZOMBIE_PIC_WIDTH/5);
        picture.setEffect(Utils.effect(1, 1, 0.3, 0.3));
        state = ZombieState.WALKING;
        toEat = null;
        hypnotized = true;
    }

    public void zombieToEat(Zombie zombie){
        toEat = zombie;
        state = ZombieState.EATING;
    }

    public final double layoutX(){
        return picture.getLayoutX() + picture.getFitWidth() * 0.5;
    }

    public void setState(ZombieState state) {
        if (this.state == state) {
            return;
        }
        this.state = state;
        nowPic = 0;
    }

    public void updateFreezeTime() {
        freezeTime = Constants.gameTime;
    }

    protected abstract Image[] getZombiePictures();

    protected abstract Image[] getPictures();

    public double getHP() {
        return HP;
    }

    public ImageView getPicture() {
        return picture;
    }

    public ImageView getSecondPicture() {
        return secondPicture;
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

    public boolean isHypnotized() {
        return hypnotized;
    }
}
