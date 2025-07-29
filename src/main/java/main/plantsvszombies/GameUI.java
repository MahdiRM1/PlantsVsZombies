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
import javafx.scene.media.AudioClip;
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
    private final Stage stage;
    private final GameMode mode;
    private ScoreBoard scoreBoard;
    private Timeline tl;
    private Scene scene;
    public static int selectedButton = -1;
    private Fog fog;
    private AudioClip backgroundMusic;

    // ?constructor: to load the previously saved game
    public GameUI(Stage stage, GameState state) {
        this.stage = stage;
        gameLogic = new GameLogic(state);
        this.mode = state.getMode();
        for (CardData data : state.getCards()) cards.add(new Card(data));
        initStackPane(cardBar(), state.getScore(), state.getTime());
        if (mode == GameMode.NIGHT) initFog(state.getFogLength());
        loadPlants();
        loadZombies();
        startGame();
    }

    // ?constructor: to start a new game
    public GameUI(Stage stage, List<String> plantsName, GameMode mode) {
        this.stage = stage;
        gameLogic = new GameLogic(mode);
        this.mode = mode;
        for (int i = 0; i < plantsName.size(); i++) cards.add(new Card(plantsName.get(i), i));
//        *initStackPane(cardBar(), mode == GameMode.DAY ? 50 : 100, 0);
        initStackPane(cardBar(), 1000, 18000);
        if (mode == GameMode.NIGHT) initFog((int)(Math.random() * 3) + 5);
        startGame();
    }

    // ?manages the start of the game
    public final void startGame() {
        backgroundMusic = new AudioClip("file:Audio/Loonboon.mp3");
        backgroundMusic.setCycleCount(Timeline.INDEFINITE);
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

    // ?initializes the stack pane
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

    // ?initialized the fog
    private void initFog(int fogLength) {
        Pane fogGrid = new Pane();
        fogGrid.setMouseTransparent(true);
        fog = new Fog(fogGrid, fogLength);
        mainPane.getChildren().add(2, fogGrid);
        mode.setFogLength(fogLength);
    }

    // ?generate card bar
    private HBox cardBar() {
        HBox cardBar = new HBox(0);
        for (Card card : cards) cardBar.getChildren().add(card.getBtn());
        cardBar.setPadding(new Insets(Constants.CARD_BAR_Y, 0, 0, Constants.CARD_BAR_X));
        return cardBar;
    }

    // ?loads the potted plants
    private void loadPlants() {
        for (Plant plant : gameLogic.getPottedPlants()) borderPane.getChildren().add(plant.getPicture());
    }

    // ?loads the zombies
    private void loadZombies() {
        for (Zombie z : gameLogic.getZombies()) pane.getChildren().add(z.getPicture());
    }

    // ?generate game map
    private GridPane map() {
        GridPane grid = new GridPane();
        for (int row = 0; row < Constants.ROWS; row++)
            for (int col = 0; col < Constants.COLS; col++)
                grid.add(mapButtons(row, col), col, row);

        grid.setPadding(new Insets(0, 0, Constants.SCREEN_WIDTH / 30, Constants.SCREEN_HEIGHT / 2.6));
        return grid;
    }

    // ?generate mapButtons and control planting visuals
    private Button mapButtons(int row, int col) {
        Button btn = new Button();
        Constants.sizeNode(btn, Constants.TILE_SIZE, Constants.TILE_SIZE);
        btn.setStyle("-fx-background-color: transparent");

        btn.setOnAction(event -> handleMapClick(row, col, btn));
        btn.setOnMouseEntered(event -> btn.setStyle("-fx-background-color: rgba(140, 140, 140, 0.3);"));
        btn.setOnMouseExited(event -> btn.setStyle("-fx-background-color: transparent;"));
        btn.setOnMouseClicked(event -> btn.setStyle("-fx-background-color: rgba(161, 245, 163, 0.3);"));
        return btn;
    }

    // ?handles the map click
    private void handleMapClick(int row, int col, Button btn) {
        if (selectedButton < 0) btn.setOnMouseClicked(event -> btn.setStyle("-fx-background-color: rgba(245, 50, 50, 0.6);"));
        else if (selectedButton < cards.size()) handlePlanting(row, col, btn);
        else useShovel(row, col, btn);
    }

    // ?handles the planting
    private void handlePlanting(int row, int col, Button btn) {
        Plant plant = getPlant(row, col);
        boolean placed = false;

        if (plant instanceof CoffeeBean || plant instanceof GraveBuster)
            if (scoreBoard.purchasePlant(plant.getPrice())) placed = true;

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

    // ?manage shovel visuals
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

    // ?control menu buttons
    private Pane buttonsPane() {
        Pane buttonsPane = new Pane();
        buttonsPane.setPickOnBounds(false);

        ImageView menu = setButton("MenuBtn", Constants.SCREEN_WIDTH / 9.4, Constants.SCREEN_HEIGHT / 16);
        menu.setOnMouseClicked(event -> {
            GlobalState.playClickTrack();
            tl.pause();
            menu();
        });
        Constants.positionNode(menu, Constants.SCREEN_WIDTH - menu.getFitWidth(), 0);

        ImageView shovel = shovelImage();
        ImageView shovelBack = setButton("shovelBack", shovel.getFitWidth(), shovel.getFitHeight());
        Constants.positionNode(shovelBack, shovel.getLayoutX(), 0);

        Cursor cursor = new ImageCursor(shovel.getImage());
        shovelBack.setOnMouseClicked(event -> {
            GlobalState.playClickTrack();
            shovelButtonClick(shovel, shovelBack, cursor);
        });

        buttonsPane.getChildren().addAll(menu, shovelBack, shovel);
        return buttonsPane;
    }

    // ?handles the shovel button click
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

    // ?add shovel image
    private ImageView shovelImage() {
        ImageView shovel = setButton("shovel", Constants.SCREEN_WIDTH / 19, Constants.SCREEN_HEIGHT / 10);
        shovel.setMouseTransparent(true);
        Constants.positionNode(shovel, Constants.SCREEN_WIDTH / 2.1, 0);
        return shovel;
    }

    // ?generate the menu pane
    private void menu() {
        Pane menuPane = new Pane();
        menuPane.setStyle("-fx-background-color: rgba(56, 56, 56, 0.7);");

        ImageView backToMenu = setButton("MainMenuBtn", Constants.SCREEN_WIDTH / 9.4, Constants.SCREEN_HEIGHT / 18);
        Constants.positionNode(backToMenu, Constants.SCREEN_WIDTH / 2.7, Constants.SCREEN_HEIGHT / 1.62);
        backToMenu.setOnMouseClicked(event -> {
            GlobalState.playClickTrack();
            save();
            new Introduction(stage).firstPage();
        });

        ImageView backToGame = setButton("BackToGame", Constants.SCREEN_WIDTH / 9.4, Constants.SCREEN_HEIGHT / 18);
        Constants.positionNode(backToGame, Constants.SCREEN_WIDTH / 1.95, Constants.SCREEN_HEIGHT / 1.62);
        backToGame.setOnMouseClicked(event -> {
            GlobalState.playClickTrack();
            tl.play();
            mainPane.getChildren().removeLast();
        });

        ImageView menuPic = new ImageView(new Image("file:Pictures/ui/menu.png"));
        Constants.positionNode(menuPic, Constants.SCREEN_WIDTH / 3, Constants.SCREEN_HEIGHT / 4.8);
        Constants.sizeNode(menuPic, Constants.SCREEN_WIDTH / 3, Constants.SCREEN_HEIGHT / 2);

        menuPane.getChildren().addAll(menuPic, backToMenu, backToGame);
        mainPane.getChildren().add(menuPane);
    }

    // ?generate buttons -> visuals
    private ImageView setButton(String text, double width, double height) {
        ImageView imageView = new ImageView(new Image("file:Pictures/ui/" + text + ".png"));
        Constants.sizeNode(imageView, width, height);
        imageView.setOnMouseEntered(e -> Constants.changeScale(imageView, 1.1));
        imageView.setOnMouseExited(e -> Constants.changeScale(imageView, 1));
        return imageView;
    }

    // ?get the plant from the selected button
    private Plant getPlant(int row, int col) {
        if (selectedButton < 0 || selectedButton >= cards.size()) return null;

        String plantName = cards.get(selectedButton).getPlantName();
        return switch (plantName){
            case "CoffeeBean" -> getCoffeeBean(row, col);
            case "GraveBuster" -> getGraveBuster(row, col);
            default -> Constants.getPlant(row, col, plantName, mode == GameMode.DAY);
        };
    }

    // ?get the coffee bean from the selected button
    private CoffeeBean getCoffeeBean(int row, int col) {
        if (gameLogic.getPlant(row, col) instanceof Shroom shroom && shroom.isSleep())
            return new CoffeeBean(row, col, shroom);
        else return null;
    }

    // ?get the grave buster from the selected button
    private GraveBuster getGraveBuster(int row, int col) {
        for (Grave grave : gameLogic.getGraves()) {
            if (grave.getRow() == row && grave.getCol() == col) return new GraveBuster(row, col, grave);
        }
        return null;
    }

    // ?updates the game
    public void updateGame() {
        winOrLose();
        cleanUpImages();
        updateRecharges();
        logicUpdates();
        plantActions();
        timeHandler();
    }

    // ?removes garbage images of struck bullets,dead zombies and eaten plants
    private void cleanUpImages() {
        for (Plant plantToRemove : gameLogic.plantsToRemove()) borderPane.getChildren().removeAll(plantToRemove.getPicture());
        for (Bullet bullet : gameLogic.checkBulletStrike()) pane.getChildren().remove(bullet.getPicture());
        for (Zombie zombie : gameLogic.zombieToRemove()) pane.getChildren().remove(zombie.getPicture());
    }

    // ?updates the game logic
    private void logicUpdates() {
        gameLogic.updateGame();
        scoreBoard.handleSuns();
        if(mode == GameMode.NIGHT) fog.updateFog();
    }

    // ?updates the plant actions
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

    // ?updates the recharges
    private void updateRecharges() {
        for (Card card : cards) card.rechargeCheck();
    }

//   private void timeHandler(){
//       if (GlobalState.gameTime == 1000) spawnZombie(1, 1);
//       if (GlobalState.gameTime == 7000) spawnZombie(0, 1);
//   }

    // ?controls the general timing of zombies entering and attack waves
     private void timeHandler() {
         Random rdm = new Random();
         int time = (int)GlobalState.gameTime / 1000;
         if (GlobalState.gameTime == 20_000) {
             AudioClip sound = new AudioClip("file:Audio/awooga.mp3");
             sound.play();
         }
         if (time <= 20) return;

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

    // ?handles the zombie entering
    private void handleZombie(long base, long mode, int zombieTypes, Random rdm) {
        if (GlobalState.gameTime % base == mode)
            spawnZombie(rdm.nextInt(zombieTypes), rdm.nextInt(5));
    }

    // ?handles the attack waves
    private void wave(Random rdm, int zombieTypes, int attackType) {
        if (GlobalState.gameTime == (long) attackType * 70_000) {
            spawnZombie(5, rdm.nextInt(5));
            AudioClip attackWave = new AudioClip("file:Audio/hugewave.mp3");
            if (attackType > 1) {
                attackWave = new AudioClip("file:Audio/siren.mp3");
                AudioClip sound = new AudioClip("file:Audio/awooga.mp3");
                sound.play();
                for (Grave grave : gameLogic.getGraves()) {
                    spawnZombie(rdm.nextInt(4), grave.getRow(), grave.getCol());
                }
            }
            attackWave.play();
        } else if (GlobalState.gameTime % 4000 == 0 || GlobalState.gameTime % 4000 == 200) {
            for (int i = 0; i < 5; i++) {
                spawnZombie(rdm.nextInt(zombieTypes), i);
            }
        }
    }

    // ?spawns a zombie
    private void spawnZombie(int z, int row) {
        spawnZombie(z, row, 11);
    }

    // ?determines what type of zombie to add
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

    // ?saves the game
    public void save() {
        GameState state = new GameState(gameLogic, cards, scoreBoard.getScore(), mode);

        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("savegame.dat"))) {
            out.writeObject(state);
            System.out.println("Game saved");
        } catch (IOException e) {
            System.out.println("cant save data");
        }
    }

    // ?manages win or lose visuals
    public void winOrLose() {
        if (gameLogic.checkLose()) finishGame("lose");
        else if (gameLogic.checkWin()) addTrophy();
    }

    // ?adds the trophy to the screen
    private void addTrophy(){
        tl.stop();
        Pane trophyPane = new Pane();
        ImageView trophy = setButton("Trophy", Constants.TILE_SIZE, Constants.TILE_SIZE);
        Constants.positionNode(trophy, Constants.SCREEN_WIDTH/1.8, Constants.SCREEN_HEIGHT/2.5);
        trophy.setOnMouseClicked(event -> finishGame("win"));
        trophyTl(trophy);
        trophyPane.getChildren().add(trophy);
        mainPane.getChildren().add(trophyPane);
    }

    // ?controls the trophy movement
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

    // ?controls the trophy movement
    private boolean trophyMove(double minY, ImageView trophy, boolean isRisen){
        double diffY = Math.abs(minY - trophy.getLayoutY());
        if (isRisen) {
            Constants.positionNode(trophy, trophy.getLayoutX() - 1, trophy.getLayoutY() - diffY / 5);
            if (diffY < 1) isRisen = false;
        } else Constants.positionNode(trophy, trophy.getLayoutX() - 0.5, trophy.getLayoutY() + diffY / 5);
        return isRisen;
    }

    // ?manages the win or lose visuals
    private void finishGame(String str) {
        tl.stop();
        backgroundMusic.stop();
        Pane finishPane = new Pane();
        finishPane.setStyle("-fx-background-color: rgba(56, 56, 56, 0.7);");
        AudioClip sound;

        ImageView restart = setButton("Restart", Constants.SCREEN_WIDTH / 5, Constants.SCREEN_HEIGHT / 12);
        Constants.positionNode(restart, Constants.SCREEN_WIDTH / 1.9, Constants.SCREEN_HEIGHT / 1.3);
        restart.setOnMouseClicked(event -> {
            GlobalState.playClickTrack();
            new Introduction(stage).plantSelectionPage(mode);
        });

        ImageView mainMenu = setButton("MainMenuBtn", Constants.SCREEN_WIDTH / 5, Constants.SCREEN_HEIGHT / 12);
        Constants.positionNode(mainMenu, Constants.SCREEN_WIDTH / 3.8, Constants.SCREEN_HEIGHT / 1.3);
        mainMenu.setOnMouseClicked(event -> {
            GlobalState.playClickTrack();
            deleteSaveData();
            new Introduction(stage).firstPage();
        });

        stage.setOnCloseRequest(event -> deleteSaveData());
        if (str.equals("lose")) {
            ImageView loseImage = new ImageView(new Image("file:Pictures/ui/LosePage.png"));
            Constants.sizeNode(loseImage, Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 1.8);
            Constants.positionNode(loseImage, Constants.SCREEN_WIDTH / 4, Constants.SCREEN_HEIGHT / 6);
            finishPane.getChildren().add(loseImage);
            sound = new AudioClip("file:Audio/scream.mp3");
        } else {
            Label win = new Label("You win");
            win.setTextFill(Color.GREEN);
            win.setFont(Font.font("Consolas", FontWeight.BOLD, 200));
            win.setEffect(new DropShadow(50, Color.BLACK));
            Constants.positionNode(win, Constants.SCREEN_WIDTH / 3.3, Constants.SCREEN_HEIGHT / 3);
            finishPane.getChildren().add(win);
            sound = new AudioClip("file:Audio/winmusic.mp3");
        }
        sound.play();

        finishPane.getChildren().addAll(restart, mainMenu);
        mainPane.getChildren().add(finishPane);
    }

    // ?deletes the save data if the game is finished
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
