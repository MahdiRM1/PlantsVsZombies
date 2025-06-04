package main.plantsvszombies;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameUI {

    public final StackPane mainPane;

    public GameUI(Stage stage){
        BorderPane bPane = new BorderPane();
        bPane.setBottom(map());
        bPane.setTop(cardBar());
        mainPane = new StackPane(bPane);
        Scene scene = new Scene(mainPane, Constants.width, Constants.height);
        stage.setScene(scene);
        stage.show();
    }

    private HBox cardBar(){
        HBox cardBar = new HBox(5);
        for (int i = 1; i <= 10; i++) {
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
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                Button btn = new Button(i + " " + j);
                btn.setPrefSize(Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
                gPane.add(btn, i, j);
            }
        }
        return gPane;
    }
}
