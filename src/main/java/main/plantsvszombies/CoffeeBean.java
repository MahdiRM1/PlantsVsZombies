package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.List;

public class CoffeeBean extends Plant{

    private boolean isEaten;
    private final Shroom shroom;
    private static final Image[] images;
    private static final Image[] eatImages;
    private static final int imagesNum = 9;
    private static final int eatImagesNum = 14;

    static {
        images = Constants.getArrayImage("Pictures/plantsGifs/CoffeeBean/normal/frame_", imagesNum);
        eatImages = Constants.getArrayImage("Pictures/plantsGifs/CoffeeBean/eat/frame_", eatImagesNum);
    }

    public CoffeeBean(int row, int col, Shroom shroom){
        super(row, col);
        price = 75;
        HP = 100;
        rechargeTime = 10;
        this.shroom = shroom;
        isEaten = false;
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies){
        updateFrame();
        if(!isEaten && Math.abs(GlobalState.gameTime - timeCreated) >= 1500) {
            nowPic = 0;
            isEaten = true;
        }
        else if(isEaten && nowPic >= getImage().length - 1) {
            HP = 0;
            return true;
        }
        return false;
    }

    @Override
    protected Image[] getImage() {
        if (isEaten) return eatImages;
        return images;
    }

    public void action(){
        shroom.wakeUp();
    }
}
