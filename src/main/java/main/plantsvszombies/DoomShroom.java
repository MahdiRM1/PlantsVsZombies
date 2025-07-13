package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.List;

public class DoomShroom extends BombPlant implements Shroom{

    private long wakeUpTime;
    private boolean isSleep;
    private static final int imagesNum = 22;
    private static final int sleepImagesNum = 25;
    private static final int doomImagesNum = 10;
    private static final Image[] sleepImages;
    private static final Image[] normalImages;
    private static final Image[] doomImages;
    private boolean finishAnimation;

    static {
        sleepImages = Constants.getArrayImage("Pictures/plantsGifs/DoomShroom/sleep/frame_", sleepImagesNum);
        normalImages = Constants.getArrayImage("Pictures/plantsGifs/DoomShroom/normal/frame_", imagesNum);
        doomImages = Constants.getArrayImage("Pictures/plantsGifs/DoomShroom/doom/frame_", doomImagesNum);
    }

    public DoomShroom(int row, int col, GameMode mode){
        super(row, col);
        price = 125;
        HP = 100;
        rechargeTime = 15;
        isSleep = setIsSleep(mode);
        wakeUpTime = timeCreated;
        finishAnimation = false;
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        if(isSleep){
            updateFrame();
            wakeUpTime = GlobalState.gameTime;
            return false;
        }

        if(!finishAnimation) {
            updateFrame();
            if (!isExploded && nowPic >= getImage().length - 1) {
                nowPic = 0;
                frameUpdateTime = 80;
                return isExploded = true;
            } else if (nowPic >= getImage().length - 1) {
                finishAnimation = true;
                String str = timeCreated != wakeUpTime ? "Day" : "Night";
                gif.setImage(new Image("file:Pictures/plantsGifs/DoomShroom/" + str + "Hole1.png"));
                gif.setLayoutY(gif.getLayoutY() + gif.getFitHeight() / 4);
                Constants.changeScale(gif, 0.5);
            }
        }

        else if(Math.abs(GlobalState.gameTime - wakeUpTime) == 12000){
            String time = timeCreated != wakeUpTime ? "Day" : "Night";
            gif.setImage(new Image("file:Pictures/plantsGifs/DoomShroom/" + time + "Hole2.png"));
        }
        else if(Math.abs(GlobalState.gameTime - wakeUpTime) == 22000) HP = 0;
        return false;
    }

    @Override
    public void action(List<Zombie> zombies){
        gif.setImage(new Image("file:Pictures/plantsGifs/doom.gif"));
        Constants.changeScale(gif, 2);
        gif.setLayoutY(gif.getLayoutY() - gif.getFitHeight()/4);
        for (Zombie z : zombies){
            if(Math.abs(z.getRow() - row) <= 2 &&  Math.abs(z.getCol() - col) <= 2) {
                z.setState(ZombieState.BOOM_DIE);
            }
        }
    }

    @Override
    public void wakeUp() {
        isSleep = false;
    }

    @Override
    public boolean isSleep(){
        return isSleep;
    }

    @Override
    protected Image[] getImage() {
        if (isSleep) return sleepImages;
        if (isExploded) return doomImages;
        return normalImages;
    }
}
