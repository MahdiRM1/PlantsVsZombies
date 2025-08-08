package main.plantsvszombies.Game;

import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.plantsvszombies.Enums.GameMode;
import main.plantsvszombies.GameState.GameState;

public class Introduction {

    private final Stage stage;
    private static final AudioClip backgroundMusic;

    static {
        backgroundMusic = Constants.setSound("introMusic", true);
    }

    public Introduction(Stage stage) {
        backgroundMusic.setVolume(GlobalState.music);
        backgroundMusic.play();
        this.stage = stage;
    }

    public void firstPage() {
        Scene scene = new Scene(MainMenuPane(), Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT - 35);
        stage.setScene(scene);
        stage.show();
    }

    private Pane MainMenuPane() {
        StackPane mainpane = new StackPane();
        Pane pane = new Pane();
        pane.getChildren().addFirst(Constants.setBackGround("MainMenu"));

        ImageView adventure = Constants.setButton("Adventure", Constants.SCREEN_WIDTH / 2.6, Constants.SCREEN_HEIGHT / 4.2);
        Constants.positionNode(adventure, Constants.SCREEN_WIDTH / 1.97, Constants.SCREEN_HEIGHT / 8);
        adventure.setOnMouseClicked(event -> handAnimation(pane, "default"));

        ImageView socket = Constants.setButton("Multiplayer", Constants.SCREEN_WIDTH / 2.7, Constants.SCREEN_HEIGHT / 4.35);
        Constants.positionNode(socket, Constants.SCREEN_WIDTH / 1.98, Constants.SCREEN_HEIGHT / 3.1);
        socket.setOnMouseEntered(e -> {});
        socket.setOnMouseExited(e -> {});
        socket.setEffect(Constants.effect(0, 0, -0.5, 0));
        socket.setOnMouseClicked(event -> GlobalState.playWrongClick());

        ImageView loadGame = new ImageView(new Image("file:Pictures/ui/LoadGame.png"));
        Constants.sizeNode(loadGame, Constants.SCREEN_WIDTH / 3, Constants.SCREEN_HEIGHT / 4.8);
        Constants.positionNode(loadGame, Constants.SCREEN_WIDTH / 1.94, Constants.SCREEN_HEIGHT / 2);
        if (Constants.dataExists()) {
            loadGame.setOnMouseEntered(event -> Constants.changeScale(loadGame, 1.05));
            loadGame.setOnMouseExited(event -> Constants.changeScale(loadGame, 1));
            loadGame.setOnMouseClicked(event -> {
                GlobalState.playClickTrack();
                handAnimation(pane, "load");
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
        music.valueProperty().addListener((obs, oldVal, newVal)
                -> GlobalState.music = newVal.doubleValue());

        Slider volume = new Slider(0, 1, GlobalState.volume);
        Constants.setSlider(volume);
        volume.setLayoutY(Constants.SCREEN_HEIGHT / 3 + Constants.SCREEN_HEIGHT / 10);
        volume.valueProperty().addListener((obs, oldVal, newVal)
                -> GlobalState.volume = newVal.doubleValue());

        option.getChildren().addAll(optionImg, OK, music, Constants.setSliderLabel("Music", music),
                volume, Constants.setSliderLabel("Volume", volume));
        pane.getChildren().add(option);
    }

    private void handAnimation(Pane pane, String mode){
        ImageView hand = new ImageView(new Image("file:Pictures/ui/handGif.gif"));
        double size = Constants.SCREEN_HEIGHT / 2;
        Constants.positionNode(hand, Constants.SCREEN_WIDTH / 3 , Constants.SCREEN_HEIGHT / 2);
        Constants.sizeNode(hand, size, size);
        pane.getChildren().add(hand);
        backgroundMusic.stop();
        AudioClip laugh = Constants.setSound("evillaugh", false);
        laugh.play();
        PauseTransition pause = new PauseTransition(Duration.millis(4000));
        pause.setOnFinished(e -> {
            if (mode.equals("load")) load();
            else if (mode.equals("socket")) ;
            else modeSelection();
        });
        pause.play();
    }

    private void load() {
        backgroundMusic.stop();
        GameState state = Constants.readState();
        new GameUI(stage, state);
    }

    private void modeSelection(){
        backgroundMusic.play();
        Pane pane = new Pane();
        pane.getChildren().addFirst(Constants.setBackGround("ModeSelection"));

        ImageView day = Constants.setButton("DayMode", Constants.SCREEN_WIDTH / 5, Constants.SCREEN_HEIGHT / 2);
        Constants.positionNode(day, Constants.SCREEN_WIDTH / 4, Constants.SCREEN_HEIGHT / 4);
        day.setOnMouseClicked(e -> startGame(GameMode.DAY));

        double sizePlant = Constants.SCREEN_HEIGHT / 6.3;
        ImageView plant = new ImageView(new Image("file:Pictures/plantPictures/SunFlower/gif.gif"));
        Constants.sizeNode(plant, sizePlant, sizePlant);
        Constants.positionNode(plant, Constants.SCREEN_WIDTH / 3.3, Constants.SCREEN_HEIGHT / 3.7);

        ImageView night = Constants.setButton("NightMode", Constants.SCREEN_WIDTH / 5, Constants.SCREEN_HEIGHT / 2);
        Constants.positionNode(night, Constants.SCREEN_WIDTH / 1.8, Constants.SCREEN_HEIGHT / 4);
        night.setOnMouseClicked(e -> startGame(GameMode.NIGHT));

        ImageView zombie = new ImageView(new Image("file:Pictures/ZombiePicture/OriginalZombie/gif.gif"));
        Constants.sizeNode(zombie, Constants.ZOMBIE_PIC_WIDTH, Constants.ZOMBIE_PIC_HEIGHT);
        Constants.positionNode(zombie, Constants.SCREEN_WIDTH / 1.68, Constants.SCREEN_HEIGHT / 3.9);

        pane.getChildren().addAll(plant, day, zombie, night);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
    }

    private void startGame(GameMode mode){
        GlobalState.playClickTrack();
        backgroundMusic.stop();
        new PlantSelection(stage, mode);
    }
}
