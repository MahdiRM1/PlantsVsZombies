package main.plantsvszombies;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameUI {

    private final GameLogic gameLogic;
    private final StackPane mainPane = new StackPane();
    private final BorderPane borderPane = new BorderPane();
    private final Pane pane = new Pane();
    private final List<Card> cards = new ArrayList<>();
    private final ScoreBoard scoreBoard;
    private final Stage stage;
    private final GameMode mode;
    private Timeline tl;
    private Scene scene;
    public static int selectedButton = -1;
    private Fog fog;

    // constructor: to load the previously saved game
    public GameUI(Stage stage, GameState state) {
        this.stage = stage;
        gameLogic = new GameLogic(state);
        this.mode = state.getMode();
        for (CardData data : state.getCards()) cards.add(new Card(data));
        initializeStackPane(cardBar());
        scoreBoard = new ScoreBoard(borderPane, state.getScore(), mode);
        for (Grave grave : gameLogic.getGraves()) borderPane.getChildren().add(grave.getPicture());
        GlobalState.gameTime = state.getTime();
        loadPlants();
        loadZombies();
        startGame();
    }

    //constructor: to start a new game
    public GameUI(Stage stage, List<String> plantsName, GameMode mode) {
        this.stage = stage;
        gameLogic = new GameLogic(mode);
        this.mode = mode;
        for (int i = 0; i < plantsName.size(); i++) cards.add(new Card(plantsName.get(i), i));
        initializeStackPane(cardBar());
        scoreBoard = new ScoreBoard(borderPane, 1000, mode);
//        scoreBoard = new ScoreBoard(borderPane, mode == GameMode.DAY ? 50 : 100, mode);
        for (Grave grave : gameLogic.getGraves()) borderPane.getChildren().add(grave.getPicture());
        GlobalState.gameTime = 0;
        startGame();
    }

    //manages the start of the game
    public void startGame() {
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

    private void initializeStackPane(HBox cardBar) {
        borderPane.getChildren().add(Constants.setBackGround(
                (mode == GameMode.DAY) ? "backGroundDay" : "backGroundNight"));
        borderPane.setTop(cardBar);
        borderPane.setBottom(map());
        pane.setMouseTransparent(true);
        Pane fogGrid = new Pane();
        fogGrid.setMouseTransparent(true);
        if (mode == GameMode.NIGHT) fog = new Fog(fogGrid);
        mainPane.getChildren().addAll(borderPane, pane, fogGrid, buttonsPane());
    }

    //generate card bar
    private HBox cardBar() {
        HBox cardBar = new HBox(0);
        for (Card card : cards) cardBar.getChildren().add(card.getBtn());
        cardBar.setPadding(new Insets(Constants.CARD_BAR_Y, 0, 0, Constants.CARD_BAR_X));
        return cardBar;
    }

    private void loadPlants() {
        for (Plant plant : gameLogic.getPottedPlants()) borderPane.getChildren().add(plant.getPicture());
    }

    private void loadZombies() {
        for (Zombie z : gameLogic.getZombies()) pane.getChildren().add(z.getPicture());
    }

    //generate game map
    private GridPane map() {
        GridPane grid = new GridPane();
        for (int row = 0; row < Constants.ROWS; row++)
            for (int col = 0; col < Constants.COLS; col++)
                grid.add(mapButtons(row, col), col, row);

        grid.setPadding(new Insets(0, 0, Constants.SCREEN_WIDTH / 30, Constants.SCREEN_HEIGHT / 2.6));
        return grid;
    }

    //generate mapButtons and control planting visuals
    private Button mapButtons(int row, int col) {
        Button btn = new Button();
        btn.setPrefSize(Constants.TILE_SIZE, Constants.TILE_SIZE);
        btn.setStyle("-fx-background-color: transparent");

        btn.setOnAction(event -> handleMapClick(row, col, btn));
        btn.setOnMouseEntered(event -> btn.setStyle("-fx-background-color: rgba(140, 140, 140, 0.3);"));
        btn.setOnMouseExited(event -> btn.setStyle("-fx-background-color: transparent;"));
        btn.setOnMouseClicked(event -> btn.setStyle("-fx-background-color: rgba(161, 245, 163, 0.3);"));
        return btn;
    }

    private void handleMapClick(int row, int col, Button btn) {
        if (selectedButton < 0) btn.setOnMouseClicked(event -> btn.setStyle("-fx-background-color: rgba(245, 50, 50, 0.6);"));
        else if (selectedButton < cards.size()) handlePlanting(row, col, btn);
        else useShovel(row, col, btn);
    }

    private void handlePlanting(int row, int col, Button btn) {
        Plant plant = getPlant(row, col);
        boolean placed = false;

        if (plant instanceof CoffeeBean || plant instanceof GraveBuster) {
            if (scoreBoard.purchasePlant(plant.getPrice())) placed = true;
        }

        if (plant != null && gameLogic.isPlantable(row, col) && scoreBoard.purchasePlant(plant.getPrice())) placed = true;
        else btn.setOnMouseClicked(event -> btn.setStyle("-fx-background-color: rgba(245, 50, 50, 0.6);"));

        if (placed) {
            gameLogic.setPlant(plant);
            cards.get(selectedButton).updateLastSelected();
            borderPane.getChildren().add(plant.getPicture());
            btn.setOnMouseClicked(event -> btn.setStyle("-fx-background-color: rgba(174, 255, 174, 0.7);"));
        }

        cards.get(selectedButton).getBtn().setStyle("-fx-background-color: transparent;");
        selectedButton = -1;
    }

    //manage shovel visuals
    private void useShovel(int row, int col, Button btn) {
        Plant plant = gameLogic.getPlant(row, col);
        if (!gameLogic.isPlantable(row, col) && !(plant instanceof DoomShroom ds && !ds.isSleep())) {
            borderPane.getChildren().remove(plant.getPicture());
            gameLogic.removePlant(row, col);
        }
        else btn.setOnMouseClicked(event -> btn.setStyle("-fx-background-color: rgba(245, 50, 50, 0.6);"));

        Pane buttons = (Pane) mainPane.getChildren().getLast();
        ImageView shovelBack = ((ImageView) buttons.getChildren().getLast());
        shovelBack.setEffect(null);
        buttons.getChildren().add(shovelImage());
        scene.setCursor(Cursor.DEFAULT);
        selectedButton = -1;
    }

    //control menu buttons
    private Pane buttonsPane() {
        Pane buttonsPane = new Pane();
        buttonsPane.setPickOnBounds(false);

        ImageView menu = setButton("MenuBtn", Constants.SCREEN_WIDTH / 9.4, Constants.SCREEN_HEIGHT / 16);
        menu.setOnMouseClicked(event -> {
            tl.pause();
            menu();
        });

        ImageView shovel = shovelImage();
        ImageView shovelBack = setButton("shovelBack", shovel.getFitWidth(), shovel.getFitHeight());
        Cursor cursor = new ImageCursor(shovel.getImage());
        shovelBack.setOnMouseClicked(event -> shovelButtonClick(shovel, shovelBack, cursor));

        shovelBack.setLayoutX(shovel.getLayoutX());
        menu.setLayoutX(Constants.SCREEN_WIDTH - menu.getFitWidth());

        buttonsPane.getChildren().addAll(menu, shovelBack, shovel);
        return buttonsPane;
    }

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

    //add shovel image
    private ImageView shovelImage() {
        ImageView shovel = setButton("shovel", Constants.SCREEN_WIDTH / 19, Constants.SCREEN_HEIGHT / 10);
        shovel.setMouseTransparent(true);
        shovel.setLayoutX(Constants.SCREEN_WIDTH / 2.1);
        return shovel;
    }

    //generate the menu pane
    private void menu() {
        Pane menuPane = new Pane();
        menuPane.setStyle("-fx-background-color: rgba(56, 56, 56, 0.7);");

        ImageView backToMenu = setButton("MainMenuBtn", Constants.SCREEN_WIDTH / 9.4, Constants.SCREEN_HEIGHT / 18);
        backToMenu.setLayoutX(Constants.SCREEN_WIDTH / 2.7);
        backToMenu.setLayoutY(Constants.SCREEN_HEIGHT / 1.62);
        backToMenu.setOnMouseClicked(event -> {
            save();
            new Introduction(stage).firstPage();
        });

        ImageView backToGame = setButton("BackToGame", Constants.SCREEN_WIDTH / 9.4, Constants.SCREEN_HEIGHT / 18);
        backToGame.setLayoutX(Constants.SCREEN_WIDTH / 1.95);
        backToGame.setLayoutY(Constants.SCREEN_HEIGHT / 1.62);
        backToGame.setOnMouseClicked(event -> {
            tl.play();
            mainPane.getChildren().removeLast();
        });

        ImageView menuPic = new ImageView(new Image("file:Pictures/ui/menu.png"));
        menuPic.setLayoutX(Constants.SCREEN_WIDTH / 3);
        menuPic.setLayoutY(Constants.SCREEN_HEIGHT / 4.8);
        menuPic.setFitWidth(Constants.SCREEN_WIDTH / 3);
        menuPic.setFitHeight(Constants.SCREEN_HEIGHT / 2);

        menuPane.getChildren().addAll(menuPic, backToMenu, backToGame);
        mainPane.getChildren().add(menuPane);
    }

    //generate buttons -> visuals
    private ImageView setButton(String text, double width, double height) {
        ImageView imageView = new ImageView(new Image("file:Pictures/ui/" + text + ".png"));
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setOnMouseEntered(e -> Constants.changeScale(imageView, 1.1));
        imageView.setOnMouseExited(e -> Constants.changeScale(imageView, 1 / 1.1));
        return imageView;
    }

    private Plant getPlant(int row, int col) {
        if (selectedButton < 0 || selectedButton >= cards.size()) return null;

        String plantName = cards.get(selectedButton).getPlantName();
        return switch (plantName){
            case "CoffeeBean" -> getCoffeeBean(row, col);
            case "GraveBuster" -> getGraveBuster(row, col);
            default -> Constants.getPlant(row, col, plantName, mode);
        };
    }

    private CoffeeBean getCoffeeBean(int row, int col) {
        if (gameLogic.getPlant(row, col) instanceof Shroom shroom && shroom.isSleep())
            return new CoffeeBean(row, col, shroom);
        else return null;
    }

    private GraveBuster getGraveBuster(int row, int col) {
        for (Grave grave : gameLogic.getGraves()) {
            if (grave.getRow() == row && grave.getCol() == col) return new GraveBuster(row, col, grave);
        }
        return null;
    }

    public void updateGame() {
        winOrLose();
        cleanUpImages();
        rechargeCheck();
        logicUpdates();
        plantActions();
        timeHandler();
    }

    //manages win or lose visuals
    public void winOrLose() {
        if (gameLogic.checkLose()) finishGame("lose");
        else if (gameLogic.checkWin()) finishGame("win");
    }

    //removes garbage images of struck bullets,dead zombies and eaten plants
    private void cleanUpImages() {
        for (Bullet bullet : gameLogic.checkBulletStrike()) pane.getChildren().remove(bullet.getPicture());
        for (Zombie zombie : gameLogic.zombieToRemove()) pane.getChildren().remove(zombie.getPicture());
        for (Plant plantToRemove : gameLogic.plantsToRemove()) borderPane.getChildren().removeAll(plantToRemove.getPicture());
    }

    private void logicUpdates() {
        gameLogic.updateGame();
        scoreBoard.handleSuns();
        if(mode == GameMode.NIGHT) fog.updateFog();
    }

    private void plantActions() {
        List<Plant> actions = gameLogic.plantsAction();
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

    private void rechargeCheck() {
        for (Card card : cards) card.rechargeCheck();
    }

    //controls the general timing of zombies entering and attack waves
    private void timeHandler() {
        Random rdm = new Random();
        int time = (int)GlobalState.gameTime / 1000;
//        if (time <= 20);
        if (time <= 40) handleZombie(5000, 1000, 1, rdm);
        else if (time < 60) handleZombie(4000, 0, 2, rdm);
        else if (time < 70);
        else if (time < 80) wave(rdm, 4, 1);
        else if (time < 130) {
            handleZombie(3000, 0, 4, rdm);
            handleZombie(3000, 0, 4, rdm);
        }
        else if (time < 140);
        else if (time < 155) wave(rdm, 5, 2);
    }

    private void handleZombie(long base, long mode, int zombieTypes, Random rdm) {
        if (GlobalState.gameTime % base == mode)
            spawnZombie(rdm.nextInt(zombieTypes), rdm.nextInt(5));
    }

    private void wave(Random rdm, int zombieTypes, int attackType) {
        if (GlobalState.gameTime == (long) attackType * 70_000) {
            spawnZombie(5, rdm.nextInt(5));
            if (attackType > 1) {
                for (Grave grave : gameLogic.getGraves()) {
                    spawnZombie(rdm.nextInt(4), grave.getRow(), grave.getCol());
                }
            }
        } else if (GlobalState.gameTime % 4000 == 0 || GlobalState.gameTime % 4000 == 200) {
            for (int i = 0; i < 5; i++) {
                spawnZombie(rdm.nextInt(zombieTypes), i);
            }
        }
    }

    private void spawnZombie(int z, int row) {
        spawnZombie(z, row, 10);
    }

    //determines what type of zombie to add
    private void spawnZombie(int z, int row, int col) {
        Zombie zombie = switch (z) {
            case 0 -> new OriginalZombie(row, col);
            case 1 -> new ConeheadZombie(row, col);
            case 2 -> new ScreenDoorZombie(row, col);
            case 3 -> new BucketheadZombie(row, col);
            case 4 -> new Imp(row, col);
            default -> new FlagZombie(row, col);
        };

        gameLogic.addZombie(zombie);
        pane.getChildren().add(zombie.getPicture());
    }

    //saves the game
    public void save() {
        GameState state = new GameState(gameLogic, cards, scoreBoard.getScore(), mode);

        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("savegame.dat"))) {
            out.writeObject(state);
            System.out.println("Game saved");
        } catch (IOException e) {
            System.out.println("cant save");
        }
    }

    private void finishGame(String str) {
        tl.stop();
        Pane finishPane = new Pane();
        finishPane.setStyle("-fx-background-color: rgba(56, 56, 56, 0.7);");

        ImageView restart = setButton("Restart", Constants.SCREEN_WIDTH / 5, Constants.SCREEN_HEIGHT / 12);
        restart.setLayoutX(Constants.SCREEN_WIDTH / 1.9);
        restart.setLayoutY(Constants.SCREEN_HEIGHT / 1.3);
        restart.setOnMouseClicked(event -> new Introduction(stage).plantSelectionPage(mode));

        ImageView mainMenu = setButton("MainMenuBtn", Constants.SCREEN_WIDTH / 5, Constants.SCREEN_HEIGHT / 12);
        mainMenu.setLayoutX(Constants.SCREEN_WIDTH / 3.8);
        mainMenu.setLayoutY(Constants.SCREEN_HEIGHT / 1.3);
        mainMenu.setOnMouseClicked(event -> {
            deleteSaveData();
            new Introduction(stage).firstPage();
        });

        stage.setOnCloseRequest(event -> deleteSaveData());
        if (str.equals("lose")) {
            ImageView loseImage = new ImageView(new Image("file:Pictures/ui/LosePage.png"));
            loseImage.setLayoutX(Constants.SCREEN_WIDTH / 4);
            loseImage.setLayoutY(Constants.SCREEN_HEIGHT / 6);
            loseImage.setFitWidth(Constants.SCREEN_WIDTH / 2);
            loseImage.setFitHeight(Constants.SCREEN_HEIGHT / 1.8);
            finishPane.getChildren().add(loseImage);
        } else {
            Label win = new Label("You win");
            win.setTextFill(Color.GREEN);
            win.setFont(Font.font("Arial", FontWeight.BOLD, 200));
            win.setEffect(new DropShadow(50, Color.BLACK));
            win.setLayoutX(Constants.SCREEN_WIDTH / 3.3);
            win.setLayoutY(Constants.SCREEN_HEIGHT / 3);
            finishPane.getChildren().add(win);
        }

        finishPane.getChildren().addAll(restart, mainMenu);
        mainPane.getChildren().add(finishPane);
    }

    private void deleteSaveData() {
        Path path = Paths.get("savegame.dat");
        try {
            Files.delete(path);
            System.out.println("save data deleted");
        } catch (IOException e) {
            System.out.println("cant delete save data");
        }
    }
}
