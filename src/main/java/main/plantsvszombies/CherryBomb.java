package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.List;

public class CherryBomb extends BombPlant{

    private static final Image[] images;
    private static final Image[] boomImages;
    private static final int imagesNum = 14;
    private static final int boomImagesNum = 13;

    static {
        images = Constants.getArrayImage("Pictures/plantsGifs/CherryBomb/normal/frame_", imagesNum);
        boomImages = Constants.getArrayImage("Pictures/plantsGifs/CherryBomb/boom/frame_", boomImagesNum);
    }

    public CherryBomb(int row, int col){
        super(row, col);
        price = 150;
        HP = 100;
        rechargeTime = 15;
    }

    @Override
    protected Image[] getImage() {
        return isExploded ? boomImages : images;
    }

    @Override
    public void action(List<Zombie> zombies){
        gif.setImage(boomImages[0]);
        Constants.changeScale(gif, 2.5);
        for (Zombie z : zombies){
            if(Math.abs(z.getRow() - row) <= 1 &&  Math.abs(z.getCol() - col) <= 1)
                z.setState(ZombieState.BOOM_DIE);
        }
    }
}
