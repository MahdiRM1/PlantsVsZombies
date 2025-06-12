package main.plantsvszombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private Plant selectedPlant;
    private long time = 0;
    private int selectedButton = -1;
    private ScoreBoard scoreBoard;

    public GameUI(Stage stage){
        gameLogic = new GameLogic();
        ImageView bg = new ImageView(new Image("file:Pictures/backGround/backGroundDay.jpg"));
        bg.setFitHeight(Constants.height);
        bg.setFitWidth(Constants.width);
        bPane.getChildren().add(bg);
        bPane.setBottom(map());
        bPane.setTop(cardBar());
        scoreBoard = new ScoreBoard(bPane);
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
            movement();
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
        for (int i = 3; i <= 6; i++) {
            Button btn = new Button("" + i);
            btn.setOpacity(0.3);
            btn.setPrefSize(Constants.PLANT_CARD_WIDTH, Constants.PLANT_CARD_HEIGHT);
            cardBar.getChildren().add(btn);
        }
        cardBar.setPadding(new Insets(35, 0, 0, 0));
        cardBar.setAlignment(Pos.CENTER_LEFT);
        return cardBar;
    }

    private Button getCardButton(String plantName, int index){
        Button btn = new Button();
        ImageView image = new ImageView(new Image("file:Pictures/plantPictures/dayTime/" + plantName + "Image.jpg"));
        image.setFitWidth(Constants.PLANT_CARD_WIDTH);
        image.setFitHeight(Constants.PLANT_CARD_HEIGHT);
        btn.setGraphic(image);
        btn.setStyle("-fx-background-color: transparent");

        btn.setOnMouseEntered(event -> btn.setStyle("-fx-background-color: rgb(62, 177, 235);"));

        btn.setOnMouseClicked(event -> btn.setStyle("-fx-background-color: rgb(62, 177, 235);"));

        btn.setOnMouseExited(e -> {
            if(selectedButton == index) btn.setStyle("-fx-background-color: rgb(62, 177, 235)");
            else btn.setStyle("-fx-background-color: transparent");
        });

        setActionButton(btn, plantName, index);

        return btn;
    }

    private void setActionButton(Button btn, String plantName, int index){
        Plant temp = null;
        switch (plantName){
            case "PeaShooter" -> temp = new PeaShooter();
            case "SunFlower" -> temp = new SunFlower(time);
        }
        final Plant plant = temp;
        btn.setOnAction(event -> {
            if(selectedPlant != null && selectedPlant.getClass() == plant.getClass()) {
                selectedPlant = null;
                selectedButton = -1;
            }
            else {
                switch (plantName) {
                    case "PeaShooter" -> selectedPlant = new PeaShooter();
                    case "SunFlower" -> selectedPlant = new SunFlower(time);
                }
                selectedButton = index;
            }
        });
    }

    private GridPane map(){
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 9; col++) {
                Button btn = new Button();
                int Row = row; int Col = col;
                btn.setPrefSize(Constants.TILE_SIZE, Constants.TILE_SIZE);
                btn.setStyle("-fx-background-color: transparent");
                btn.setOnAction(event -> {
                    if(selectedPlant != null) {
                        gameLogic.setPlant(Row, Col, selectedPlant);
                        if(btn.getGraphic() == null) btn.setGraphic(selectedPlant.getGif());
                        ((HBox)bPane.getTop()).getChildren().get(selectedButton).setStyle("-fx-background-color: transparent;");
                        selectedPlant = null;
                    }
                });
//                btn.setOpacity(0.3);
                gPane.add(btn, col, row);
            }
        }
        gPane.setPadding(new Insets(0,0,Constants.width/24,Constants.height/2.62));
        return gPane;
    }

    public void movement(){
        for(Zombie z : gameLogic.getZombies()) z.action();
        for(Bullet b : gameLogic.getBullets()) b.move();
        for(SunFlower sunFlower : gameLogic.sunFlowers()) {
            scoreBoard.addSun(sunFlower.givenSun(time));
        }
        scoreBoard.sunHandler(time);

        for(Integer[] a : gameLogic.plantsAligned()) {
            Bullet b = ((PeaPlant)gameLogic.getPlant(a[0], a[1])).shoot(a[0], a[1], time);
            if(b != null) {
                gameLogic.addBullet(b);
                pane.getChildren().addAll(b.getPicture());
            }
        }
        gameLogic.setState();
        for(ImageView bulletImage : gameLogic.checkBulletStrike()) pane.getChildren().remove(bulletImage);
        for (Zombie zombie : gameLogic.zombieToRemove()) pane.getChildren().remove(zombie.getPicture());
        for(Integer[] plantToRemove : gameLogic.plantsToRemove())
            ((Button)gPane.getChildren().get(plantToRemove[0] * 9 + plantToRemove[1])).setGraphic(null);
    }
}
