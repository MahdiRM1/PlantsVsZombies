package main.plantsvszombies;

import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable {
    private ArrayList<PlantData> plants = new ArrayList<>();
    private ArrayList<ZombieData> zombies = new ArrayList<>();
    private ArrayList<CardData> cards = new ArrayList<>();
    private int score;
    private long time;

    public GameState(GameLogic logic, ArrayList<Card> cards, int score){
        getPlants(logic.getPottedPlants());
        getZombies(logic.getZombies());
        getCards(cards);
        this.score = score;
        time = GlobalState.gameTime;
    }

    //saves plant data
    private void getPlants(Plant[][] pottedPlants){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (pottedPlants[i][j] != null) {
                    PlantData plantData = new PlantData(pottedPlants[i][j]);
                    plants.add(plantData);
                }
            }
        }
    }

    //saves zombie data
    private void getZombies(ArrayList<Zombie> zombies){
        for (Zombie z : zombies){
            ZombieData zombieData = new ZombieData(z);
            this.zombies.add(zombieData);
        }
    }

    //saves card data
    private void getCards(ArrayList<Card> cards){
        for (Card c : cards){
            CardData cardData = new CardData(c);
            this.cards.add(cardData);
        }
    }

    //getters
    public ArrayList<PlantData> getPlants() {
        return plants;
    }

    public ArrayList<ZombieData> getZombies() {
        return zombies;
    }

    public ArrayList<CardData> getCards() {
        return cards;
    }

    public int getScore() {
        return score;
    }

    public long getTime() {
        return time;
    }
}
