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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import main.plantsvszombies.Enums.GameMode;
import main.plantsvszombies.Game.PlayModes.Client;
import main.plantsvszombies.Game.PlayModes.PlayMode;
import main.plantsvszombies.Game.Tools.Constants;
import main.plantsvszombies.Game.Tools.ImageFactory;
import main.plantsvszombies.Game.Tools.SoundManager;
import main.plantsvszombies.Game.Tools.Utils;

public class PlantSelection{
    private final List<String> selectedCards = new ArrayList<>();
    private final GameMode gameMode;
    private final Stage stage;
    private final StackPane mainPane;
    private AudioClip backgroundMusic;
    private HBox cardBar;
    private final PlayMode playMode;

    public PlantSelection(Stage stage, GameMode mode, PlayMode playMode){
        this.stage = stage;
        this.playMode = playMode;
        mainPane = new StackPane();
        gameMode = mode;
        music();
        createPane();
        Scene scene = new Scene(mainPane, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT - 35);
        scene.getStylesheets().add(getClass().getResource("/styles/ui.css").toExternalForm());
        stage.setScene(scene);
    }

    private void music(){
        backgroundMusic = new AudioClip(getClass().getResource("/Audio/LookupattheSky.mp3").toExternalForm());
        backgroundMusic.setVolume(SoundManager.music);
        backgroundMusic.setCycleCount(-1);
        backgroundMusic.play();
    }

    private void createPane(){
        Pane pane = new Pane();
        pane.getChildren().add(ImageFactory.createBackGround(
                (gameMode == GameMode.DAY) ? "plantSelectionDay" : "plantSelectionNight"));

        Button menu = Utils.createMenuButton("MENU", Constants.SCREEN_WIDTH / 9.4, Constants.SCREEN_HEIGHT / 16);
        menu.setOnMouseClicked(event -> menu());
        ImageFactory.setNodePosition(menu, Constants.SCREEN_WIDTH - menu.getPrefWidth(), 0);

        cardBar = new HBox(0);
        ImageFactory.setNodePosition(cardBar, Constants.CARD_BAR_X, Constants.CARD_BAR_Y);

        VBox box = new VBox(10, plants());
        ImageFactory.setNodePosition(box, Constants.SCREEN_WIDTH / 15, Constants.SCREEN_HEIGHT / 4);

        pane.getChildren().addAll(ImageFactory.createScoreBoardPicture(), cardBar, box, startGameBtn(), menu);

        for (int i = 0; i < 8; i++) pane.getChildren().add(createZombie((int) (Math.random() * 5)));
        mainPane.getChildren().add(pane);
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

    private Button startGameBtn() {
        Button start = Utils.createButton("Let's Rock", Constants.SCREEN_WIDTH / 7, Constants.SCREEN_HEIGHT / 14);
        ImageFactory.setNodePosition(start, Constants.SCREEN_WIDTH / 5.7, Constants.SCREEN_HEIGHT / 1.126);
        start.setStyle("-fx-text-fill: rgba(50, 50, 50, 0.7);");

        start.setOnMouseEntered(event -> {
            if (selectedCards.size() != 6) return;

            start.setStyle("-fx-text-fill: #CF9929;");
            ImageFactory.changeScale(start, 1.1);
        });
        start.setOnMouseExited(event -> {
            if (selectedCards.size() != 6) return;

            start.setStyle("-fx-text-fill: rgba(50, 50, 50, 0.7);");
            ImageFactory.changeScale(start, 1);
        });
        start.setOnMouseClicked(event -> {
            if (selectedCards.size() != 6) return;

            startGame();
        });
        return start;
    }

    private void startGame() {
        SoundManager.playClickTrack();
        backgroundMusic.stop();
        if (playMode instanceof Client client) {
            client.ready();
            client.waitForPlayers();
        }
        new GameUI(selectedCards, stage, gameMode, playMode);
    }

    private Button getCardButton(String plantName) {
        Button btn = new Button();
        btn.setGraphic(ImageFactory.createCard(plantName));
        btn.setStyle("-fx-background-color: transparent");

        btn.setOnAction(event -> plantBtnAction(btn, plantName));
        btn.setOnMouseEntered(event -> ImageFactory.changeScale(btn.getGraphic(), 1.05));
        btn.setOnMouseExited(event -> {
            ImageFactory.changeScale(btn.getGraphic(), 1);
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
            ImageFactory.setNodeSize(imageView, Constants.PLANT_CARD_WIDTH, Constants.PLANT_CARD_HEIGHT);
            btn2.setGraphic(imageView);
            cardBar.getChildren().add(btn2);
        }
        else btn.setStyle("-fx-background-color: rgb(150, 0, 0);");
    }

    private ImageView createZombie(int z) {
        Random rdm = new Random();
        String[] zombieTypes = {"OriginalZombie", "ConeheadZombie", "ScreenDoorZombie", "BucketheadZombie", "Imp"};
        String chosen = zombieTypes[z];

        ImageView image = new ImageView(new Image(getClass().getResource("/Pictures/ZombiePicture/" + chosen + "/gif.gif").toExternalForm()));
        ImageFactory.setNodeSize(image, Constants.ZOMBIE_PIC_WIDTH, Constants.ZOMBIE_PIC_HEIGHT);
        ImageFactory.setNodePosition(image,
                Constants.SCREEN_WIDTH / 1.5 + rdm.nextDouble(Constants.SCREEN_WIDTH / 4),
                rdm.nextDouble(Constants.SCREEN_HEIGHT / 1.3));
        return image;
    }

    // generate the menu pane
    private void menu(){
        SoundManager.playSound("pause");

        Pane menuPane = Utils.createMenu();
        menuPane.setStyle("-fx-background-color: rgba(56, 56, 56, 0.7);");

        Button mainMenu = mainMenuBtn();
        Button backToGame = backToGame();

        menuPane.getChildren().addAll(backToGame, mainMenu);
        mainPane.getChildren().add(menuPane);
    }

    private Button mainMenuBtn(){
        Button mainMenu = Utils.createMenuButton("MAIN MENU", Constants.SCREEN_WIDTH / 5, Constants.SCREEN_HEIGHT / 15);
        ImageFactory.setNodePosition(mainMenu, Constants.SCREEN_WIDTH / 2.48, Constants.SCREEN_HEIGHT / 1.6);
        mainMenu.setOnMouseClicked(event -> {
            backgroundMusic.stop();
            SoundManager.playClickTrack();
            new Introduction(stage).firstPage();
        });
        return mainMenu;
    }

    private Button backToGame(){
        Button backToGame = Utils.submitButton("Back To Game");
        backToGame.setOnMouseClicked(e -> {
            SoundManager.playClickTrack();
            mainPane.getChildren().removeLast();
            if (SoundManager.music != backgroundMusic.getVolume()){
                backgroundMusic.stop();
                backgroundMusic.setVolume(SoundManager.music);
                backgroundMusic.play();
            }
        });
        return backToGame;
    }
}