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
import java.util.ArrayList;
import java.util.Random;

public class GameUI {

    private final GameLogic gameLogic;
    private final StackPane mainPane;
    BorderPane bPane = new BorderPane();
    private final AnchorPane pane = new AnchorPane();
    GridPane gPane = new GridPane();
    private Plant selectedPlant;
    private int time = 0;
    private int selectedButton = -1;

    public GameUI(Stage stage){
        gameLogic = new GameLogic();
        ImageView bg = new ImageView(new Image("file:Pictures/backGround/backGroundDay.jpg"));
        bg.setFitHeight(Constants.height);
        bg.setFitWidth(Constants.width);
        bPane.getChildren().add(bg);
        bPane.setBottom(map());
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
        Button peaShooter = new Button();
        peaShooter.setOpacity(30);
        ImageView image = new ImageView(new Image("file:Pictures/plantPictures/dayTime/PeaShooterImage.jpg"));
        image.setFitWidth(Constants.PLANT_CARD_WIDTH);
        image.setFitHeight(Constants.PLANT_CARD_HEIGHT);
        peaShooter.setGraphic(image);
        peaShooter.setStyle("-fx-background-color: transparent");

        peaShooter.setOnMouseEntered(event -> peaShooter.setStyle("-fx-background-color: rgb(62, 177, 235);"));

        peaShooter.setOnMouseClicked(event -> peaShooter.setStyle("-fx-background-color: rgb(62, 177, 235);"));

        peaShooter.setOnMouseExited(e -> {
            if(selectedPlant instanceof PeaShooter) peaShooter.setStyle("-fx-background-color: rgb(62, 177, 235)");
            else peaShooter.setStyle("-fx-background-color: transparent");
        });

        peaShooter.setOnAction(event -> {
            if(selectedPlant instanceof PeaShooter) {
                selectedPlant = null;
                selectedButton = -1;
            }
            else {
                selectedPlant = new PeaShooter();
                selectedButton = 0;
            }
        });
        cardBar.getChildren().add(peaShooter);
        for (int i = 2; i <= 6; i++) {
            Button btn = new Button("" + i);
            btn.setOpacity(0.3);
            btn.setPrefSize(Constants.PLANT_CARD_WIDTH, Constants.PLANT_CARD_HEIGHT);
            cardBar.getChildren().add(btn);
        }
        cardBar.setPadding(new Insets(35, 0, 0, 0));
        cardBar.setAlignment(Pos.CENTER_LEFT);
        return cardBar;
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
                        selectedPlant = null;
                        ((HBox)bPane.getTop()).getChildren().get(selectedButton).setStyle("-fx-background-color: transparent;");
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

        ArrayList<Integer[]> plantsAligned = gameLogic.plantsAligned();
        for(Integer[] a : plantsAligned) {
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
