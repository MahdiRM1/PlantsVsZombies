package main.plantsvszombies.GameState;

import main.plantsvszombies.Enums.GameMode;
import main.plantsvszombies.Game.GameLogic;
import main.plantsvszombies.Game.GlobalState;
import main.plantsvszombies.Items.Card;
import main.plantsvszombies.Items.Grave;
import main.plantsvszombies.Plants.Plant;
import main.plantsvszombies.Zombies.Zombie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameState implements Serializable {

    private final List<PlantData> plants = new ArrayList<>();
    private final List<ZombieData> zombies = new ArrayList<>();
    private final List<CardData> cards = new ArrayList<>();
    private final List<GraveData> graves = new ArrayList<>();
    private final int score;
    private final long time;
    private final GameMode mode;
    private final int fogLength;

    public GameState(GameLogic logic, List<Card> cards, int score, GameMode mode) {
        setPlantsData(logic.getPottedPlants());
        setZombieData(logic.getZombies());
        setCardData(cards);
        setGraveData(logic.getGraves());
        this.score = score;
        this.mode = mode;
        fogLength = mode.getFogLength();
        time = GlobalState.gameTime;
    }

    //saves plant data
    private void setPlantsData(List<Plant> pottedPlants) {
        for (Plant plant : pottedPlants) {
            PlantData plantData = new PlantData(plant);
            plants.add(plantData);
        }
    }

    //saves zombie data
    private void setZombieData(List<Zombie> zombies) {
        for (Zombie z : zombies) {
            ZombieData zombieData = new ZombieData(z);
            this.zombies.add(zombieData);
        }
    }

    //saves card data
    private void setCardData(List<Card> cards) {
        for (Card c : cards) {
            CardData cardData = new CardData(c);
            this.cards.add(cardData);
        }
    }

    private void setGraveData(List<Grave> graves) {
        for (Grave grave : graves) {
            GraveData data = new GraveData(grave);
            this.graves.add(data);
        }
    }

    //getters
    public List<PlantData> getPlants() {
        return plants;
    }

    public List<ZombieData> getZombies() {
        return zombies;
    }

    public List<CardData> getCards() {
        return cards;
    }

    public List<GraveData> getGraves() {
        return graves;
    }

    public int getScore() {
        return score;
    }

    public long getTime() {
        return time;
    }

    public GameMode getMode() {
        return mode;
    }

    public int getFogLength(){
        return fogLength;
    }
}
