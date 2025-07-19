package main.plantsvszombies;

import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Card {

    private final String plantName;
    private final int rechargeTime;
    private final int index;
    private final Button btn;
    private long lastSelected;
    private boolean canChoose;

    public Card(CardData data) {
        plantName = data.getPlantName();
        lastSelected = data.getLastSelected();
        rechargeTime = data.getRechargeTime();
        index = data.getIndex();
        btn = cardButton(index);
        rechargeCheck();
    }

    public Card(String plantName, int index) {
        this.plantName = plantName;
        rechargeTime = findRechargeTime();
        lastSelected = -rechargeTime;
        this.index = index;
        canChoose = true;
        btn = cardButton(index);
    }

    //recharge time of each plant
    private int findRechargeTime() {
        Plant plant = switch (plantName){
            case "CoffeeBean" -> new CoffeeBean(0, 0, null);
            case "GraveBuster" -> new GraveBuster(0, 0, null);
            default -> Constants.getPlant(0, 0, plantName, false);
        };
        return plant.getRechargeTime() * 1000;
    }

    //generate buttons for plant cards
    private Button cardButton(int index) {
        Button btn = new Button();
        btn.setGraphic(Constants.setCard(plantName));
        btn.setStyle("-fx-background-color: transparent");
        btn.setOnAction(event -> {
            if (!canChoose) return;

            if (GameUI.selectedButton >= 0 && GameUI.selectedButton < 6) {
                HBox cardBar = (HBox) btn.getParent();
                Button lastBtnSelected = ((Button) cardBar.getChildren().get(GameUI.selectedButton));
                lastBtnSelected.setStyle("-fx-background-color: transparent");
            }

            if (GameUI.selectedButton != index) GameUI.selectedButton = index;
            else {
                GameUI.selectedButton = -1;
                btn.setStyle("-fx-background-color: transparent");
            }
        });
        btn.setOnMouseEntered(event -> {
            if (canChoose) btn.setStyle("-fx-background-color: rgb(62, 177, 235);");
        });
        btn.setOnMouseClicked(event -> {
            if (canChoose) btn.setStyle("-fx-background-color: rgb(174, 255, 174);");
        });
        btn.setOnMouseExited(e -> {
            if (GameUI.selectedButton == index) btn.setStyle("-fx-background-color: rgb(174, 255, 174)");
            else btn.setStyle("-fx-background-color: transparent");
        });
        return btn;
    }

    //check recharge logic
    public void rechargeCheck() {
        double rechargeCheck = (double) (GlobalState.gameTime - lastSelected) / rechargeTime;
        ImageView imageView = (ImageView) btn.getGraphic();
        if (rechargeCheck >= 1) {
            imageView.setEffect(null);
            canChoose = true;
        } else {
            imageView.setEffect(rechargeCardEffect(rechargeCheck));
            canChoose = false;
        }
    }

    //recharge visual effects
    private Effect rechargeCardEffect(double recharge) {
        ColorAdjust choose = new ColorAdjust();
        choose.setBrightness(-1.0 + recharge);
        return choose;
    }

    public void updateLastSelected() {
        lastSelected = GlobalState.gameTime;
    }

    //getters
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

    public Button getBtn() {
        return btn;
    }
}
