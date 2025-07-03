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
    private static final int SUN_VALUE = 25;
    private static final int SUN_LIFE_TIME = 7500;

    private int score;
    private final BorderPane pane;
    private final Label scoreLabel;
    private final ArrayList<Sun> suns = new ArrayList<>();

    public ScoreBoard(BorderPane pane, int score) {
        this.pane = pane;
        this.score = score;
        scoreLabel = createScoreLabel();
        setScoreBoardOnPane();
    }

    private void setScoreBoardOnPane() {
        ImageView board = Constants.setScoreBoardPicture();
        pane.getChildren().add(1, board);
        pane.setLeft(scoreLabel);
    }

    private Label createScoreLabel() {
        Label scoreLabel = new Label(score + "");
        Font font = Font.font("Arial", FontWeight.BOLD, Constants.SCREEN_HEIGHT / 25.6);
        scoreLabel.setFont(font);
        scoreLabel.setTextFill(Color.BLACK);
        scoreLabel.setPadding(new Insets(-Constants.SCREEN_HEIGHT / 30, 0, 0, Constants.SCREEN_WIDTH / 26.3));
        return scoreLabel;
    }

    // updates the scoreBoard for sun points
    public void addSun(Sun sun) {
        if (sun == null)
            return;

        ImageView sunImage = sun.getPicture();
        sunImage.setOnMouseClicked(event -> collectSun(sun));
        pane.getChildren().add(sunImage);
        suns.add(sun);
    }

    private void collectSun(Sun sun) {
        score += SUN_VALUE;
        scoreLabel.setText(score + "");
        pane.getChildren().remove(sun.getPicture());
        suns.remove(sun);
    }

    public void handleSuns() {
        sunDrop();
        cleanUpSuns();
        fallenSun();
    }

    // removes unclicked suns after time window is up
    private void cleanUpSuns() {
        for (Sun sun : suns) {
            if (Math.abs(sun.getTimeCreated() - GlobalState.gameTime) >= SUN_LIFE_TIME) {
                pane.getChildren().remove(sun.getPicture());
                suns.remove(sun);
            }
        }
    }

    private void fallenSun() {
        for (Sun s : suns)
            s.moveSun();
    }

    // manage fallen sun movement
    private void sunDrop() {
        if (GlobalState.gameTime % 10000 == 0) {
            Sun s = new Sun(SunType.FALLEN);
            addSun(s);
        }
    }

    // checks if a plant can be purchased
    public boolean purchasePlant(int price) {
        if (score < price)
            return false;

        score -= price;
        scoreLabel.setText(score + "");
        return true;
    }

    public int getScore() {
        return score;
    }
}