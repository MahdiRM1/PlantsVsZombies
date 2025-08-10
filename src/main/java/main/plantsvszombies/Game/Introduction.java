package main.plantsvszombies.Game;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.plantsvszombies.Enums.GameMode;
import main.plantsvszombies.Game.PlayModes.Client;
import main.plantsvszombies.Game.PlayModes.DefaultMode;
import main.plantsvszombies.Game.PlayModes.PlayMode;
import main.plantsvszombies.Game.PlayModes.Server;
import main.plantsvszombies.Game.Tools.Constants;
import main.plantsvszombies.Game.Tools.ImageFactory;
import main.plantsvszombies.Game.Tools.SoundManager;
import main.plantsvszombies.Game.Tools.Utils;
import main.plantsvszombies.GameState.GameState;

public class Introduction {

    private final Stage stage;
    private static final AudioClip backgroundMusic;

    static {
        backgroundMusic = SoundManager.setSound("introMusic", true);
    }

    public Introduction(Stage stage) {
        backgroundMusic.setVolume(SoundManager.music);
        backgroundMusic.play();
        this.stage = stage;
    }

    public void firstPage() {
        Scene scene = new Scene(MainMenuPane(), Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT - 35);
        stage.setScene(scene);
        stage.show();
    }

    private Pane MainMenuPane() {
        StackPane mainPane = new StackPane();
        Pane pane = new Pane();
        pane.getChildren().addFirst(ImageFactory.createBackGround("MainMenu"));

        ImageView adventure = ImageFactory.createButton("Adventure", Constants.SCREEN_WIDTH / 2.6, Constants.SCREEN_HEIGHT / 4.2);
        ImageFactory.setNodePosition(adventure, Constants.SCREEN_WIDTH / 1.97, Constants.SCREEN_HEIGHT / 8);
        adventure.setOnMouseClicked(event -> handAnimation(mainPane, "default"));

        ImageView socket = ImageFactory.createButton("Multiplayer", Constants.SCREEN_WIDTH / 2.7, Constants.SCREEN_HEIGHT / 4.35);
        ImageFactory.setNodePosition(socket, Constants.SCREEN_WIDTH / 1.98, Constants.SCREEN_HEIGHT / 3.1);
        socket.setOnMouseClicked(event -> handAnimation(mainPane, "socket"));

        ImageView loadGame = new ImageView(new Image("file:Pictures/ui/LoadGame.png"));
        ImageFactory.setNodeSize(loadGame, Constants.SCREEN_WIDTH / 3, Constants.SCREEN_HEIGHT / 4.8);
        ImageFactory.setNodePosition(loadGame, Constants.SCREEN_WIDTH / 1.94, Constants.SCREEN_HEIGHT / 2);
        if (Utils.dataExists()) {
            loadGame.setOnMouseEntered(event -> ImageFactory.changeScale(loadGame, 1.05));
            loadGame.setOnMouseExited(event -> ImageFactory.changeScale(loadGame, 1));
            loadGame.setOnMouseClicked(event -> {
                SoundManager.playClickTrack();
                handAnimation(mainPane, "load");
            });
        }else {
            loadGame.setEffect(Utils.effect(0, 0, -0.5, 0));
            loadGame.setOnMouseClicked(event -> SoundManager.playWrongClick());
        }

        ImageView quit = ImageFactory.createButton("Quit", Constants.SCREEN_WIDTH / 8.5, Constants.SCREEN_HEIGHT / 5.5);
        ImageFactory.setNodePosition(quit, Constants.SCREEN_WIDTH / 1.14, Constants.SCREEN_HEIGHT / 1.405);
        quit.setOnMouseClicked(event -> checkQuit(mainPane));

        ImageView help = ImageFactory.createButton("help", Constants.SCREEN_WIDTH / 8.4, Constants.SCREEN_HEIGHT / 4);
        ImageFactory.setNodePosition(help, Constants.SCREEN_WIDTH / 1.28, Constants.SCREEN_HEIGHT / 1.54);
        help.setOnMouseClicked(e -> helpPage(mainPane));

        ImageView options = ImageFactory.createButton("option", Constants.SCREEN_WIDTH / 6.55, Constants.SCREEN_HEIGHT / 5.4);
        ImageFactory.setNodePosition(options, Constants.SCREEN_WIDTH / 1.47, Constants.SCREEN_HEIGHT / 1.475);
        options.setOnMouseClicked(e -> option(mainPane));

        pane.getChildren().addAll(adventure, socket, loadGame, quit, options, help);
        mainPane.getChildren().add(pane);
        return mainPane;
    }

    private void helpPage(StackPane mainPane){
        Pane helpPage = new Pane();
        SoundManager.playClickTrack();
        
        ImageView backGround = ImageFactory.createBackGround("help");
        ImageView mainmenu = ImageFactory.createButton("mainmenuhelp", Constants.SCREEN_WIDTH / 6 , Constants.SCREEN_HEIGHT / 12);
        ImageFactory.setNodePosition(mainmenu, Constants.SCREEN_WIDTH / 2.4, Constants.SCREEN_HEIGHT / 1.25);
        mainmenu.setOnMouseClicked(e -> {
            SoundManager.playClickTrack();
            mainPane.getChildren().removeLast();
        });

        helpPage.getChildren().addAll(backGround, mainmenu);
        mainPane.getChildren().add(helpPage);
    }

    private void checkQuit(StackPane mainPane){
        Pane quitPage = new Pane();
        SoundManager.playClickTrack();

        ImageView quitImg = new ImageView(new Image("file:Pictures/ui/quitPic.png"));
        ImageFactory.setNodePosition(quitImg, Constants.SCREEN_WIDTH / 3.2, Constants.SCREEN_HEIGHT / 4);
        ImageFactory.setNodeSize(quitImg, Constants.SCREEN_WIDTH / 2.6, Constants.SCREEN_HEIGHT / 2);

        ImageView quit = ImageFactory.createButton("quitBtn", Constants.SCREEN_WIDTH / 6.7, Constants.SCREEN_HEIGHT / 13.5);
        ImageFactory.setNodePosition(quit, Constants.SCREEN_WIDTH / 2.9, Constants.SCREEN_HEIGHT / 1.58);
        quit.setOnMouseClicked(e -> {
            SoundManager.playClickTrack();
            stage.close();
        });

        ImageView cancel = ImageFactory.createButton("cancel", Constants.SCREEN_WIDTH / 6.7, Constants.SCREEN_HEIGHT / 13.5);
        ImageFactory.setNodePosition(cancel, Constants.SCREEN_WIDTH / 1.95, Constants.SCREEN_HEIGHT / 1.58);
        cancel.setOnMouseClicked(e -> {
            SoundManager.playClickTrack();
            mainPane.getChildren().removeLast();
        });

        quitPage.getChildren().addAll(quitImg, quit, cancel);
        mainPane.getChildren().add(quitPage);
    }

    private void option(StackPane pane){
        SoundManager.playClickTrack();
        Pane option = Utils.createMenu();

        ImageView OK = ImageFactory.createButton("OK", Constants.SCREEN_WIDTH / 3, Constants.SCREEN_HEIGHT / 7.5);
        ImageFactory.setNodePosition(OK, Constants.SCREEN_WIDTH / 2.97, Constants.SCREEN_HEIGHT / 1.34);
        OK.setOnMouseClicked(e -> {
            SoundManager.playClickTrack();
            pane.getChildren().removeLast();
            if (SoundManager.music != backgroundMusic.getVolume()){
                backgroundMusic.stop();
                backgroundMusic.setVolume(SoundManager.music);
                backgroundMusic.play();
            }
        });

        option.getChildren().add(OK);
        pane.getChildren().add(option);
    }

    private void handAnimation(StackPane mainPane, String mode){
        ImageView hand = new ImageView(new Image("file:Pictures/ui/handGif.gif"));
        double size = Constants.SCREEN_HEIGHT / 2;
        ImageFactory.setNodePosition(hand, Constants.SCREEN_WIDTH / 3 , Constants.SCREEN_HEIGHT / 2);
        ImageFactory.setNodeSize(hand, size, size);
        mainPane.getChildren().add(hand);
        backgroundMusic.stop();
        AudioClip laugh = SoundManager.setSound("evillaugh", false);
        laugh.play();
        PauseTransition pause = new PauseTransition(Duration.millis(4000));
        pause.setOnFinished(e -> {
            if (mode.equals("load")) load();
            else if (mode.equals("socket")) multiPlayerMode(mainPane);
            else modeSelection();
        });
        pause.play();
    }

    private void multiPlayerMode(StackPane mainPane){
        Pane pane = new Pane();
        HBox box = socketBox();

        Button btn1 = socketButtons("Client");
        btn1.setOnAction(e -> {
            PlayMode playMode = new Client();
            startGame(GameMode.DAY, playMode);
        });

        Button btn2 = socketButtons("Server");
        btn2.setOnMouseClicked(e -> {
            Server server = new Server();
            Thread thread = new Thread(server);
            thread.start();
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            PlayMode playMode = new Client();
            startGame(GameMode.DAY, playMode);
        });

        box.getChildren().addAll(btn1, btn2);

        pane.getChildren().addAll(box);
        mainPane.getChildren().add(pane);
    }

    private HBox socketBox(){
        HBox box = new HBox(Constants.SCREEN_WIDTH / 20);
        box.setPrefSize(Constants.SCREEN_WIDTH/3, Constants.SCREEN_HEIGHT/2);
        box.setAlignment(Pos.CENTER);
        box.setStyle(
            "-fx-background-color:rgb(150, 150, 0); " +
            "-fx-background-radius: 15px; " +
            "-fx-padding: 20px; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 0);"
        );
        box.setLayoutX(Constants.SCREEN_WIDTH / 3);
        box.setLayoutY(Constants.SCREEN_HEIGHT / 4);
        return box;
    }

    private Button socketButtons(String str){
        Button btn = new Button(str);
        btn.setPrefSize(Constants.SCREEN_WIDTH / 8, Constants.SCREEN_WIDTH / 8);
        btn.setStyle(
            "-fx-background-color:rgb(50, 50, 50); " +
            "-fx-background-radius: 10px; " +
            "-fx-text-fill: rgb(0, 150, 0);" +
            "-fx-font-size: 25px;"+
            "-fx-padding: 20px; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 0);"
        );
        btn.setOnMouseEntered(e -> ImageFactory.changeScale(btn, 1.1));
        btn.setOnMouseExited(e -> ImageFactory.changeScale(btn, 1));
        return btn;
    }

    private void load() {
        backgroundMusic.stop();
        GameState state = Utils.readState();
        new GameUI(stage, state);
    }

    private void modeSelection(){
        backgroundMusic.play();
        Pane pane = new Pane();
        pane.getChildren().addFirst(ImageFactory.createBackGround("ModeSelection"));

        ImageView day = ImageFactory.createButton("DayMode", Constants.SCREEN_WIDTH / 5, Constants.SCREEN_HEIGHT / 2);
        ImageFactory.setNodePosition(day, Constants.SCREEN_WIDTH / 4, Constants.SCREEN_HEIGHT / 4);
        day.setOnMouseClicked(e -> startGame(GameMode.DAY, new DefaultMode()));

        double sizePlant = Constants.SCREEN_HEIGHT / 6.3;
        ImageView plant = new ImageView(new Image("file:Pictures/plantPictures/SunFlower/gif.gif"));
        ImageFactory.setNodeSize(plant, sizePlant, sizePlant);
        ImageFactory.setNodePosition(plant, Constants.SCREEN_WIDTH / 3.3, Constants.SCREEN_HEIGHT / 3.7);

        ImageView night = ImageFactory.createButton("NightMode", Constants.SCREEN_WIDTH / 5, Constants.SCREEN_HEIGHT / 2);
        ImageFactory.setNodePosition(night, Constants.SCREEN_WIDTH / 1.8, Constants.SCREEN_HEIGHT / 4);
        night.setOnMouseClicked(e -> startGame(GameMode.NIGHT, new DefaultMode()));

        ImageView zombie = new ImageView(new Image("file:Pictures/ZombiePicture/OriginalZombie/gif.gif"));
        ImageFactory.setNodeSize(zombie, Constants.ZOMBIE_PIC_WIDTH, Constants.ZOMBIE_PIC_HEIGHT);
        ImageFactory.setNodePosition(zombie, Constants.SCREEN_WIDTH / 1.68, Constants.SCREEN_HEIGHT / 3.9);

        pane.getChildren().addAll(plant, day, zombie, night);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
    }

    private void startGame(GameMode mode, PlayMode playMode){
        SoundManager.playClickTrack();
        backgroundMusic.stop();
        new PlantSelection(stage, mode, playMode);
    }
}
