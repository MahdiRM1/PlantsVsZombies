package main.plantsvszombies.Plants.BombPlants;

import javafx.scene.image.Image;
import main.plantsvszombies.Enums.ZombieState;
import main.plantsvszombies.Game.Tools.*;
import main.plantsvszombies.Zombies.Zombie;

import java.util.List;

public class Squash extends BombPlant{
    private static final Image[] NORMAL_FRAMES;
    private static final Image[] ATTACK_FRAMES;
    private static final int FRAME_COUNT = 50;
    private static final int ATTACK_FRAME_COUNT = 60;
    private Zombie zombie = null;
    private double maxTop = -1;
    private double maxBottom = -1;

    static {
        NORMAL_FRAMES = ImageFactory.arrayImage("plantPictures/Squash/normal/frame_", FRAME_COUNT);
        ATTACK_FRAMES = ImageFactory.arrayImage("plantPictures/Squash/attack/frame_", ATTACK_FRAME_COUNT);
    }

    public Squash(int row, int col) {
        super(row, col);
        price = 50;
        HP = 100;
        rechargeTime = 25;
    }

    private void zombieCollision(List<Zombie> zombies){
        for (Zombie z : zombies)
            if (this.getRow() == z.getRow() && Math.abs(this.col - z.getCol()) < 2 && !z.isHypnotized()) {
                see(z);
                return;
            }
    }

    @Override
    public void damage(){
        HP -= 5;
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        updateFrame();

        if (zombie == null) zombieCollision(zombies);
        else {
            move();
            return nowPic == getImage().length - 1;
        }

        return false;
    }

    private void move(){
        if (nowPic > 25 && nowPic < 44) {
            double up = (maxTop - (picture.getLayoutY()+picture.getFitHeight()))/3;
            double toZombie = (zombie.layoutX() - this.layoutX())/3;
            ImageFactory.setNodePosition(picture, picture.getLayoutX() + toZombie, picture.getLayoutY() + up);
        }
        else if(nowPic > 44){
            double down = (maxBottom - (picture.getLayoutY()+picture.getFitHeight()))/2;
            ImageFactory.setNodePosition(picture, picture.getLayoutX(), picture.getLayoutY() + down);
        }

        if (nowPic == 40) SoundManager.playSound("bonk");
    }

    private void see(Zombie z){
        SoundManager.playSound("hmm");
        zombie = z;
        maxTop = z.getPicture().getLayoutY() + z.getPicture().getFitHeight()/8;
        maxBottom = maxTop + z.getPicture().getFitHeight();
        if (zombie.layoutX() < this.layoutX()) picture.setScaleX(-1);
        nowPic = 0;
    }

    @Override
    public void action(List<Zombie> zombies) {
        for (Zombie z : zombies)
            if (row == z.getRow() && zombie.getCol() == z.getCol() && !z.isHypnotized())
                z.setState(ZombieState.DEAD);
        die();
    }

    @Override
    protected Image[] getImage() {
        if (zombie != null) return ATTACK_FRAMES;
        return NORMAL_FRAMES;
    }
}
