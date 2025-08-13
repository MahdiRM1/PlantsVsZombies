package main.plantsvszombies.Game;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.plantsvszombies.Enums.GameMode;
import main.plantsvszombies.Game.PlayModes.Client;
import main.plantsvszombies.Game.PlayModes.DefaultMode;
import main.plantsvszombies.Game.PlayModes.PlayMode;
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
        scene.getStylesheets().add("file:src/main/resources/styles/ui.css");
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
        socket.setOnMouseClicked(event -> multiPlayerMode(mainPane));

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
        Button mainmenu = Utils.createButton("main menu", Constants.SCREEN_WIDTH / 6 , Constants.SCREEN_HEIGHT / 12);
        ImageFactory.setNodePosition(mainmenu, Constants.SCREEN_WIDTH / 2.4, Constants.SCREEN_HEIGHT / 1.25);
        mainmenu.setOnMouseClicked(e -> {
            SoundManager.playClickTrack();
            mainPane.getChildren().removeLast();
        });

        helpPage.getChildren().addAll(backGround, mainmenu);
        mainPane.getChildren().add(helpPage);
    }

    private void checkQuit(StackPane mainPane){
        SoundManager.playClickTrack();

        Button quit = Utils.createMenuButton("QUIT", Constants.SCREEN_WIDTH / 9, Constants.SCREEN_HEIGHT / 15);
        quit.setOnMouseClicked(e -> {
            SoundManager.playClickTrack();
            stage.close();
        });

        Button cancel = Utils.createMenuButton("CANCEL", Constants.SCREEN_WIDTH / 9, Constants.SCREEN_HEIGHT / 15);
        cancel.setOnMouseClicked(e -> {
            SoundManager.playClickTrack();
            mainPane.getChildren().removeLast();
        });

        String phrase = "Wait\n\nAre You Sure You Wish To\nQuit The Game?";
        chooseRole(mainPane, quit, cancel, phrase);
    }

    private void option(StackPane pane){
        SoundManager.playClickTrack();
        Pane option = Utils.createMenu();

        Button OK = Utils.submitButton("OK");
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
        Pane pane = new Pane();
        double size = Constants.SCREEN_HEIGHT / 2;
        ImageFactory.setNodePosition(hand, Constants.SCREEN_WIDTH / 3 , Constants.SCREEN_HEIGHT / 2);
        ImageFactory.setNodeSize(hand, size, size);
        pane.getChildren().add(hand);
        backgroundMusic.stop();
        AudioClip laugh = SoundManager.setSound("evillaugh", false);
        laugh.play();
        PauseTransition pause = new PauseTransition(Duration.millis(4000));
        pause.setOnFinished(e -> {
            switch (mode) {
                case "load" -> load();
                case "socket" -> multiPlayerMode(mainPane);
                default ->  modeSelection();
            }
        });
        pause.play();
        mainPane.getChildren().add(pane);
    }

    private void multiPlayerMode(StackPane mainPane){
        Button btn1 = Utils.createMenuButton("CLIENT", Constants.SCREEN_WIDTH/9, Constants.SCREEN_HEIGHT/15);
        btn1.setOnAction(e -> {
            PlayMode playMode = new Client("192.168.242.30");
            startGame(GameMode.DAY, playMode);
        });

        Button btn2 = Utils.createMenuButton("SERVER", Constants.SCREEN_WIDTH / 9, Constants.SCREEN_HEIGHT / 15);
        btn2.setOnMouseClicked(e -> {
//            MultiServer multiServer = new MultiServer();
//            Runnable runnable = multiServer::connect;
//            Thread thread1 = new Thread(runnable);
//            thread1.start();
//            PlayMode playMode = multiServer.innerConnection();
//            Thread thread = new Thread(multiServer);
//            thread.setPriority(10);
//            thread.start();
//            startGame(GameMode.DAY, playMode);
        });

        String phrase = "MultiPlayer\n\nChoose your role in \nthe game";
        chooseRole(mainPane, btn1, btn2, phrase);
    }

    private void chooseRole(StackPane mainPane, Button btn1, Button btn2, String str){
        Pane pane = new Pane();
        chooseBox(pane, str);

        ImageFactory.setNodePosition(btn1, Constants.SCREEN_WIDTH/2.7, Constants.SCREEN_HEIGHT/1.65);
        ImageFactory.setNodePosition(btn2, Constants.SCREEN_WIDTH/1.95, Constants.SCREEN_HEIGHT/1.65);

        pane.getChildren().addAll(btn1, btn2);
        mainPane.getChildren().add(pane);
    }

    private void chooseBox(Pane pane, String text){
        ImageView chooseRole = new ImageView(new Image("file:Pictures/ui/dialog_topleft.png"));
        ImageFactory.setNodePosition(chooseRole, Constants.SCREEN_WIDTH/3, Constants.SCREEN_HEIGHT/4);
        ImageFactory.setNodeSize(chooseRole, Constants.SCREEN_WIDTH/3, Constants.SCREEN_HEIGHT/2);
        Label label = new Label(text);
        ImageFactory.setNodePosition(label, Constants.SCREEN_WIDTH/2.5, Constants.SCREEN_HEIGHT / 2.5);
        ImageFactory.setNodeSize(label, Constants.SCREEN_WIDTH/5.25, Constants.SCREEN_HEIGHT/8);
        Font font = Font.loadFont("file:src/main/resources/fonts/BreakdownPG.otf", 30);
        label.setFont(font);
        label.setStyle(
                "-fx-text-fill: rgb(214, 178, 94);" +
                "-fx-effect: dropshadow(one-pass-box, black, 5, 1, 0, 0);"
        );
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(chooseRole, label);
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
