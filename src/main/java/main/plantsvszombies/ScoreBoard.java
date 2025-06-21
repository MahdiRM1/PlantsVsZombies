package main.plantsvszombies;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.ArrayList;

public class ScoreBoard {

    private int score;
    private final BorderPane pane;
    private final Label scoreLabel;
    private final ArrayList<Sun> suns = new ArrayList<>();

    public ScoreBoard(BorderPane pane, int score){
        this.pane = pane;
        this.score = score;
        ImageView board = Constants.setScoreBoardPicture();
        pane.getChildren().add(1, board);
        scoreLabel = new Label(score + "");
        Font font = Font.font("Arial", FontWeight.BOLD, Constants.height/25.6);
        scoreLabel.setFont(font);
        scoreLabel.setTextFill(Color.BLACK);
        scoreLabel.setPadding(new Insets(-Constants.height/30,0,0,Constants.height/14));
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

    public void sunHandler(){
        sunDrop();
        garbageSuns();
        fallenSun();
    }

    private void garbageSuns(){
        for (int i = 0; i < suns.size(); i++) {
            if(Math.abs(suns.get(i).getTimeCreated() - GlobalState.gameTime) >= 5000) {
                pane.getChildren().remove(suns.get(i).getPicture());
                suns.remove(i);
            }
        }
    }

    private void fallenSun(){
        for (Sun s : suns) s.moveSun();
    }

    private void sunDrop(){
        if(GlobalState.gameTime % 10000 == 0){
            Sun s = new Sun(SunType.FALLEN);
            addSun(s);
        }
    }

    public boolean purchasePlant(int price) {
        if(score >= price){
            score -= price;
            scoreLabel.setText(score + "");
            return true;
        }
        return false;
    }

    public int getScore() {
        return score;
    }
}
