package main.plantsvszombies.Items;

import java.util.List;

import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import main.plantsvszombies.Enums.BulletType;
import main.plantsvszombies.Game.Constants;
import main.plantsvszombies.Zombies.Zombie;

public class Bullet {

    private static final AudioClip[] hit = new AudioClip[4];
    private static final AudioClip[] shoot = new AudioClip[2];
    private final int row;
    private final ImageView picture;
    private final BulletType type;
    static {
        for (int i = 0; i < 4; i++) hit[i] = Constants.setSound("splat" + i, false);
        for (int i = 0; i < 2; i++) shoot[i] = Constants.setSound("shoot" + i, false);
    }

    public Bullet(int row, int col, BulletType type) {
        this.row = row;
        picture = Constants.setBulletPicture(row, col, type);
        this.type = type;
        if (type == BulletType.SHROOM_BULLET) shoot[1].play();
        else shoot[0].play();
    }

    //manage bullet movement
    public void move() {
        picture.setLayoutX(picture.getLayoutX() + Constants.TILE_SIZE / (25));
    }

    public void hit(boolean sound) {
        int hitNum = (int) (Math.random() * 2);
        if (sound) {
            hitNum += 2;
        }
        hit[hitNum].play();
    }

    public boolean checkStrike(List<Zombie> zombies){
        if (layoutX() > Constants.SCREEN_WIDTH) return true;
        for (Zombie z : zombies){
            double abs = layoutX() - z.layoutX();
            if (row == z.getRow() && (-10 < abs && abs < 100) &&
                z.alive() && !z.isHypnotized()){
                    z.damage(this);
                    return true;
            }
        }
        return false;
    }

    public double layoutX(){
        return picture.getLayoutX() + picture.getFitWidth() * 0.5;
    }

    //getters
    public ImageView getPicture() {
        return picture;
    }

    public int getRow() {
        return row;
    }

    public BulletType getType() {
        return type;
    }
}
