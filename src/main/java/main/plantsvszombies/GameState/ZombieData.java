package main.plantsvszombies.GameState;

import main.plantsvszombies.Zombies.Zombie;

import java.io.Serializable;

public class ZombieData implements Serializable {

    private final String type;
    private final int row;
    private final double picLayoutX;
    private final double HP;
    private final boolean hypnotized;

    public ZombieData(Zombie zombie) {
        type = zombie.getClass().getSimpleName();
        row = zombie.getRow();
        picLayoutX = zombie.getPicture().getLayoutX();
        HP = zombie.getHP();
        hypnotized = zombie.isHypnotized();
    }

    public String getType() {
        return type;
    }

    public int getRow() {
        return row;
    }

    public double getPicLayoutX() {
        return picLayoutX;
    }

    public double getHP() {
        return HP;
    }

    public boolean isHypnotized() {
        return hypnotized;
    }
}



