package main.plantsvszombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;

public class GameUI {

    private final GameLogic gameLogic;
    private final StackPane mainPane;
    BorderPane bPane = new BorderPane();
    private final AnchorPane pane = new AnchorPane();
    GridPane gPane = new GridPane();
    private String selectedPlant;
    private long time = 0;
    private int selectedButton = -1;
    private final ScoreBoard scoreBoard;

    public GameUI(Stage stage){
        gameLogic = new GameLogic();
        bPane.getChildren().add(Constants.setDayBackGround());
        bPane.setBottom(map());
        scoreBoard = new ScoreBoard(bPane);
        bPane.setTop(cardBar());
        mainPane = new StackPane(bPane);
        Random rdm = new Random();
        Zombie z = gameLogic.addZombie(new OriginalZombie(rdm.nextInt(5)));
        pane.getChildren().add(z.getPicture());
        z = gameLogic.addZombie(new OriginalZombie(rdm.nextInt(5)));
        pane.getChildren().add(z.getPicture());
        pane.setMouseTransparent(true);
        mainPane.getChildren().add(pane);
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            time += 50;
            updateGame();
        }));
        tl.setCycleCount(2000);
        tl.play();
        Scene scene = new Scene(mainPane, Constants.width, Constants.height);
        stage.setScene(scene);
        stage.show();
    }

    private HBox cardBar(){
        HBox cardBar = new HBox(5);
        Button btn1 = getCardButton("PeaShooter", 0);
        Button btn2 = getCardButton("SunFlower", 1);
        cardBar.getChildren().addAll(btn1, btn2);
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
                if(btn.getGraphic() == null && scoreBoard.getScore() >= plant.getPrice()) {
                    gameLogic.setPlant(row, col, plant);
                    btn.setGraphic(plant.getGif());
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
                return new PeaShooter(row, col, time);
            }
            case "SunFlower" -> {
                return new SunFlower(row, col, time);
            }
            default -> {
                return null;
            }
        }
    }//in vase new kardan moghe kashtane ke string migire plant mide

    public void updateGame(){
        characterActions();
        garbageImages();
        addObjectImages();
    }//in hamon method movement bode ke chand ta tikash kardam fek mekonam behtar shode bashe

    private void characterActions(){
        for(Zombie z : gameLogic.getZombies()) z.action();
        for(Bullet b : gameLogic.getBullets()) b.move();
        gameLogic.setZombieState();
        scoreBoard.sunHandler(time);
    }

    private void addObjectImages(){
        for(SunFlower sunFlower : gameLogic.sunFlowers()) {
            scoreBoard.addSun(sunFlower.givenSun(time));
        }

        for(PeaPlant shooter : gameLogic.plantsAligned()) {
            Bullet b = shooter.shoot(shooter.getRow(), shooter.getCol(), time);
            if(b != null) {
                gameLogic.addBullet(b);
                pane.getChildren().addAll(b.getPicture());
            }
        }
    }

    private void garbageImages(){
        for(Bullet bullet : gameLogic.checkBulletStrike()) pane.getChildren().remove(bullet.getPicture());
        for (Zombie zombie : gameLogic.zombieToRemove()) pane.getChildren().remove(zombie.getPicture());
        for(Plant plantToRemove : gameLogic.plantsToRemove())
            ((Button)gPane.getChildren().get(plantToRemove.getRow() * 9 + plantToRemove.getCol())).setGraphic(null);
    }
}
