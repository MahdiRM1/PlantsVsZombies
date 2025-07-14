package main.plantsvszombies;

import java.io.Serializable;

class PlantData implements Serializable {

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

class ZombieData implements Serializable {

    private final String type;
    private final int row;
    private final double picLayoutX;
    private final int HP;

    public ZombieData(Zombie zombie) {
        type = zombie.getClass().getSimpleName();
        row = zombie.getRow();
        picLayoutX = zombie.getPicture().getLayoutX();
        HP = zombie.getHP();
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

    public int getHP() {
        return HP;
    }
}

class CardData implements Serializable {

    private final String plantName;
    private final int rechargeTime;
    private final long lastSelected;
    private final int index;

    public CardData(Card card) {
        plantName = card.getPlantName();
        rechargeTime = card.getRechargeTime();
        lastSelected = card.getLastSelected();
        index = card.getIndex();
    }

    public String getPlantName() {
        return plantName;
    }

    public int getRechargeTime() {
        return rechargeTime;
    }

    public long getLastSelected() {
        return lastSelected;
    }

    public int getIndex() {
        return index;
    }
}

class GraveData implements Serializable {

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
