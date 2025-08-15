package main.plantsvszombies.Game;


import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import main.plantsvszombies.Enums.GameMode;
import main.plantsvszombies.Enums.SunType;
import main.plantsvszombies.Game.Tools.Constants;
import main.plantsvszombies.Game.Tools.ImageFactory;
import main.plantsvszombies.Items.Sun;

public class ScoreBoard {

    private static final int SUN_VALUE = 25;
    private static final int SUN_LIFE_TIME = 7500;

    private final BorderPane pane;
    private final Label scoreLabel;
    private final GameMode mode;
    private final ArrayList<Sun> suns = new ArrayList<>();
    private int score;

    public ScoreBoard(BorderPane pane, int score, GameMode mode) {
        this.pane = pane;
        this.score = score;
        scoreLabel = createScoreLabel();
        this.mode = mode;
        setScoreBoardOnPane();
    }

    private void setScoreBoardOnPane() {
        ImageView board = ImageFactory.createScoreBoardPicture();
        pane.getChildren().add(1, board);
        pane.setLeft(scoreLabel);
    }

    private Label createScoreLabel() {
        Label scoreLabel = new Label(score + "");
        Font font = Font.loadFont(getClass().getResource("/fonts/BreakdownPG.otf").toExternalForm(), 40);
        scoreLabel.setFont(font);
        scoreLabel.setStyle("-fx-text-fill: rgb(25, 25, 25);");
        scoreLabel.setPadding(new Insets(-Constants.SCREEN_HEIGHT / 30, 0, 0, Constants.SCREEN_WIDTH / 26.3));
        return scoreLabel;
    }

    // updates the scoreBoard for sun points
    public void addSun(Sun sun) {
        if (sun == null) return;

        ImageView sunImage = sun.getPicture();
        sunImage.setOnMouseClicked(event -> {
            sun.setType(SunType.COLLECTED);
            Sun.sound.play();
        });
        pane.getChildren().add(sunImage);
        suns.add(sun);
    }

    private void collectSun(Sun sun) {
        score += SUN_VALUE;
        scoreLabel.setText(score + "");
        removeSunFromPane(sun);
    }

    public void handleSuns() {
        sunDrop();
        cleanUpSuns();
        fallenSun();
    }

    // removes unclicked suns after time window is up
    private void cleanUpSuns() {
        for (Sun sun : suns) {
            if (Math.abs(sun.getTimeCreated() - Constants.gameTime) >= SUN_LIFE_TIME) {
                removeSunFromPane(sun);
                break;
            }
            if (Math.abs(sun.getPicture().getLayoutX() - Constants.SCREEN_WIDTH / 40) < 5) {
                collectSun(sun);
                break;
            }
        }
    }

    private void removeSunFromPane(Sun sun) {
        pane.getChildren().remove(sun.getPicture());
        suns.remove(sun);
    }

    private void fallenSun() {
        for (Sun s : suns) s.moveSun();
    }

    // manage fallen sun movement
    private void sunDrop() {
        if (Constants.gameTime % 10000 == 0 && mode == GameMode.DAY) {
            Sun s = new Sun(SunType.BASE_FALLEN);
            addSun(s);
        }
    }

    // checks if a plant can be purchased
    public boolean purchasePlant(int price) {
        if (score < price) return false;

        score -= price;
        scoreLabel.setText(score + "");
        return true;
    }

    public int getScore() {
        return score;
    }
}
