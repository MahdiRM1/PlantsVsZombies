package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.List;

public class Jalapeno extends BombPlant{

    private static final Image[] images;
    private static final Image[] boomImages;
    private static final int imagesNum = 13;

    static {
        images = Constants.getArrayImage("Pictures/plantsGifs/Jalapeno/normal/frame_", imagesNum);
        boomImages = Constants.getArrayImage("Pictures/plantsGifs/Jalapeno/attack/frame_", imagesNum);
    }

    public Jalapeno(int row, int col){
        super(row, col);
        price = 125;
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
        gif.setFitWidth(Constants.TILE_SIZE * 9);
        gif.setLayoutX(Constants.SCREEN_WIDTH/4.9);
        for (Zombie z : zombies){
            if(z.getRow() == row) {
                z.setState(ZombieState.BOOM_DIE);
            }
        }
    }
}