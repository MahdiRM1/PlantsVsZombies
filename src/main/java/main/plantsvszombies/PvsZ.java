package main.plantsvszombies;

import javafx.application.Application;
import javafx.stage.Stage;

public class PvsZ extends Application {
    public void start(Stage stage) {
        new Introdoction().show(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}