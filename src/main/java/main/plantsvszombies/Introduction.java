package main.plantsvszombies;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Introduction {

    private Stage stage;

    public void show(Stage stage){
        this.stage = stage;
        Scene scene = new Scene(Pane(), Constants.width, Constants.height);
        stage.setScene(scene);
        stage.show();
    }

    private BorderPane Pane(){
        BorderPane borderPane = new BorderPane();
        borderPane.getChildren().addFirst(backGround());
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
                btn.setStyle(btn.getStyle() + "-fx-background-color: rgb(134, 114, 1); ")
        );
        btn.setOnAction(event -> new GameUI(stage));
        return btn;
    }

    private ImageView backGround(){
        ImageView bg = new ImageView(new Image("file:Pictures/background.jpg"));
        bg.setFitHeight(Constants.height);
        bg.setFitWidth(Constants.width);
        return bg;
    }
}
