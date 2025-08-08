package main.plantsvszombies.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import main.plantsvszombies.Enums.GameMode;

public class PlantSelection{
    private final List<String> selectedCards = new ArrayList<>();
    private final HBox cardBar;
    private final GameMode gameMode;
    private final Stage stage;
    private static final AudioClip backgroundMusic;

    static {
        backgroundMusic = new AudioClip("file:Audio/LookupattheSky.mp3");
    }

    public PlantSelection(Stage stage, GameMode mode){
        this.stage = stage;
        backgroundMusic.play();
        Pane pane = new Pane();
        gameMode = mode;
        pane.getChildren().add(Constants.setBackGround(
                (gameMode == GameMode.DAY) ? "plantSelectionDay" : "plantSelectionNight"));

        cardBar = new HBox(0);
        Constants.positionNode(cardBar, Constants.CARD_BAR_X, Constants.CARD_BAR_Y);

        VBox box = new VBox(10, plants());
        Constants.positionNode(box, Constants.SCREEN_WIDTH / 15, Constants.SCREEN_HEIGHT / 4);

        pane.getChildren().addAll(Constants.setScoreBoardPicture(), cardBar, box, startGameBtn());

        for (int i = 0; i < 8; i++) pane.getChildren().add(createZombie((int) (Math.random() * 5)));

        Scene scene = new Scene(pane, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT - 35);
        stage.setScene(scene);
    }

    private HBox[] plants(){
        double cardSpacing = Constants.SCREEN_WIDTH / 80;

        HBox box1 = new HBox(cardSpacing,
                getCardButton("PeaShooter"), getCardButton("SunFlower"),
                getCardButton("PotatoMine"), getCardButton("SnowPea"),
                getCardButton("Repeater")
        );
        HBox box2 = new HBox(cardSpacing,
                getCardButton("CherryBomb"), getCardButton("Jalapeno"),
                getCardButton("WallNut"), getCardButton("TallNut"),
                getCardButton("CoffeeBean")
        );
        HBox box3 = new HBox(cardSpacing,
                getCardButton("PuffShroom"), getCardButton("ScaredyShroom"),
                getCardButton("IceShroom"), getCardButton("HypnoShroom"),
                getCardButton("GraveBuster")
        );
        HBox box4 = new HBox(cardSpacing,
                getCardButton("DoomShroom"), getCardButton("Plantern"),
                getCardButton("Blover")
        );

        return new HBox[]{box1, box2, box3, box4};
    }

    private ImageView startGameBtn() {
        Image letsRock1 = new Image("file:Pictures/ui/LetsRock1.png");
        Image letsRock2 = new Image("file:Pictures/ui/LetsRock2.png");
        ImageView start = new ImageView(letsRock1);
        Constants.sizeNode(start, Constants.SCREEN_WIDTH / 7.5, Constants.SCREEN_HEIGHT / 16);
        Constants.positionNode(start, Constants.SCREEN_WIDTH / 5.65, Constants.SCREEN_HEIGHT / 1.122);

        start.setOnMouseEntered(event -> {
            if (selectedCards.size() != 6) return;

            start.setImage(letsRock2);
            Constants.changeScale(start, 1.05);
        });
        start.setOnMouseExited(event -> {
            if (selectedCards.size() != 6) return;

            start.setImage(letsRock1);
            Constants.changeScale(start, 1);
        });
        start.setOnMouseClicked(event -> {
            if (selectedCards.size() != 6) return;

            startGame();
        });
        return start;
    }

    private void startGame(){
        GlobalState.playClickTrack();
        backgroundMusic.stop();
        AudioClip startGame = Constants.setSound("readysetplant", false);
        startGame.play();
        new GameUI(selectedCards, stage, gameMode);
    }

    private Button getCardButton(String plantName) {
        Button btn = new Button();
        btn.setGraphic(Constants.setCard(plantName));
        btn.setStyle("-fx-background-color: transparent");

        btn.setOnAction(event -> plantBtnAction(btn, plantName));
        btn.setOnMouseEntered(event -> Constants.changeScale(btn.getGraphic(), 1.05));
        btn.setOnMouseExited(event -> {
            Constants.changeScale(btn.getGraphic(), 1);
            btn.setStyle("-fx-background-color: transparent;");
        });
        return btn;
    }

    private void plantBtnAction(Button btn, String plantName){
        if (selectedCards.contains(plantName)) {
            selectedCards.remove(plantName);
            cardBar.getChildren().removeIf(node
                    -> ((ImageView) ((Button) node).getGraphic()).getImage()//image on button in cardBar
                    .equals(((ImageView) btn.getGraphic()).getImage()));//image on clicked btn
        } else if (selectedCards.size() < 6) {
            if ((plantName.equals("CoffeeBean") && gameMode == GameMode.NIGHT)
                    || (plantName.equals("GraveBuster") && gameMode == GameMode.DAY)) {
                btn.setStyle("-fx-background-color: rgb(150, 0, 0);");
                return;
            }
            selectedCards.add(plantName);
            Button btn2 = getCardButton(plantName);
            ImageView imageView = new ImageView(((ImageView) btn.getGraphic()).getImage());
            Constants.sizeNode(imageView, Constants.PLANT_CARD_WIDTH, Constants.PLANT_CARD_HEIGHT);
            btn2.setGraphic(imageView);
            cardBar.getChildren().add(btn2);
        }
        else btn.setStyle("-fx-background-color: rgb(150, 0, 0);");
    }

    private ImageView createZombie(int z) {
        Random rdm = new Random();
        String[] zombieTypes = {"OriginalZombie", "ConeheadZombie", "ScreenDoorZombie", "BucketheadZombie", "Imp"};
        String chosen = zombieTypes[z];

        ImageView image = new ImageView(new Image("file:Pictures/ZombiePicture/" + chosen + "/gif.gif"));
        Constants.sizeNode(image, Constants.ZOMBIE_PIC_WIDTH, Constants.ZOMBIE_PIC_HEIGHT);
        Constants.positionNode(image,
                Constants.SCREEN_WIDTH / 1.5 + rdm.nextDouble(Constants.SCREEN_WIDTH / 4),
                rdm.nextDouble(Constants.SCREEN_HEIGHT / 1.3));
        return image;
    }
}