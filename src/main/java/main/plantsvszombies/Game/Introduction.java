package main.plantsvszombies.Game;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.plantsvszombies.Enums.GameMode;
import main.plantsvszombies.Game.PlayModes.Client;
import main.plantsvszombies.Game.PlayModes.DefaultMode;
import main.plantsvszombies.Game.PlayModes.MultiServer;
import main.plantsvszombies.Game.PlayModes.PlayMode;
import main.plantsvszombies.Game.Tools.Constants;
import main.plantsvszombies.Game.Tools.ImageFactory;
import main.plantsvszombies.Game.Tools.SoundManager;
import main.plantsvszombies.Game.Tools.Utils;
import main.plantsvszombies.GameState.GameState;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class Introduction {

    private final Stage stage;
    private static final MediaPlayer backgroundMusic;
    private GameMode mode;

    static {
        backgroundMusic = new MediaPlayer(new Media(Introduction.class.getResource("/Audio/introMusic.mp3").toExternalForm()));
    }

    public Introduction(Stage stage) {
        backgroundMusic.setVolume(SoundManager.music);
        backgroundMusic.play();
        this.stage = stage;
    }

    public void firstPage() {
        Scene scene = new Scene(MainMenuPane(), Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/styles/ui.css").toExternalForm());
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

        ImageView loadGame = new ImageView(new Image(getClass().getResource("/Pictures/ui/LoadGame.png").toExternalForm()));
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

        ImageView quit = ImageFactory.createButton("Quit", Constants.SCREEN_WIDTH / 8.8, Constants.SCREEN_HEIGHT / 6);
        ImageFactory.setNodePosition(quit, Constants.SCREEN_WIDTH / 1.14, Constants.SCREEN_HEIGHT / 1.35);
        quit.setOnMouseClicked(event -> checkQuit(mainPane));

        ImageView help = ImageFactory.createButton("help", Constants.SCREEN_WIDTH / 8.8, Constants.SCREEN_HEIGHT / 4.4);
        ImageFactory.setNodePosition(help, Constants.SCREEN_WIDTH / 1.265, Constants.SCREEN_HEIGHT / 1.43);
        help.setOnMouseClicked(e -> helpPage(mainPane));

        ImageView options = ImageFactory.createButton("option", Constants.SCREEN_WIDTH / 8, Constants.SCREEN_HEIGHT / 6);
        ImageFactory.setNodePosition(options, Constants.SCREEN_WIDTH / 1.44, Constants.SCREEN_HEIGHT / 1.4);
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

        String phrase = "Wait\nAre You Sure You Wish To\nQuit The Game?";
        chooseRole(mainPane, cancel, quit, phrase);
    }

    private void option(StackPane pane){
        SoundManager.playClickTrack();
        Pane option = Utils.createMenu(stage);

        Button OK = Utils.submitButton("OK");
        OK.setOnMouseClicked(e -> {
            SoundManager.playClickTrack();
            pane.getChildren().removeLast();
            backgroundMusic.setVolume(SoundManager.music);
        });

        option.getChildren().add(OK);
        pane.getChildren().add(option);
    }

    private void handAnimation(StackPane mainPane, String mode){
        ImageView hand = new ImageView(new Image(getClass().getResource("/Pictures/ui/handGif.gif").toExternalForm()));
        Pane pane = new Pane();
        double size = Constants.SCREEN_HEIGHT / 2;
        ImageFactory.setNodePosition(hand, Constants.SCREEN_WIDTH / 3 , Constants.SCREEN_HEIGHT / 2);
        ImageFactory.setNodeSize(hand, size, size);
        pane.getChildren().add(hand);
        backgroundMusic.stop();
        SoundManager.playSound("evillaugh");
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
        Button client = Utils.createMenuButton("JOIN", Constants.SCREEN_WIDTH/9, Constants.SCREEN_HEIGHT/15);
        client.setOnAction(e -> clientMode(mainPane));

        Button server = Utils.createMenuButton("CREATE", Constants.SCREEN_WIDTH / 9, Constants.SCREEN_HEIGHT / 15);
        server.setOnMouseClicked(e -> serverModeSelection(mainPane));

        String phrase = "MultiPlayer\n\nChoose an option";
        chooseRole(mainPane, client, server, phrase);
    }

    private void serverModeSelection(StackPane mainPane){
        mainPane.getChildren().removeLast();
        Button day = Utils.createMenuButton("DAY", Constants.SCREEN_WIDTH/9, Constants.SCREEN_HEIGHT/15);
        day.setOnMouseClicked(e -> {
            mode = GameMode.DAY;
            serverMode(mainPane);
        });

        Button night = Utils.createMenuButton("NIGHT", Constants.SCREEN_WIDTH/9, Constants.SCREEN_HEIGHT/15);
        night.setOnMouseClicked(e -> {
            mode = GameMode.NIGHT;
            serverMode(mainPane);
        });

        String phrase = "mode selection\n select game mode";
        chooseRole(mainPane, night, day, phrase);
    }

    private void serverMode(StackPane mainPane){
        MultiServer multiServer = new MultiServer(mode);
        Runnable runnable = multiServer::connect;
        Thread thread = new Thread(runnable);
        thread.start();
        serverModeUI(mainPane, multiServer, thread);
    }

    private void serverModeUI(StackPane mainPane, MultiServer multiServer, Thread thread){
        mainPane.getChildren().removeLast();
        Button accept = Utils.createMenuButton("ACCEPT", Constants.SCREEN_WIDTH/9, Constants.SCREEN_HEIGHT/15);
        accept.setOnMouseClicked(e -> {
            if (!multiServer.getServers().isEmpty()) serverStart(multiServer);
        });
        accept.setOnMouseEntered(e -> {
            if (!multiServer.getServers().isEmpty()) ImageFactory.changeScale(accept, 1.1);
        });

        Button cancel = Utils.createMenuButton("CANCEL", Constants.SCREEN_WIDTH/9, Constants.SCREEN_HEIGHT/15);
        cancel.setOnMouseClicked(e -> {
            thread.interrupt();
            multiServer.cleanup();
            mainPane.getChildren().removeLast();
        });

        String phrase = "Enter the IP in the\n client textfield.\n" + Ip();
        chooseRole(mainPane, cancel, accept, phrase);
    }

    private void serverStart(MultiServer multiServer){
        Client client = new Client("127.0.0.1");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("line 238 introduction: " + e.getMessage());
            throw new RuntimeException();
        }
        Thread thread = new Thread(multiServer);
        thread.start();
        startGame(client);
    }

    private void clientMode(StackPane mainPane){
        mainPane.getChildren().removeLast();

        Button cancel = Utils.createMenuButton("CANCEL", Constants.SCREEN_WIDTH/9, Constants.SCREEN_HEIGHT/15);
        cancel.setOnAction(e -> mainPane.getChildren().removeLast());

        TextField textField = field();

        Button submit = Utils.createMenuButton("SUBMIT", Constants.SCREEN_WIDTH/9, Constants.SCREEN_HEIGHT/15);
        submit.setOnAction(e -> {
            Client client = new Client(textField.getText());
            mode = client.getGameMode();
            startGame(client);
        });

        String phrase = "Please get IP\n\n.";
        chooseRole(mainPane, cancel, submit, phrase);

        ((Pane)(mainPane.getChildren().getLast())).getChildren().add(textField);
    }

    private TextField field(){
        TextField textField = new TextField();
        textField.setPromptText("Enter IP");
        textField.getStyleClass().add("TextField");
        Font font = Font.loadFont(getClass().getResource("/fonts/Coold.ttf").toExternalForm(), 30);
        textField.setFont(font);
        textField.setPrefSize(Constants.SCREEN_WIDTH/4, Constants.SCREEN_HEIGHT/15);
        ImageFactory.setNodePosition(textField, Constants.SCREEN_WIDTH/2.7, Constants.SCREEN_HEIGHT/2.1);
        return textField;
    }

    private void chooseRole(StackPane mainPane, Node btn1, Node btn2, String str){
        Pane pane = new Pane();
        chooseBox(pane, str);

        ImageFactory.setNodePosition(btn1, Constants.SCREEN_WIDTH/2.7, Constants.SCREEN_HEIGHT/1.65);
        ImageFactory.setNodePosition(btn2, Constants.SCREEN_WIDTH/1.95, Constants.SCREEN_HEIGHT/1.65);

        pane.getChildren().addAll(btn1, btn2);
        mainPane.getChildren().add(pane);
    }

    private String Ip(){
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            while (nets.hasMoreElements()) {
                NetworkInterface nif = nets.nextElement();
                if (nif.isUp() && !nif.isLoopback() && !nif.isVirtual()) {
                    Enumeration<InetAddress> addresses = nif.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        InetAddress addr = addresses.nextElement();
                        if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) return addr.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("line 301 introduction: " + e.getMessage());
            throw new RuntimeException();
        }
        return null;
    }

    private void chooseBox(Pane pane, String text){
        ImageView chooseRole = new ImageView(new Image(getClass().getResource("/Pictures/ui/dialog_topleft.png").toExternalForm()));
        ImageFactory.setNodePosition(chooseRole, Constants.SCREEN_WIDTH/3, Constants.SCREEN_HEIGHT/4);
        ImageFactory.setNodeSize(chooseRole, Constants.SCREEN_WIDTH/3, Constants.SCREEN_HEIGHT/2);
        Label label = new Label(text);
        ImageFactory.setNodePosition(label, Constants.SCREEN_WIDTH/2.5, Constants.SCREEN_HEIGHT / 2.5);
        ImageFactory.setNodeSize(label, Constants.SCREEN_WIDTH/5.25, Constants.SCREEN_HEIGHT/8);
        Font font = Font.loadFont(getClass().getResource("/fonts/BreakdownPG.otf").toExternalForm(), 25);
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
        day.setOnMouseClicked(e -> {
            mode = GameMode.DAY;
            startGame(new DefaultMode());
        });

        double sizePlant = Constants.SCREEN_HEIGHT / 8;
        ImageView plant = new ImageView(new Image(getClass().getResource("/Pictures/plantPictures/SunFlower/gif.gif").toExternalForm()));
        ImageFactory.setNodeSize(plant, sizePlant, sizePlant);
        ImageFactory.setNodePosition(plant, Constants.SCREEN_WIDTH / 3.2, Constants.SCREEN_HEIGHT / 3.6);

        ImageView night = ImageFactory.createButton("NightMode", Constants.SCREEN_WIDTH / 5, Constants.SCREEN_HEIGHT / 2);
        ImageFactory.setNodePosition(night, Constants.SCREEN_WIDTH / 1.8, Constants.SCREEN_HEIGHT / 4);
        night.setOnMouseClicked(e -> {
            mode = GameMode.NIGHT;
            startGame(new DefaultMode());
        });

        ImageView zombie = new ImageView(new Image(getClass().getResource("/Pictures/ZombiePicture/OriginalZombie/gif.gif").toExternalForm()));
        ImageFactory.setNodeSize(zombie, Constants.ZOMBIE_PIC_WIDTH, Constants.ZOMBIE_PIC_HEIGHT);
        ImageFactory.setNodePosition(zombie, Constants.SCREEN_WIDTH / 1.68, Constants.SCREEN_HEIGHT / 3.9);

        pane.getChildren().addAll(plant, day, zombie, night);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
    }

    private void startGame(PlayMode playMode){
        SoundManager.playClickTrack();
        backgroundMusic.stop();
        new PlantSelection(stage, mode, playMode);
    }
}
