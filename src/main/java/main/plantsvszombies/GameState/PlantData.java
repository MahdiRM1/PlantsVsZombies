package main.plantsvszombies.GameState;

import main.plantsvszombies.Plants.Shroom;
import main.plantsvszombies.Plants.Plant;

import java.io.Serializable;

public class PlantData implements Serializable {

    private final String type;
    private final int row, col;
    private final double HP;
    private final boolean isSleep;

    public PlantData(Plant plant) {
        type = plant.getClass().getSimpleName();
        row = plant.getRow();
        col = plant.getCol();
        HP = plant.getHP();
        isSleep = !(plant instanceof Shroom s) || s.isSleep();
    }

    public String getType() {
        return type;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public double getHP() {
        return HP;
    }

    public boolean isSleep() {
        return isSleep;
    }
}