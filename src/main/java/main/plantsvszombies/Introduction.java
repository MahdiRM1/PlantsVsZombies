package main.plantsvszombies;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Random;

public class Introduction {

    private Stage stage;
    private final ArrayList<String> selectedCards = new ArrayList<>();
    private HBox cardBar;

    public void firstPage(Stage stage){
        this.stage = stage;
        Scene scene = new Scene(Pane(), Constants.width, Constants.height - 35);
        stage.setScene(scene);
        stage.show();
    }

    public void chooseCardPage(){
        Pane pane = new Pane();
        pane.getChildren().addFirst(Constants.setBackGround("plantSelection"));

        cardBar = new HBox(0);
        cardBar.setLayoutX(Constants.height/5.2);
        cardBar.setLayoutY(Constants.height/50);

        HBox box1 = new HBox(50,
                getCardButton("PeaShooter"), getCardButton("SunFlower") ,
                getCardButton("WallNut") ,getCardButton("TallNut")
        );
        HBox box2 = new HBox(50,
                getCardButton("Repeater"), getCardButton("SnowPea"),
                getCardButton("CherryBomb") ,getCardButton("Jalapeno")
        );

        VBox vBox = new VBox(20);
        vBox.getChildren().addAll(box1, box2);
        vBox.setLayoutX(Constants.height/10);
        vBox.setLayoutY(Constants.height/5);
        pane.getChildren().addAll(Constants.setScoreBoardPicture(), cardBar, vBox, startGameBtn());
        for (int i = 0; i < 8; i++) pane.getChildren().add(addZombie());
        Scene scene = new Scene(pane, Constants.width, Constants.height - 35);
        stage.setScene(scene);
        stage.show();
    }

    private Button startGameBtn(){
        Button btn = new Button("Let's Rock");
        btn.setPrefSize(Constants.height/3.2, Constants.height/16);
        btn.setStyle(
                "-fx-background-radius: 10; " +
                "-fx-background-color: rgb(100, 50, 0);" +
                "-fx-text-fill: green; " +
                "-fx-font-size: 35px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0.5, 0, 1);"
        );
        btn.setOnMouseEntered(event -> {
            if(selectedCards.size() != 6) btn.setStyle(btn.getStyle() + "-fx-background-color: rgb(100, 0, 0);");
            else {
                double differentX = btn.getPrefWidth() * 1.1 - btn.getPrefWidth();
                double differentY = btn.getPrefHeight() * 1.1 - btn.getPrefHeight();
                btn.setPrefSize(btn.getPrefWidth() * 1.1, btn.getPrefHeight() * 1.1);
                btn.setLayoutX(btn.getLayoutX() - differentX/2);
                btn.setLayoutY(btn.getLayoutY() - differentY/2);
            }
        });
        btn.setOnMouseExited(event -> {
            if(selectedCards.size() == 6) {
                double differentX = btn.getPrefWidth() / 1.1 - btn.getPrefWidth();
                double differentY = btn.getPrefHeight() / 1.1 - btn.getPrefHeight();
                btn.setPrefSize(btn.getPrefWidth() / 1.1, btn.getPrefHeight() / 1.1);
                btn.setLayoutX(btn.getLayoutX() - differentX/2);
                btn.setLayoutY(btn.getLayoutY() - differentY/2);
            }
            else btn.setStyle(btn.getStyle() + "-fx-background-color: rgb(100, 50, 0);");
        });
        btn.setLayoutX(Constants.width/5.9);
        btn.setLayoutY(Constants.height/1.15);
        btn.setOnAction(event -> {
            if (selectedCards.size() == 6) new GameUI(stage, selectedCards);
        });
        return btn;
    }

    private ImageView addZombie(){
        Random rdm = new Random();
        ImageView image = new ImageView();
        switch (rdm.nextInt(4)){
            case 0 -> image.setImage(new Image("file:Pictures/ZombieGif/OriginalZombie.gif"));
            case 1 -> image.setImage(new Image("file:Pictures/ZombieGif/ConeheadZombie.gif"));
            case 2 -> image.setImage(new Image("file:Pictures/ZombieGif/BucketheadZombie.gif"));
            case 3 -> image.setImage(new Image("file:Pictures/ZombieGif/Imp.gif"));
        }
        image.setFitHeight(Constants.ZOMBIE_PIC_HEIGHT);
        image.setFitWidth(Constants.ZOMBIE_PIC_WEIGHT);
        image.setLayoutX(Constants.width/1.8 + rdm.nextDouble(Constants.width/3));
        image.setLayoutY(rdm.nextDouble(Constants.height/1.5));
        return image;
    }


    private Button getCardButton(String plantName){
        Button btn = new Button();
        btn.setGraphic(Constants.setCard(plantName));
        btn.setPrefSize(btn.getPrefWidth() * 1.2, btn.getPrefHeight() * 1.2);
        ImageView image = (ImageView)btn.getGraphic();
        image.setFitHeight(image.getFitHeight() * 1.2);
        image.setFitWidth(image.getFitWidth() * 1.2);
        btn.setStyle("-fx-background-color: transparent");
        btn.setOnAction(event -> {
            if(selectedCards.contains(plantName)) {
                selectedCards.remove(plantName);
                cardBar.getChildren().remove(btn);
                btn.setEffect(null);
            }
            else if(selectedCards.size() < 6) {
                selectedCards.add(plantName);
                cardBar.getChildren().add(btn);
                btn.setEffect(chooseCardEffect());
            }
            else btn.setStyle("-fx-background-color: rgb(150, 0, 0);");
        });
        btn.setOnMouseEntered(event -> btn.setStyle("-fx-background-color: rgb(62, 177, 235);"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent;"));
        return btn;
    }

    private Effect chooseCardEffect(){
        ColorAdjust choose = new ColorAdjust();
        choose.setBrightness(-0.5);
        choose.setContrast(-0.3);
        return choose;
    }

    private BorderPane Pane(){
        BorderPane borderPane = new BorderPane();
        borderPane.getChildren().addFirst(Constants.setBackGround("GameStartBackGround"));
        VBox box = new VBox();
        box.getChildren().add(initializeBtn("Start Game"));
        box.setAlignment(Pos.CENTER);
        borderPane.setCenter(box);
        return borderPane;
    }

    private Button initializeBtn(String str){
        Button btn = new Button(str);
        btn.setStyle(
            "-fx-background-radius: 20; " +
            "-fx-min-width: 150px; " +
            "-fx-min-height: 75px; " +
            "-fx-background-color: rgb(206, 175, 0); "  +
            "-fx-text-fill: white; " +
            "-fx-font-size: 50px; " +
            "-fx-font-weight: bold; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0.5, 0, 1);"
        );
        btn.setOnMouseEntered(event ->
                btn.setStyle(btn.getStyle() + "-fx-background-color: rgb(134, 114, 1); ")
        );
        btn.setOnMouseExited(event ->
                btn.setStyle(btn.getStyle() + "-fx-background-color: rgb(206, 175, 0); ")
        );
        btn.setOnMousePressed(event ->
                btn.setStyle(btn.getStyle() + "-fx-background-color: rgb(0, 0, 0); ")
        );
        btn.setOnMouseReleased(event ->
                btn.setStyle(btn.getStyle() + "-fx-background-color: rgb(62, 177, 235); ")
        );
        btn.setOnAction(event -> chooseCardPage());
        return btn;
    }
}
