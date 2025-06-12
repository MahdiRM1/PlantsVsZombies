package main.plantsvszombies;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;

public class ScoreBoard {

    private int score;
    BorderPane pane;
    private final ArrayList<Sun> suns = new ArrayList<>();

    public ScoreBoard(BorderPane pane){
        this.pane = pane;
        score = 50;
    }

    public void addSun(Sun s){
        if(s != null) {
            ImageView image = s.getPicture();
            image.setOnMouseClicked(event -> {
                score += 25;
                Platform.runLater(() -> {
                    pane.getChildren().remove(image);
                });
                System.out.println(score);
                suns.remove(s);
            });
            Platform.runLater(() -> {
                pane.getChildren().add(image);
            });
            suns.add(s);
        }
    }

    public void sunHandler(long time){
        sunDrop(time);
        garbageSuns(time);
        fallenSun(time);
    }

    private void garbageSuns(long time){
        for (int i = 0; i < suns.size(); i++) {
            if(Math.abs(suns.get(i).getTimeCreated() - time) >= 10000) {
                pane.getChildren().remove(suns.get(i).getPicture());
                suns.remove(i);
            }
        }
    }

    private void fallenSun(long time){
        for (Sun s : suns){
            if(s.getType() == SunType.FALLEN) s.moveSun(time);
        }
    }

    private void sunDrop(long time){
        if(time % 10000 == 0){
            Sun s = new Sun(time, SunType.FALLEN);
            addSun(s);
        }
    }
}
