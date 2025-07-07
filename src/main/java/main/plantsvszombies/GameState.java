package main.plantsvszombies;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameState implements Serializable {
    private final List<PlantData> plants = new ArrayList<>();
    private final List<ZombieData> zombies = new ArrayList<>();
    private final List<CardData> cards = new ArrayList<>();
    private final int score;
    private final long time;
    private final GameMode mode;

    public GameState(GameLogic logic, List<Card> cards, int score, GameMode mode){
        getPlants(logic.getPottedPlants());
        getZombies(logic.getZombies());
        getCards(cards);
        this.score = score;
        this.mode = mode;
        time = GlobalState.gameTime;
    }

    //saves plant data
    private void getPlants(List<Plant> pottedPlants){
        for (Plant plant : pottedPlants) {
            PlantData plantData = new PlantData(plant);
            plants.add(plantData);
        }
    }

    //saves zombie data
    private void getZombies(List<Zombie> zombies){
        for (Zombie z : zombies){
            ZombieData zombieData = new ZombieData(z);
            this.zombies.add(zombieData);
        }
    }

    //saves card data
    private void getCards(List<Card> cards){
        for (Card c : cards){
            CardData cardData = new CardData(c);
            this.cards.add(cardData);
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

    public int getScore() {
        return score;
    }

    public long getTime() {
        return time;
    }

    public GameMode getMode() {
        return mode;
    }
}
