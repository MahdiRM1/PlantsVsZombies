package main.plantsvszombies;

import javafx.scene.image.Image;

import java.util.List;

public class CoffeeBean extends Plant{

    private final Shroom shroom;

    public CoffeeBean(int row, int col, Shroom shroom){
        super(row, col);
        price = 75;
        HP = 100;
        rechargeTime = 10;
        this.shroom = shroom;
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies){
        if (Math.abs(GlobalState.gameTime - timeCreated) == 1500)
            gif.setImage(new Image("file:Pictures/plantsGifs/CoffeeBeanEat.gif"));
        else if (Math.abs(GlobalState.gameTime - timeCreated) == 2700){
            HP = 0;
//            shroom.wakeUp();
            return true;
        }
        return false;
    }

    public void action(){
        shroom.wakeUp();
    }
}
