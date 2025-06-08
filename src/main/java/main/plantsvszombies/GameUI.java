package main.plantsvszombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameUI {

    private final GameLogic gameLogic;
    private final StackPane mainPane;
    private Plant selectedPlant;
    AnchorPane pane = new AnchorPane();
    Zombie z;

    public GameUI(Stage stage){
        gameLogic = new GameLogic();
        BorderPane bPane = new BorderPane();
        bPane.setBottom(map());
        bPane.setTop(cardBar());
        mainPane = new StackPane(bPane);
        z = new OriginalZombie(2);
        pane.getChildren().add(z.getPicture());
        pane.setMouseTransparent(true);
        mainPane.getChildren().add(pane);
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(50), event -> {
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
        Button peashoter = new Button();
        ImageView image = new ImageView(new Image("file:Pictures/peashooterCard.png"));
        image.setFitWidth(Constants.TILE_HEIGHT);
        image.setFitHeight(Constants.TILE_WIDTH);
        peashoter.setGraphic(image);
        peashoter.setOnAction(event -> {
            if(selectedPlant == null) selectedPlant = new PeaShooter();
            else selectedPlant = null;
        });
        cardBar.getChildren().add(peashoter);
        for (int i = 2; i <= 10; i++) {
            Button btn = new Button("" + i);
            btn.setPrefSize(Constants.TILE_HEIGHT, Constants.TILE_WIDTH);
            cardBar.getChildren().add(btn);
        }
        cardBar.setPadding(new Insets(5));
        ScrollPane scrollPane = new ScrollPane(cardBar);
        scrollPane.setPrefWidth(Constants.TILE_HEIGHT * 7);
        scrollPane.setPrefHeight(Constants.TILE_WIDTH + 30);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        HBox topBar = new HBox(scrollPane);
        topBar.setAlignment(Pos.CENTER_LEFT);
        return topBar;
    }

    private GridPane map(){
        GridPane gPane = new GridPane();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                Button btn = new Button();
                btn.setPrefSize(Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
                int finalI = i;
                int finalJ = j;
                btn.setOnAction(event -> {
                    if(selectedPlant != null) {
                        gameLogic.setPlant(finalI, finalJ, selectedPlant);
                        if(btn.getGraphic() == null) btn.setGraphic(selectedPlant.getGif());
                        selectedPlant = null;
                    }
                });
                gPane.add(btn, j, i);
            }
        }
        return gPane;
    }

    public void movement(){
        z.move();
        pane.getChildren().removeLast();
        pane.getChildren().add(z.getPicture());
        mainPane.getChildren().removeLast();
        mainPane.getChildren().add(pane);
    }
}
