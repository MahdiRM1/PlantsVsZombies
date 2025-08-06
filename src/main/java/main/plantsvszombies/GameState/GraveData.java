package main.plantsvszombies.GameState;

import main.plantsvszombies.Items.Grave;

import java.io.Serializable;

public class GraveData implements Serializable {

    private final int row, col;
    private final int picNumber;

    public GraveData(Grave grave) {
        row = grave.getRow();
        col = grave.getCol();
        picNumber = grave.getPicNumber();
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
}
