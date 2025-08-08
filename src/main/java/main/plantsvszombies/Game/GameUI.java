package main.plantsvszombies.Game;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.plantsvszombies.Enums.*;
import main.plantsvszombies.Game.PlayModes.*;
import main.plantsvszombies.GameState.*;
import main.plantsvszombies.Items.*;
import main.plantsvszombies.Plants.*;
import main.plantsvszombies.Plants.BombPlants.*;
import main.plantsvszombies.Plants.OtherPlants.*;
import main.plantsvszombies.Plants.PeaPlants.*;
import main.plantsvszombies.Zombies.*;

public class GameUI {

    private final GameLogic gameLogic;
    private final StackPane mainPane = new StackPane();
    private final BorderPane borderPane = new BorderPane();
    private final List<Card> cards = new ArrayList<>();
    private final Pane pane = new Pane();
    private final Stage stage;
    private final GameMode mode;
    private final PlayMode playMode;
    private final GameTimer timer;
    private ScoreBoard scoreBoard;
    private Timeline tl;
    private Scene scene;
    public static int selectedButton = -1;
    private Fog fog;
    private AudioClip backgroundMusic;

    // constructor: to load the previously saved game
    public GameUI(Stage stage, GameState state) {
        this.stage = stage;
        gameLogic = new GameLogic(state);
        this.mode = state.getMode();
        timer = new GameTimer();
        for (CardData data : state.getCards()) cards.add(new Card(data));
        initStackPane(cardBar(), state.getScore(), state.getTime());
        if (mode == GameMode.NIGHT) initFog(state.getFogLength());
        playMode = new DefaultMode(pane, gameLogic.getZombies(), gameLogic.getGraves());
        loadPlants();
        loadZombies();
        startGame();
    }

    // constructor: to start a new game
    public GameUI(Stage stage, GameMode gameMode) {
        this.stage = stage;
        this.mode = gameMode;
        timer = new GameTimer();
        gameLogic = new GameLogic(this.mode);
        playMode = new DefaultMode(pane, gameLogic.getZombies(), gameLogic.getGraves());
        new PlantSelection(stage, this , gameMode);
    }

    public void start(List<String> plantsName) {
        for (int i = 0; i < plantsName.size(); i++) {
            cards.add(new Card(plantsName.get(i), i));
        }
//        initStackPane(cardBar(), mode == GameMode.DAY ? 50 : 100, 0);
        initStackPane(cardBar(), 1000, 69000);
        if (mode == GameMode.NIGHT) {
            initFog((int) (Math.random() * 3) + 5);
        }
        startGame();
    }

    // manages the start of the game
    public final void startGame() {
        backgroundMusic = Constants.setSound("gameMusic", true);
        backgroundMusic.setVolume(GlobalState.music);
        backgroundMusic.play();
        tl = new Timeline(new KeyFrame(Duration.millis(20), event -> {
            GlobalState.gameTime += 20;
            updateGame();
        }));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
        scene = new Scene(mainPane, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT - 35);
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> save());
        stage.show();
    }

    // initializes the stack pane
    private void initStackPane(HBox cardBar, int score, long time) {
        borderPane.getChildren().add(Constants.setBackGround(
                (mode == GameMode.DAY) ? "backGroundDay" : "backGroundNight"));
        borderPane.setTop(cardBar);
        borderPane.setBottom(map());
        pane.setMouseTransparent(true);
        scoreBoard = new ScoreBoard(borderPane, score, mode);
        for (Grave grave : gameLogic.getGraves()) borderPane.getChildren().add(grave.getPicture());
        GlobalState.gameTime = time;
        mainPane.getChildren().addAll(borderPane, pane, buttonsPane());
    }

    // initialized the fog
    private void initFog(int fogLength) {
        Pane fogGrid = new Pane();
        fogGrid.setMouseTransparent(true);
        fog = new Fog(fogGrid, fogLength);
        mainPane.getChildren().add(2, fogGrid);
        mode.setFogLength(fogLength);
    }

    // generate card bar
    private HBox cardBar() {
        HBox cardBar = new HBox(0);
        for (Card card : cards) cardBar.getChildren().add(card.getBtn());
        cardBar.setPadding(new Insets(Constants.CARD_BAR_Y, 0, 0, Constants.CARD_BAR_X));
        return cardBar;
    }

    // loads the potted plants
    private void loadPlants() {
        for (Plant plant : gameLogic.getPottedPlants()) borderPane.getChildren().add(plant.getPicture());
    }

    // loads the zombies
    private void loadZombies() {
        for (Zombie z : gameLogic.getZombies()) pane.getChildren().add(z.getPicture());
    }

    // generate game map
    private GridPane map() {
        GridPane grid = new GridPane();
        for (int row = 0; row < Constants.ROWS; row++)
            for (int col = 0; col < Constants.COLS; col++)
                grid.add(mapButtons(row, col), col, row);

        grid.setPadding(new Insets(0, 0, Constants.SCREEN_WIDTH / 30, Constants.SCREEN_HEIGHT / 2.6));
        return grid;
    }

    // generate mapButtons and control planting visuals
    private Button mapButtons(int row, int col) {
        Button btn = new Button();
        Constants.sizeNode(btn, Constants.TILE_SIZE, Constants.TILE_SIZE);
        btn.setStyle("-fx-background-color: transparent");

        btn.setOnAction(event -> handleMapClick(row, col, btn));
        btn.setOnMouseEntered(event -> btn.setStyle("-fx-background-color: rgba(140, 140, 140, 0.3);"));
        btn.setOnMouseExited(event -> btn.setStyle("-fx-background-color: transparent;"));
        return btn;
    }

    // handles the map click
    private void handleMapClick(int row, int col, Button btn) {
        if (selectedButton < 0) btn.setOnMouseClicked(event -> wrongClick(btn));
        else if (selectedButton < cards.size()) handlePlanting(row, col, btn);
        else useShovel(row, col, btn);
    }

    private void wrongClick(Button btn){
        btn.setStyle("-fx-background-color: rgba(245, 50, 50, 0.6);");
        GlobalState.playWrongClick();
    }

    private void correctClick(Button btn){
        btn.setStyle("-fx-background-color: rgba(174, 255, 174, 0.7);");
        GlobalState.playCorrectClick();
    }

    // handles the planting
    private void handlePlanting(int row, int col, Button btn) {
        Plant plant = getPlant(row, col);
        boolean placed = false;

        if (plant instanceof CoffeeBean || plant instanceof GraveBuster)
            if (scoreBoard.purchasePlant(plant.getPrice())) placed = true;

        if (plant != null && gameLogic.isPlantable(row, col) && scoreBoard.purchasePlant(plant.getPrice())) placed = true;
        else btn.setOnMouseClicked(event -> wrongClick(btn));

        if (placed) {
            gameLogic.setPlant(plant);
            cards.get(selectedButton).updateLastSelected();
            borderPane.getChildren().add(plant.getPicture());
            btn.setOnMouseClicked(event -> correctClick(btn));
        }

        cards.get(selectedButton).getBtn().setStyle("-fx-background-color: transparent;");
        selectedButton = -1;
    }

    // manage shovel visuals
    private void useShovel(int row, int col, Button btn) {
        Plant plant = gameLogic.getPlant(row, col);
        if (!gameLogic.isPlantable(row, col) && !(plant instanceof DoomShroom ds && !ds.isSleep()))
            gameLogic.removePlant(row, col, borderPane);
        else btn.setOnMouseClicked(event -> wrongClick(btn));

        Pane buttons = (Pane) mainPane.getChildren().getLast();
        ImageView shovelBack = ((ImageView) buttons.getChildren().getLast());
        shovelBack.setEffect(null);
        buttons.getChildren().add(shovelImage());
        scene.setCursor(Cursor.DEFAULT);
        selectedButton = -1;
    }

    // control menu buttons
    private Pane buttonsPane() {
        Pane buttonsPane = new Pane();
        buttonsPane.setPickOnBounds(false);

        ImageView menu = Constants.setButton("MenuBtn", Constants.SCREEN_WIDTH / 9.4, Constants.SCREEN_HEIGHT / 16);
        menu.setOnMouseClicked(event -> {
            GlobalState.playClickTrack();
            tl.pause();
            menu();
        });
        Constants.positionNode(menu, Constants.SCREEN_WIDTH - menu.getFitWidth(), 0);

        ImageView shovel = shovelImage();
        ImageView shovelBack = Constants.setButton("shovelBack", shovel.getFitWidth(), shovel.getFitHeight());
        Constants.positionNode(shovelBack, shovel.getLayoutX(), 0);

        Cursor cursor = new ImageCursor(shovel.getImage());
        shovelBack.setOnMouseClicked(event -> {
            GlobalState.playClickTrack();
            shovelButtonClick(shovel, shovelBack, cursor);
            GlobalState.playShovelClick();
        });

        buttonsPane.getChildren().addAll(menu, shovelBack, shovel);
        buttonsPane.getChildren().addAll(timer.get());
        return buttonsPane;
    }

    // handles the shovel button click
    private void shovelButtonClick(ImageView shovel, ImageView shovelBack, Cursor cursor) {
        Pane buttonsPane = (Pane) (mainPane.getChildren().getLast());
        if (selectedButton != cards.size()) {
            scene.setCursor(cursor);
            selectedButton = cards.size();
            buttonsPane.getChildren().removeLast();
            ColorAdjust choose = new ColorAdjust();
            choose.setBrightness(-0.5);
            shovelBack.setEffect(choose);
        } else {
            scene.setCursor(Cursor.DEFAULT);
            selectedButton = -1;
            buttonsPane.getChildren().add(shovel);
            shovelBack.setEffect(null);
        }
    }

    // add shovel image
    private ImageView shovelImage() {
        ImageView shovel = Constants.setButton("shovel", Constants.SCREEN_WIDTH / 19, Constants.SCREEN_HEIGHT / 10);
        shovel.setMouseTransparent(true);
        Constants.positionNode(shovel, Constants.SCREEN_WIDTH / 2.1, 0);
        return shovel;
    }

    // generate the menu pane
    private void menu(){
        AudioClip pause = Constants.setSound("pause", false);
        pause.play();

        Pane menuPane = new Pane();
        menuPane.setStyle("-fx-background-color: rgba(56, 56, 56, 0.7);");

        ImageView optionImg = new ImageView(new Image("file:Pictures/ui/optionPic.png"));
        Constants.positionNode(optionImg, Constants.SCREEN_WIDTH / 3.3, Constants.SCREEN_HEIGHT / 10);
        Constants.sizeNode(optionImg, Constants.SCREEN_WIDTH / 2.5, Constants.SCREEN_HEIGHT / 1.25);

        ImageView mainMenu = mainMenuBtn("menu");
        Constants.positionNode(mainMenu, Constants.SCREEN_WIDTH / 2.48, Constants.SCREEN_HEIGHT / 1.6);

        ImageView restart = restartBtn();
        Constants.positionNode(restart, Constants.SCREEN_WIDTH / 2.48, Constants.SCREEN_HEIGHT / 1.84);


        ImageView backToGame = Constants.setButton("BackToGame", Constants.SCREEN_WIDTH / 3, Constants.SCREEN_HEIGHT / 7.5);
        Constants.positionNode(backToGame, Constants.SCREEN_WIDTH / 2.97, Constants.SCREEN_HEIGHT / 1.34);
        backToGame.setOnMouseClicked(e -> {
            GlobalState.playClickTrack();
            mainPane.getChildren().removeLast();
            if (GlobalState.music != backgroundMusic.getVolume()){
                backgroundMusic.stop();
                backgroundMusic.setVolume(GlobalState.music);
                backgroundMusic.play();
            }
            tl.play();
        });

        Slider music = new Slider(0, 1, GlobalState.music);
        Constants.setSlider(music);
        music.setLayoutY(Constants.SCREEN_HEIGHT / 3);
        music.valueProperty().addListener((obs, oldVal, newVal) -> GlobalState.music = newVal.doubleValue());
        Label musicLabel = Constants.setSliderLabel("Music", music);

        Slider volume = new Slider(0, 1, GlobalState.volume);
        Constants.setSlider(volume);
        volume.setLayoutY(Constants.SCREEN_HEIGHT / 3 + Constants.SCREEN_HEIGHT / 10);
        volume.valueProperty().addListener((obs, oldVal, newVal) -> GlobalState.volume = newVal.doubleValue());
        Label volumeLabel = Constants.setSliderLabel("Volume", volume);

        menuPane.getChildren().addAll(optionImg, backToGame, mainMenu, restart, music, musicLabel, volume, volumeLabel);
        mainPane.getChildren().add(menuPane);
    }

    // get the plant from the selected button
    private Plant getPlant(int row, int col) {
        if (selectedButton < 0 || selectedButton >= cards.size()) return null;

        String plantName = cards.get(selectedButton).getPlantName();
        return switch (plantName){
            case "CoffeeBean" -> getCoffeeBean(row, col);
            case "GraveBuster" -> getGraveBuster(row, col);
            default -> Constants.getPlant(row, col, plantName, mode == GameMode.DAY);
        };
    }

    // get the coffee bean from the selected button
    private CoffeeBean getCoffeeBean(int row, int col) {
        if (gameLogic.getPlant(row, col) instanceof Shroom shroom && shroom.isSleep())
            return new CoffeeBean(row, col, shroom);
        else return null;
    }

    // get the grave buster from the selected button
    private GraveBuster getGraveBuster(int row, int col) {
        for (Grave grave : gameLogic.getGraves()) {
            if (grave.getRow() == row && grave.getCol() == col) return new GraveBuster(row, col, grave);
        }
        return null;
    }

    // updates the game
    public void updateGame() {
        winOrLose();
        cleanUpImages();
        updateRecharges();
        logicUpdates();
        plantActions();
        timer.update();
        playMode.updateGame();
    }

    // removes garbage images of struck bullets,dead zombies and eaten plants
    private void cleanUpImages() {
        for (Plant plantToRemove : gameLogic.plantsToRemove()) borderPane.getChildren().remove(plantToRemove.getPicture());
        for (Bullet bullet : gameLogic.checkBulletStrike()) pane.getChildren().remove(bullet.getPicture());
        for (Zombie zombie : gameLogic.zombieToRemove()) pane.getChildren().removeAll(zombie.getPicture(), zombie.getSecondPicture());
    }

    // updates the game logic
    private void logicUpdates() {
        gameLogic.updateGame();
        scoreBoard.handleSuns();
        if(mode == GameMode.NIGHT) fog.updateFog();
    }

    // updates the plant actions
    private void plantActions() {
        List<Plant> actions = gameLogic.updatePlantActions();
        for (Plant plant : actions) {
            switch (plant) {
                case PeaPlant peaPlant -> gameLogic.addBullet(peaPlant.action(), pane);
                case SunFlower sunFlower -> scoreBoard.addSun(sunFlower.action());
                case BombPlant bomb -> bomb.action(gameLogic.getZombies());
                case CoffeeBean coffeeBean -> coffeeBean.action();
                case GraveBuster graveBuster -> gameLogic.removeGrave(graveBuster.action(), borderPane);
                case Plantern plantern -> plantern.action(fog);
                case Blover blover -> blover.action(fog);
                default -> {}
            }
        }
    }

    // updates the recharges
    private void updateRecharges() {
        for (Card card : cards) card.rechargeCheck();
    }

    // saves the game
    public void save() {
        GameState state = new GameState(gameLogic, cards, scoreBoard.getScore(), mode);
        Constants.writeState(state);
    }

    // manages win or lose visuals
    public void winOrLose() {
        String condition = playMode.WinOrLose();
        switch (condition){
            case "win" -> win();
            case "lose" -> lose();
            default -> {}
        }
    }

    private void lose(){
        tl.stop();
        Pane lose = new Pane();
        AudioClip sound = Constants.setSound("losemusic", false);
        sound.play();
        ImageView loseImage = new ImageView(new Image("file:Pictures/ui/LosePage.png"));
        Constants.sizeNode(loseImage, Constants.TILE_SIZE, Constants.TILE_SIZE);
        Constants.positionNode(loseImage, (Constants.SCREEN_WIDTH - Constants.TILE_SIZE)/2, (Constants.SCREEN_HEIGHT - Constants.TILE_SIZE)/2);
        lose.getChildren().add(loseImage);
        mainPane.getChildren().add(lose);
        finnishAnimation(loseImage);
    }

    private void win(){
        tl.stop();
        addTrophy();
    }

    // adds the trophy to the screen
    private void addTrophy(){
        Pane trophyPane = new Pane();
        ImageView trophy = Constants.setButton("Trophy", Constants.TILE_SIZE, Constants.TILE_SIZE);
        Constants.positionNode(trophy, Constants.SCREEN_WIDTH/1.8, Constants.SCREEN_HEIGHT/2.5);
        trophy.setOnMouseClicked(event -> {
            AudioClip win = Constants.setSound("winmusic", false);
            win.play();
            finnishAnimation(trophy);
        });
        trophyTl(trophy);
        trophyPane.getChildren().add(trophy);
        mainPane.getChildren().add(trophyPane);
    }

    // controls the trophy movement
    private void trophyTl(ImageView trophy){
        var ref = new Object() {
            boolean isRisen = true;
        };
        double minY = trophy.getLayoutY() - 1.2 * Constants.TILE_SIZE;
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20),
                event -> ref.isRisen = trophyMove(minY, trophy, ref.isRisen)));
        timeline.setCycleCount(60);
        timeline.play();
    }

    // controls the trophy movement
    private boolean trophyMove(double minY, ImageView trophy, boolean isRisen){
        double diffY = Math.abs(minY - trophy.getLayoutY());
        if (isRisen) {
            Constants.positionNode(trophy, trophy.getLayoutX() - 1, trophy.getLayoutY() - diffY / 5);
            if (diffY < 1) isRisen = false;
        } else Constants.positionNode(trophy, trophy.getLayoutX() - 0.5, trophy.getLayoutY() + diffY / 5);
        return isRisen;
    }

    private void finnishAnimation(ImageView image){
        image.setOnMouseExited(e -> {});
        image.setOnMouseEntered(e -> {});
        image.setOnMouseClicked(e -> {});
        double size = image.getFitHeight();
        double diffX = image.getLayoutX() - Constants.SCREEN_WIDTH/3;
        double diffY = image.getLayoutY() - Constants.SCREEN_HEIGHT/10;
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), e ->{
            image.setFitWidth(image.getFitWidth() + (size / 50));
            image.setFitHeight(image.getFitHeight() + (size / 50));
            image.setLayoutX(image.getLayoutX() - (diffX / 150));
            image.setLayoutY(image.getLayoutY() - (diffY / 150));
        }));
        timeline.setCycleCount(150);
        timeline.setOnFinished(e -> finishGame((Pane)(mainPane.getChildren().getLast())));
        timeline.play();
    }

    // manages the win or lose visuals
    private void finishGame(Pane pane) {
        backgroundMusic.stop();
        pane.setStyle("-fx-background-color: rgba(56, 56, 56, 0.7);");

        ImageView restart = restartBtn();
        Constants.positionNode(restart, Constants.SCREEN_WIDTH / 1.9, Constants.SCREEN_HEIGHT / 1.3);

        ImageView mainMenu = mainMenuBtn("full");
        Constants.positionNode(mainMenu, Constants.SCREEN_WIDTH / 3.8, Constants.SCREEN_HEIGHT / 1.3);

        stage.setOnCloseRequest(event -> Constants.deleteSaveData());

        pane.getChildren().addAll(restart, mainMenu);
    }

    private ImageView restartBtn(){
        ImageView restart = Constants.setButton("Restart", Constants.SCREEN_WIDTH / 5, Constants.SCREEN_HEIGHT / 12);
        restart.setOnMouseClicked(event -> {
            GlobalState.playClickTrack();
            new PlantSelection(stage, this, mode);
        });
        return restart;
    }

    private ImageView mainMenuBtn(String state){
        ImageView mainMenu = Constants.setButton("MainMenuBtn", Constants.SCREEN_WIDTH / 5, Constants.SCREEN_HEIGHT / 12);
        mainMenu.setOnMouseClicked(event -> {
            backgroundMusic.stop();
            GlobalState.playClickTrack();
            new Introduction(stage).firstPage();

            if (state.equals("menu")) save();
            else Constants.deleteSaveData();
        });
        return mainMenu;
    }
}
