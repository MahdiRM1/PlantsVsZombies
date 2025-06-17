package main.plantsvszombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import java.util.Random;

public class GameUI {

    private final GameLogic gameLogic;
    private final StackPane mainPane;
    BorderPane bPane = new BorderPane();
    private final Pane pane = new Pane();
    GridPane gPane = new GridPane();
    private String selectedPlant;
    private int selectedButton = -1;
    private final ScoreBoard scoreBoard;

    public GameUI(Stage stage){
        gameLogic = new GameLogic();
        bPane.getChildren().add(Constants.setDayBackGround());
        bPane.setBottom(map());
        scoreBoard = new ScoreBoard(bPane);
        bPane.setTop(cardBar());
        mainPane = new StackPane(bPane);
        pane.setMouseTransparent(true);
        mainPane.getChildren().add(pane);
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            GlobalState.gameTime += 50;
            updateGame();
        }));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
        Scene scene = new Scene(mainPane, Constants.width, Constants.height);
        stage.setScene(scene);
        stage.show();
    }

    private HBox cardBar(){
        HBox cardBar = new HBox(0);
        Button btn1 = getCardButton("PeaShooter", 0);
        Button btn2 = getCardButton("SunFlower", 1);
        Button btn3 = getCardButton("WallNut", 2);
        Button btn4 = getCardButton("TallNut", 3);
        Button btn5 = getCardButton("Repeater", 4);
        Button btn6 = getCardButton("SnowPea", 5);
        Button btn7 = getCardButton("CherryBomb", 5);
        Button btn8 = getCardButton("Jalapeno", 5);
        cardBar.getChildren().addAll(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8);
        cardBar.setPadding(new Insets(Constants.height/19, 0, 0, Constants.height/7.4));
        cardBar.setAlignment(Pos.CENTER_LEFT);
        return cardBar;
    }

    private Button getCardButton(String plantName, int index){
        Button btn = new Button();
        btn.setGraphic(Constants.setCard(plantName));
        btn.setStyle("-fx-background-color: transparent");
        btn.setOnAction(event -> {
            selectedPlant = plantName;
            selectedButton = index;
        });
        btn.setOnMouseEntered(event -> btn.setStyle("-fx-background-color: rgb(62, 177, 235);"));
        btn.setOnMouseClicked(event -> btn.setStyle("-fx-background-color: rgb(62, 177, 235);"));
        btn.setOnMouseExited(e -> {
            if(plantName.equals(selectedPlant)) btn.setStyle("-fx-background-color: rgb(62, 177, 235)");
            else btn.setStyle("-fx-background-color: transparent");
        });
        return btn;
    }//in cartaye bazio meghdardehi mikone

    private GridPane map(){
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 9; col++) {
                gPane.add(mapButtons(row, col), col, row);
            }
        }
        gPane.setPadding(new Insets(0,0,Constants.height/12.8,Constants.height/2.62));
        return gPane;
    }

    private Button mapButtons(int row, int col){
        Button btn = new Button();
        btn.setPrefSize(Constants.TILE_SIZE, Constants.TILE_SIZE);
        btn.setStyle("-fx-background-color: transparent");
        btn.setOnAction(event -> {
            if(selectedPlant != null) {
                Plant plant = getPlant(row, col);
                if(scoreBoard.getScore() >= plant.getPrice() && gameLogic.setPlant(row, col, plant)) {
                    bPane.getChildren().add(plant.getGif());
                    scoreBoard.purchasePlant(plant.getPrice());
                    btn.setOnMouseClicked(event1 -> btn.setStyle("-fx-background-color: rgba(161, 245, 163, 0.6);"));
                }
                else btn.setOnMouseClicked(event2 -> btn.setStyle("-fx-background-color: rgba(245, 50, 50, 0.6);"));
                ((HBox)bPane.getTop()).getChildren().get(selectedButton).setStyle("-fx-background-color: transparent;");
                selectedPlant = null;
            }else btn.setOnMouseClicked(event2 -> btn.setStyle("-fx-background-color: rgba(245, 50, 50, 0.6);"));
        });
        btn.setOnMouseEntered(event -> btn.setStyle("-fx-background-color: rgba(140, 140, 140, 0.3);"));
        btn.setOnMouseExited(event -> btn.setStyle("-fx-background-color: transparent;"));
        btn.setOnMouseClicked(event -> btn.setStyle("-fx-background-color: rgba(161, 245, 163, 0.3);"));
        return btn;
    }//in faghat buttonaye mapo meghdardehi mikone

    private Plant getPlant(int row, int col){
        switch (selectedPlant){
            case "PeaShooter" -> {
                return new PeaShooter(row, col);
            }
            case "SunFlower" -> {
                return new SunFlower(row, col);
            }
            case "WallNut" -> {
                return new WallNut(row, col);
            }
            case "TallNut" -> {
                return new TallNut(row, col);
            }
            case "Repeater" -> {
                return new Repeater(row, col);
            }
            case "SnowPea" -> {
                return new SnowPea(row, col);
            }
            case "CherryBomb" -> {
                return new CherryBomb(row, col);
            }
            case "Jalapeno" -> {
                return new Jalapeno(row, col);
            }
            default -> {
                return null;
            }
        }
    }//in vase new kardan moghe kashtane ke string migire plant mide

    public void updateGame(){
        garbageImages();
        characterActions();
        addObjectImages();
        timeHandler();
    }//in hamon method movement bode ke chand ta tikash kardam fek mekonam behtar shode bashe

    private void timeHandler(){
        Random rdm = new Random();
        if(GlobalState.gameTime <= 15000);
        else if(GlobalState.gameTime <= 50000){
            System.out.println(1);
            if(GlobalState.gameTime % 5000 == 1000) zombieGetter(0, rdm.nextInt(5));
        }
        else if(GlobalState.gameTime <= 70000){
            System.out.println(2);
            if(GlobalState.gameTime % 3000 == 0) zombieGetter(rdm.nextInt(2), rdm.nextInt(5));
        }
        else if(GlobalState.gameTime < 80000){
            System.out.println(3);
            if(GlobalState.gameTime % 3000 == 0 || GlobalState.gameTime % 3000 == 200){
                for (int i = 0; i < 5; i++) {
                    zombieGetter(rdm.nextInt(2), i);
                }
            }
        }
        else if(GlobalState.gameTime <= 120000){
            System.out.println(4);
            if(GlobalState.gameTime % 3000 == 0) {
                zombieGetter(rdm.nextInt(3), rdm.nextInt(5));
                zombieGetter(rdm.nextInt(3), rdm.nextInt(5));
            }
        }
        else if (GlobalState.gameTime < 140000){
            System.out.println(5);
                if(GlobalState.gameTime % 3000 == 0 || GlobalState.gameTime % 3000 == 200){
                for (int i = 0; i < 5; i++) {
                    zombieGetter(rdm.nextInt(4), i);
                }
            }
        }
        else {
            if(gameLogic.getZombies().isEmpty()){
                System.out.println(6);
                Label win = new Label("You win");
                win.setTextFill(Color.RED);
                win.setFont(Font.font("Arial", FontWeight.BOLD, 100));
                win.setEffect(new DropShadow(10, Color.BLACK));
                pane.getChildren().add(win);
            }
        }
    }

    private void addZombie(Zombie z){
        gameLogic.addZombie(z);
        pane.getChildren().add(z.getPicture());
    }

    private void zombieGetter(int z, int row){
        switch (z){
            case 0 -> addZombie(new OriginalZombie(row));
            case 1 -> addZombie(new ConeheadZombie(row));
            case 2 -> addZombie(new BucketheadZombie(row));
            case 3 -> addZombie(new Imp(row));
        }
    }

    private void characterActions(){
        for(Zombie z : gameLogic.getZombies()) z.action();
        for(Bullet b : gameLogic.getBullets()) b.move();
        gameLogic.setZombieState();
        scoreBoard.sunHandler();
    }

    private void addObjectImages(){
        for(SunFlower sunFlower : gameLogic.sunFlowers()) {
            scoreBoard.addSun(sunFlower.givenSun());
        }

        for(PeaPlant shooter : gameLogic.plantsAligned()) {
            Bullet b = shooter.shoot(shooter.getRow(), shooter.getCol());
            if(b != null) {
                gameLogic.addBullet(b);
                pane.getChildren().addAll(b.getPicture());
            }
        }
    }

    private void garbageImages(){
        for(Bullet bullet : gameLogic.checkBulletStrike()) pane.getChildren().remove(bullet.getPicture());
        for (Zombie zombie : gameLogic.zombieToRemove()) pane.getChildren().remove(zombie.getPicture());
        for(Plant plantToRemove : gameLogic.plantsToRemove()) bPane.getChildren().remove(plantToRemove.getGif());
    }
}
