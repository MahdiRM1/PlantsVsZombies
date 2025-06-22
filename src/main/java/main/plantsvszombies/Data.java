package main.plantsvszombies;

import java.io.Serializable;

class PlantData implements Serializable {
    private String type;
    private int row, col;
    private double HP;

    public PlantData(Plant plant){
        type = plant.getClass().getSimpleName();
        row = plant.getRow();
        col = plant.getCol();
        HP = plant.getHP();
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
}

class ZombieData implements Serializable {
    private String type;
    private int row;
    private double picLayoutX;
    private int HP;

    public ZombieData(Zombie zombie){
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
    private String plantName;
    private int rechargeTime;
    private long lastSelected;
    private int index;

    public CardData(Card card){
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
