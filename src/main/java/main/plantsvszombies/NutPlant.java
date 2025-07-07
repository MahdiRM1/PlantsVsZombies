package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.List;

public abstract class NutPlant extends Plant{

    private NutState state;
    protected double maxHP;

    public NutPlant(int row, int col){
        super(row, col);
        state = NutState.FULL_LIFE;
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        if (HP < maxHP / 4 && state == NutState.HALF_LIFE) {
            setEndLife();
        } else if (HP < maxHP / 1.5 && state == NutState.FULL_LIFE) {
            setHalfLife();
        }
        return false;
    }

    private void setHalfLife(){
        gif.setImage(new Image("file:Pictures/plantsGifs/" + this.getClass().getSimpleName() + "_HalfLife.gif"));
        state = NutState.HALF_LIFE;
    }

    private void setEndLife(){
        gif.setImage(new Image("file:Pictures/plantsGifs/" + this.getClass().getSimpleName() + "_EndLife.gif"));
        state = NutState.END_LIFE;
    }
}
