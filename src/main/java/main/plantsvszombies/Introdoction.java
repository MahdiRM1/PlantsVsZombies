package main.plantsvszombies;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Introdoction {

    private static final double width = Screen.getPrimary().getBounds().getWidth();
    private static final double height = Screen.getPrimary().getBounds().getHeight();

    public void show(Stage stage){
        StackPane mainPane = new StackPane();
        mainPane.getChildren().addFirst(backGround());
        HBox box = new HBox();
        mainPane.getChildren().add(initializeBtn("Start Game"));
        Scene scene = new Scene(mainPane, width, height);
        stage.setScene(scene);
        stage.show();
    }

    private Button initializeBtn(String str){
        Button btn = new Button(str);
        btn.setStyle(
                "-fx-background-radius: 20; " +
                        "-fx-min-width: 150px; " +
                        "-fx-min-height: 75px; " +
                        "-fx-background-color: rgb(206, 175, 0)); "  +
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
                btn.setStyle(btn.getStyle() + "-fx-background-color: rgb(206, 175, 0)); ")
        );
        return btn;
    }

    private ImageView backGround(){
        ImageView bg = new ImageView(new Image("file:Pictures/background.jpg"));
        bg.setFitHeight(height);
        bg.setFitWidth(width);
        return bg;
    }
}
