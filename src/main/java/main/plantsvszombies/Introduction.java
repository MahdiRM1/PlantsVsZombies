package main.plantsvszombies;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Introduction {

    private final Stage stage;
    private final List<String> selectedCards = new ArrayList<>();
    private GameMode mode;
    private HBox cardBar;
    private static final AudioClip backgroundMusic;

    static {
        backgroundMusic = new AudioClip("file:Audio/LookupattheSky.mp3");
    }

    public Introduction(Stage stage) {
        backgroundMusic.setCycleCount(Timeline.INDEFINITE);
        backgroundMusic.setVolume(GlobalState.music);
        backgroundMusic.play();
        this.stage = stage;
    }

    public void firstPage() {
        Scene scene = new Scene(MainMenuPane(), Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT - 35);
        stage.setScene(scene);
        stage.show();
    }

    private void load() {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("savegame.dat"))) {
            GameState state = (GameState) input.readObject();
            backgroundMusic.stop();
            new GameUI(stage, state);
            System.out.println("game loaded");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("cant load data");
        }
    }

    public void plantSelectionPage(GameMode mode) {
        this.mode = mode;
        plantSelectionPage();
    }

    private void plantSelectionPage() {
        Pane pane = new Pane();
        pane.getChildren().add(Constants.setBackGround(
                (mode == GameMode.DAY) ? "plantSelectionDay" : "plantSelectionNight"));

        cardBar = new HBox(0);
        Constants.positionNode(cardBar, Constants.CARD_BAR_X, Constants.CARD_BAR_Y);

        double cardSpacing = Constants.SCREEN_WIDTH / 80;
        double layoutX = Constants.SCREEN_WIDTH / 15;

        HBox box1 = new HBox(cardSpacing,
                getCardButton("PeaShooter"), getCardButton("SunFlower"),
                getCardButton("PotatoMine"), getCardButton("SnowPea"),
                getCardButton("Repeater")
        );
        Constants.positionNode(box1, layoutX, Constants.SCREEN_HEIGHT / 4);

        HBox box2 = new HBox(cardSpacing,
                getCardButton("CherryBomb"), getCardButton("Jalapeno"),
                getCardButton("WallNut"), getCardButton("TallNut"),
                getCardButton("CoffeeBean")

        );
        Constants.positionNode(box2, layoutX, Constants.SCREEN_HEIGHT / 2.5);

        HBox box3 = new HBox(cardSpacing,
                getCardButton("PuffShroom"), getCardButton("ScaredyShroom"),
                getCardButton("IceShroom"), getCardButton("HypnoShroom"),
                getCardButton("GraveBuster")
        );
        Constants.positionNode(box3, layoutX, Constants.SCREEN_HEIGHT / 1.8);

        HBox box4 = new HBox(cardSpacing,
                getCardButton("DoomShroom"), getCardButton("Plantern"),
                getCardButton("Blover")
        );
        Constants.positionNode(box4, layoutX, Constants.SCREEN_HEIGHT / 1.4);

        pane.getChildren().addAll(Constants.setScoreBoardPicture(),
                cardBar, box1, box2, box3, box4, startGameBtn());
        for (int i = 0; i < 8; i++) {
            pane.getChildren().add(createZombie((int)(Math.random()*5)));
        }
        Scene scene = new Scene(pane, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT - 35);
        stage.setScene(scene);
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

            GlobalState.playClickTrack();
            AudioClip startGame = Constants.setSound("readysetplant", false);
            startGame.play();
            backgroundMusic.stop();
            new GameUI(stage, selectedCards, mode);
        });
        return start;
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

    private Button getCardButton(String plantName) {
        Button btn = new Button();
        btn.setGraphic(Constants.setCard(plantName));
        btn.setStyle("-fx-background-color: transparent");

        btn.setOnAction(event -> {
            if (selectedCards.contains(plantName)) {
                selectedCards.remove(plantName);
                cardBar.getChildren().removeIf(node
                        -> ((ImageView) ((Button) node).getGraphic()).getImage()//image on button in cardBar
                                .equals(((ImageView) btn.getGraphic()).getImage()));//image on clicked btn
            } else if (selectedCards.size() < 6) {
                if ((plantName.equals("CoffeeBean") && mode == GameMode.NIGHT)
                        || (plantName.equals("GraveBuster") && mode == GameMode.DAY)) {
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
        });
        btn.setOnMouseEntered(event -> Constants.changeScale(btn.getGraphic(), 1.05));
        btn.setOnMouseExited(event -> {
            Constants.changeScale(btn.getGraphic(), 1);
            btn.setStyle("-fx-background-color: transparent;");
        });
        return btn;
    }

    private Pane MainMenuPane() {
        StackPane mainpane = new StackPane();
        Pane pane = new Pane();
        pane.getChildren().addFirst(Constants.setBackGround("MainMenu"));

        ImageView adventure = Constants.setButton("Adventure", Constants.SCREEN_WIDTH / 2.6, Constants.SCREEN_HEIGHT / 4.2);
        Constants.positionNode(adventure, Constants.SCREEN_WIDTH / 1.97, Constants.SCREEN_HEIGHT / 8);
        adventure.setOnMouseClicked(event -> handAnimation(pane, false));

        ImageView socket = Constants.setButton("Multiplayer", Constants.SCREEN_WIDTH / 2.7, Constants.SCREEN_HEIGHT / 4.35);
        Constants.positionNode(socket, Constants.SCREEN_WIDTH / 1.98, Constants.SCREEN_HEIGHT / 3.1);
        socket.setOnMouseEntered(e -> {});
        socket.setOnMouseExited(e -> {});
        socket.setEffect(Constants.effect(0, 0, -0.5, 0));
        socket.setOnMouseClicked(event -> GlobalState.playWrongClick());

        ImageView loadGame = new ImageView(new Image("file:Pictures/ui/LoadGame.png"));
        Constants.sizeNode(loadGame, Constants.SCREEN_WIDTH / 3, Constants.SCREEN_HEIGHT / 4.8);
        Constants.positionNode(loadGame, Constants.SCREEN_WIDTH / 1.94, Constants.SCREEN_HEIGHT / 2);
        if (dataExists()) {
            loadGame.setOnMouseEntered(event -> Constants.changeScale(loadGame, 1.05));
            loadGame.setOnMouseExited(event -> Constants.changeScale(loadGame, 1));
            loadGame.setOnMouseClicked(event -> {
                GlobalState.playClickTrack();
                handAnimation(pane, true);
            });
        }else {
            loadGame.setEffect(Constants.effect(0, 0, -0.5, 0));
            loadGame.setOnMouseClicked(event -> GlobalState.playWrongClick());
        }

        ImageView quit = Constants.setButton("Quit", Constants.SCREEN_WIDTH / 8.4, Constants.SCREEN_HEIGHT / 6);
        Constants.positionNode(quit, Constants.SCREEN_WIDTH / 1.14, Constants.SCREEN_HEIGHT / 1.405);
        quit.setOnMouseClicked(event -> checkQuit(mainpane));

        ImageView help = Constants.setButton("help", Constants.SCREEN_WIDTH / 8.4, Constants.SCREEN_HEIGHT / 4);
        Constants.positionNode(help, Constants.SCREEN_WIDTH / 1.28, Constants.SCREEN_HEIGHT / 1.54);
        help.setOnMouseClicked(e -> helpPage(mainpane));

        ImageView options = Constants.setButton("option", Constants.SCREEN_WIDTH / 6.55, Constants.SCREEN_HEIGHT / 5.4);
        Constants.positionNode(options, Constants.SCREEN_WIDTH / 1.47, Constants.SCREEN_HEIGHT / 1.475);
        options.setOnMouseClicked(e -> option(mainpane));

        pane.getChildren().addAll(adventure, socket, loadGame, quit, options, help);
        mainpane.getChildren().add(pane);
        return mainpane;
    }

    private void helpPage(StackPane mainPane){
        Pane helpPage = new Pane();
        GlobalState.playClickTrack();
        
        ImageView backGround = Constants.setBackGround("help");
        ImageView mainmenu = Constants.setButton("mainmenuhelp", Constants.SCREEN_WIDTH / 6 , Constants.SCREEN_HEIGHT / 12);
        Constants.positionNode(mainmenu, Constants.SCREEN_WIDTH / 2.4, Constants.SCREEN_HEIGHT / 1.25);
        mainmenu.setOnMouseClicked(e -> {
            GlobalState.playClickTrack();
            mainPane.getChildren().removeLast();
        });

        helpPage.getChildren().addAll(backGround, mainmenu);
        mainPane.getChildren().add(helpPage);
    }

    private void checkQuit(StackPane mainPane){
        Pane quitPage = new Pane();
        GlobalState.playClickTrack();

        ImageView quitImg = new ImageView(new Image("file:Pictures/ui/quitPic.png"));
        Constants.positionNode(quitImg, Constants.SCREEN_WIDTH / 3.2, Constants.SCREEN_HEIGHT / 4);
        Constants.sizeNode(quitImg, Constants.SCREEN_WIDTH / 2.6, Constants.SCREEN_HEIGHT / 2);

        ImageView quit = Constants.setButton("quitBtn", Constants.SCREEN_WIDTH / 6.7, Constants.SCREEN_HEIGHT / 13.5);
        Constants.positionNode(quit, Constants.SCREEN_WIDTH / 2.9, Constants.SCREEN_HEIGHT / 1.58);
        quit.setOnMouseClicked(e -> {
            GlobalState.playClickTrack();
            stage.close();
        });

        ImageView cancel = Constants.setButton("cancel", Constants.SCREEN_WIDTH / 6.7, Constants.SCREEN_HEIGHT / 13.5);
        Constants.positionNode(cancel, Constants.SCREEN_WIDTH / 1.95, Constants.SCREEN_HEIGHT / 1.58);
        cancel.setOnMouseClicked(e -> {
            GlobalState.playClickTrack();
            mainPane.getChildren().removeLast();
        });

        quitPage.getChildren().addAll(quitImg, quit, cancel);
        mainPane.getChildren().add(quitPage);
    }

    private void option(StackPane pane){
        GlobalState.playClickTrack();
        Pane option = new Pane();
        ImageView optionImg = new ImageView(new Image("file:Pictures/ui/optionPic.png"));
        Constants.positionNode(optionImg, Constants.SCREEN_WIDTH / 3.3, Constants.SCREEN_HEIGHT / 10);
        Constants.sizeNode(optionImg, Constants.SCREEN_WIDTH / 2.5, Constants.SCREEN_HEIGHT / 1.25);

        ImageView OK = Constants.setButton("OK", Constants.SCREEN_WIDTH / 3, Constants.SCREEN_HEIGHT / 7.5);
        Constants.positionNode(OK, Constants.SCREEN_WIDTH / 2.97, Constants.SCREEN_HEIGHT / 1.34);
        OK.setOnMouseClicked(e -> {
            GlobalState.playClickTrack();
            pane.getChildren().removeLast();
            if (GlobalState.music != backgroundMusic.getVolume()){
                backgroundMusic.stop();
                backgroundMusic.setVolume(GlobalState.music);
                backgroundMusic.play();
            }
        });

        Slider music = new Slider(0, 1, GlobalState.music);
        Constants.setSlider(music);
        music.setLayoutY(Constants.SCREEN_HEIGHT / 3);
        music.valueProperty().addListener((obs, oldVal, newVal) -> GlobalState.music = newVal.doubleValue());

        Slider volume = new Slider(0, 1, GlobalState.volume);
        Constants.setSlider(volume);
        volume.setLayoutY(Constants.SCREEN_HEIGHT / 3 + Constants.SCREEN_HEIGHT / 10);
        volume.valueProperty().addListener((obs, oldVal, newVal) -> GlobalState.volume = newVal.doubleValue());

        option.getChildren().addAll(optionImg, OK, music, Constants.setSliderLabel("Music", music), volume, Constants.setSliderLabel("Volume", volume));
        pane.getChildren().add(option);
    }

    private void handAnimation(Pane pane, boolean load){
        ImageView hand = new ImageView(new Image("file:Pictures/ui/handGif.gif"));
        double size = Constants.SCREEN_HEIGHT / 2;
        Constants.positionNode(hand, Constants.SCREEN_WIDTH / 3 , Constants.SCREEN_HEIGHT / 2);
        Constants.sizeNode(hand, size, size);
        pane.getChildren().add(hand);
        backgroundMusic.stop();
        AudioClip laugh = Constants.setSound("evillaugh", false);
        laugh.play();
        Timeline tl = new Timeline(new KeyFrame(Duration.seconds(2)));
        tl.setOnFinished(e -> {
            if(load) load();
            else modeSelection();
        });
        tl.setCycleCount(2);
        tl.play();
    }

    private void modeSelection(){
        backgroundMusic.play();
        Pane pane = new Pane();
        pane.getChildren().addFirst(Constants.setBackGround("ModeSelection"));

        ImageView day = Constants.setButton("DayMode", Constants.SCREEN_WIDTH / 5, Constants.SCREEN_HEIGHT / 2);
        Constants.positionNode(day, Constants.SCREEN_WIDTH / 4, Constants.SCREEN_HEIGHT / 4);
        day.setOnMouseClicked(e -> {
            GlobalState.playClickTrack();
            mode = GameMode.DAY;
            plantSelectionPage();
        });

        double sizePlant = Constants.SCREEN_HEIGHT / 6.3;
        ImageView plant = new ImageView(new Image("file:Pictures/plantPictures/SunFlower/gif.gif"));
        Constants.sizeNode(plant, sizePlant, sizePlant);
        Constants.positionNode(plant, Constants.SCREEN_WIDTH / 3.3, Constants.SCREEN_HEIGHT / 3.7);

        ImageView night = Constants.setButton("NightMode", Constants.SCREEN_WIDTH / 5, Constants.SCREEN_HEIGHT / 2);
        Constants.positionNode(night, Constants.SCREEN_WIDTH / 1.8, Constants.SCREEN_HEIGHT / 4);
        night.setOnMouseClicked(e -> {
            GlobalState.playClickTrack();
            mode = GameMode.NIGHT;
            plantSelectionPage();
        });

        ImageView zombie = createZombie(0);
        Constants.positionNode(zombie, Constants.SCREEN_WIDTH / 1.68, Constants.SCREEN_HEIGHT / 3.9);

        pane.getChildren().addAll(plant, day, zombie, night);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
    }

    private boolean dataExists(){
        File file = new File("savegame.dat");
        return file.exists();
    }
}
