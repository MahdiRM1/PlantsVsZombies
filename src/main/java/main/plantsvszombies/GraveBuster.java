package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.List;

public class GraveBuster extends Plant{

    private final Grave grave;

    public GraveBuster(int row, int col, Grave grave){
        super(row, col);
        price = 75;
        HP = 100;
        rechargeTime = 10;
        this.grave = grave;
        Constants.changeScale(gif, 1.3);
        gif.setLayoutY(gif.getLayoutY() - Constants.TILE_SIZE/2);
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies){
        if (Math.abs(GlobalState.gameTime - timeCreated) < 2000)
            gif.setLayoutY(gif.getLayoutY() + Constants.TILE_SIZE/200);
        else if (Math.abs(GlobalState.gameTime - timeCreated) == 2000) {
            HP = 0;
            return true;
        }
        return false;
    }

    public Grave action(){
        return grave;
    }
}