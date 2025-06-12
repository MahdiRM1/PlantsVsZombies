package main.plantsvszombies;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.ArrayList;

public class ScoreBoard {

    private int score;
    BorderPane pane;
    Label scoreLabel;
    private final ArrayList<Sun> suns = new ArrayList<>();

    public ScoreBoard(BorderPane pane){
        this.pane = pane;
        score = 50;
        ImageView board = new ImageView(new Image("file:Pictures/ScoreBoard/ChooserBackground.png"));
        board.setFitWidth(Constants.height/1.1);
        board.setFitHeight(Constants.height/5.5);
        board.setLayoutY(Constants.height/29.25);
        pane.getChildren().addAll(board);

        scoreLabel = new Label(score + "");
        Font font = Font.font("Arial", FontWeight.BOLD, 40);
        scoreLabel.setFont(font);
        scoreLabel.setTextFill(Color.BLACK);
        scoreLabel.setPadding(new Insets(-Constants.height/25,0,0,Constants.height/30));
        pane.setLeft(scoreLabel);
    }

    public void addSun(Sun s){
        if(s != null) {
            ImageView image = s.getPicture();
            image.setOnMouseClicked(event -> {
                score += 25;
                scoreLabel.setText(score + "");
                pane.getChildren().remove(image);
                suns.remove(s);
            });
            pane.getChildren().add(image);
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
            if(Math.abs(suns.get(i).getTimeCreated() - time) >= 5000) {
                pane.getChildren().remove(suns.get(i).getPicture());
                suns.remove(i);
            }
        }
    }

    private void fallenSun(long time){
        for (Sun s : suns) s.moveSun(time);
    }

    private void sunDrop(long time){
        if(time % 10000 == 0){
            Sun s = new Sun(time, SunType.FALLEN);
            addSun(s);
        }
    }

    public void purchasePlant(int price) {
        score -= price;
        scoreLabel.setText(score + "");
    }

    public int getScore() {
        return score;
    }
}
