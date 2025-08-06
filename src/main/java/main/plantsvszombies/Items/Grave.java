package main.plantsvszombies.Items;

import java.util.Random;

import javafx.scene.image.ImageView;
import main.plantsvszombies.Game.Constants;
import main.plantsvszombies.GameState.GraveData;

public class Grave {

    private final int row, col;
    private final int picNumber;
    private final ImageView picture;

    public Grave(GraveData data) {
        this.row = data.getRow();
        this.col = data.getCol();
        picNumber = data.getPicNumber();
        picture = Constants.setGravePicture(row, col, picNumber);
    }

    public Grave(int row, int col) {
        Random rdm = new Random();
        this.row = row;
        this.col = col;
        picNumber = rdm.nextInt(5) + 1;
        picture = Constants.setGravePicture(row, col, picNumber);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getPicNumber() {
        return picNumber;
    }

    public ImageView getPicture() {
        return picture;
    }
}
