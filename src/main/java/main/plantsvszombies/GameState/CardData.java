package main.plantsvszombies.GameState;

import main.plantsvszombies.Items.Card;

import java.io.Serializable;

public class CardData implements Serializable {

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

