package main.plantsvszombies;

import javafx.application.Application;
import javafx.stage.Stage;
import main.plantsvszombies.Game.Introduction;
import javafx.scene.image.Image;

import java.util.Objects;

public class PvsZ extends Application {

    @Override
    public void start(Stage stage) {
        stage.getIcons().add(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/pvz.png")))
        );
        stage.setTitle("Plants vs Zombies");
        new Introduction(stage).firstPage();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
