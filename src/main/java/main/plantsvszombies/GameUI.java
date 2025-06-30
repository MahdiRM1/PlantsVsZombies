package main.plantsvszombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class GameUI {

    private final GameLogic gameLogic;
    private final StackPane mainPane = new StackPane();
    private final BorderPane bPane = new BorderPane();
    private final Pane pane = new Pane();
    private final ArrayList<Card> cards = new ArrayList<>();
    private final ScoreBoard scoreBoard;
    private final Stage stage;
    private Timeline tl;
    private Scene scene;
    private GameMode mode;
    public static int selectedButton = -1;

    // constructor: to load the previously saved game
    public GameUI(Stage stage, GameState state){
        this.stage = stage;
        gameLogic = new GameLogic(setPottedPlants(state.getPlants()), state.getZombies());
        this.mode = state.getMode();
        for (CardData data : state.getCards()) cards.add(new Card(data));
        initializeStackPane(cardBar());
        scoreBoard = new ScoreBoard(bPane, state.getScore());
        GlobalState.gameTime = state.getTime();
        loadBPane();
        loadPane();
        startGame();
    }

    //constructor: to start a new game
    public GameUI(Stage stage, ArrayList<String> plantsName, GameMode mode){
        this.stage = stage;
        gameLogic = new GameLogic();
        this.mode = mode;
        for (int i = 0; i < 6; i++)  cards.add(new Card(plantsName.get(i), i));
        initializeStackPane(cardBar());
        scoreBoard = new ScoreBoard(bPane, 1000);
        GlobalState.gameTime = 0;
        startGame();
    }

    //manages the start of the game
    public void startGame(){
        tl = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            GlobalState.gameTime += 50;
            updateGame();
        }));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
        zombieGetter(0, 2);
        scene = new Scene(mainPane, Constants.width, Constants.height - 35);
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> {
            save();
            Platform.exit();
        });
        stage.show();
    }


    private void initializeStackPane(HBox cardBar){
        switch (mode){
            case DAY -> bPane.getChildren().add(Constants.setBackGround("backGroundDay"));
            case NIGHT -> bPane.getChildren().add(Constants.setBackGround("backGroundNight"));
        }
        bPane.setBottom(map());
        bPane.setTop(cardBar);
        pane.setMouseTransparent(true);
        mainPane.getChildren().add(bPane);
        mainPane.getChildren().add(pane);
        mainPane.getChildren().add(buttonsPane());
    }

    //generate card bar
    private HBox cardBar(){
        HBox cardBar = new HBox(0);
        for (int i = 0; i < 6; i++) cardBar.getChildren().add(cards.get(i).getBtn());
        cardBar.setPadding(new Insets(Constants.height/50, 0, 0, Constants.height/5.2));
        return cardBar;
    }

    private void loadBPane(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                Plant plant = gameLogic.getPottedPlants()[i][j];
                if(plant != null) bPane.getChildren().add(plant.getGif());
            }
        }
    }

    private void loadPane() {
        for (Zombie z : gameLogic.getZombies()) {
            ImageView zombieImage = z.getPicture();
            if (!pane.getChildren().contains(zombieImage)) {
                pane.getChildren().add(zombieImage);
            }
        }
    }

    //generate game map
    private GridPane map(){
        GridPane gPane = new GridPane();
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 9; col++) {
                gPane.add(mapButtons(row, col), col, row);
            }
        }
        gPane.setPadding(new Insets(0,0,Constants.height/16,Constants.height/2.6));
        return gPane;
    }

    //generate mapButtons and control planting visuals
    private Button mapButtons(int row, int col){
        Button btn = new Button();
        btn.setPrefSize(Constants.TILE_SIZE, Constants.TILE_SIZE);
        btn.setStyle("-fx-background-color: transparent");

        btn.setOnAction(event -> {
            Plant plant = getPlant(row, col);
            if(plant != null) {
                if(plant instanceof CoffeeBean coffeeBean){
                    gameLogic.coffeeBean = coffeeBean;
                    cards.get(selectedButton).updateLastSelected();
                    bPane.getChildren().add(plant.getGif());
                    btn.setOnMouseClicked(event1 -> btn.setStyle("-fx-background-color: rgba(174, 255, 174, 0.7);"));
                }
                else if(gameLogic.isPlantable(row, col) && scoreBoard.purchasePlant(plant.getPrice())) {
                    gameLogic.setPlant(row, col, plant);
                    cards.get(selectedButton).updateLastSelected();
                    bPane.getChildren().add(plant.getGif());
                    btn.setOnMouseClicked(event1 -> btn.setStyle("-fx-background-color: rgba(174, 255, 174, 0.7);"));
                }
                else btn.setOnMouseClicked(event2 -> btn.setStyle("-fx-background-color: rgba(245, 50, 50, 0.6);"));
            }else if (selectedButton == 6) {
                if(!gameLogic.isPlantable(row, col)) useShovel(row, col);
                else btn.setOnMouseClicked(event2 -> btn.setStyle("-fx-background-color: rgba(245, 50, 50, 0.6);"));
                Pane buttons = (Pane)mainPane.getChildren().getLast();
                ImageView shovelBack = ((ImageView)buttons.getChildren().getLast());
                shovelBack.setEffect(null);
                buttons.getChildren().add(shovelImage());
                scene.setCursor(Cursor.DEFAULT);
                selectedButton = -1;
            }
            else btn.setOnMouseClicked(event2 -> btn.setStyle("-fx-background-color: rgba(245, 50, 50, 0.6);"));
            if (selectedButton > -1 && selectedButton < 6) {
                Button btnSelected = cards.get(selectedButton).getBtn();
                btnSelected.setStyle("-fx-background-color: transparent;");
                selectedButton = -1;
            }
        });

        btn.setOnMouseEntered(event -> btn.setStyle("-fx-background-color: rgba(140, 140, 140, 0.3);"));
        btn.setOnMouseExited(event -> btn.setStyle("-fx-background-color: transparent;"));
        btn.setOnMouseClicked(event -> btn.setStyle("-fx-background-color: rgba(161, 245, 163, 0.3);"));
        return btn;
    }

    //control menu buttons
    private Pane buttonsPane(){
        Pane buttonsPane = new Pane();
        buttonsPane.setPickOnBounds(false);

        ImageView menu = setButton("MenuBtn", Constants.height/5, Constants.height/16);
        menu.setOnMouseClicked(event -> {
            tl.pause();
            menu();
        });

        ImageView shovel = shovelImage();
        ImageView shovelBack = setButton("shovelBack", shovel.getFitWidth(), shovel.getFitHeight());
        Cursor cursor = new ImageCursor(shovel.getImage());
        shovelBack.setOnMouseClicked(event -> {
            if (selectedButton != 6) {
                scene.setCursor(cursor);
                selectedButton = 6;
                buttonsPane.getChildren().remove(shovel);
                ColorAdjust choose = new ColorAdjust();
                choose.setBrightness(-0.5);
                shovelBack.setEffect(choose);
            }
            else {
                scene.setCursor(Cursor.DEFAULT);
                selectedButton = -1;
                buttonsPane.getChildren().add(shovel);
                shovelBack.setEffect(null);
            }
        });

        shovelBack.setLayoutX(shovel.getLayoutX());
        menu.setLayoutX(Constants.width - menu.getFitWidth());

        buttonsPane.getChildren().addAll(menu, shovelBack, shovel);

        return buttonsPane;
    }

    //add shovel image
    private ImageView shovelImage(){
        ImageView shovel = setButton("shovel", Constants.height/10, Constants.height/10);

        shovel.setMouseTransparent(true);

        shovel.setLayoutX(Constants.width/2.1);

        return shovel;
    }

    //manage shovel visuals
    private void useShovel(int row, int col) {
        bPane.getChildren().remove(gameLogic.getPottedPlants()[row][col].getGif());
        gameLogic.removePlant(row, col);
    }

    //generate the menu pane
    private void menu(){
        Pane menuPane = new Pane();

        ImageView backToMenu = setButton("MainMenuBtn", Constants.height/5, Constants.height/18);
        backToMenu.setOnMouseClicked(event -> {
            save();
            new Introduction().firstPage(stage);
        });
        backToMenu.setLayoutX(Constants.width/2.7);
        backToMenu.setLayoutY(Constants.height/1.62);

        ImageView backToGame = setButton("BackToGame", Constants.height/5, Constants.height/18);
        backToGame.setOnMouseClicked(event -> {
            tl.play();
            mainPane.getChildren().removeLast();
        });
        backToGame.setLayoutX(Constants.width/1.95);
        backToGame.setLayoutY(Constants.height/1.62);

        ImageView menuPic = new ImageView(new Image("file:Pictures/ui/menu.png"));
        menuPic.setLayoutX(Constants.width/3);
        menuPic.setLayoutY(Constants.height/4.8);
        menuPic.setFitWidth(Constants.width/3);
        menuPic.setFitHeight(Constants.height/2);

        menuPane.setStyle("-fx-background-color: rgba(56, 56, 56, 0.7);");
        menuPane.getChildren().addAll(menuPic, backToMenu, backToGame);
        mainPane.getChildren().add(menuPane);
    }

    //generate buttons -> visuals
    private ImageView setButton(String text, double width, double height){
        ImageView imageView = new ImageView(new Image("file:Pictures/ui/" + text + ".png"));
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);

        imageView.setOnMouseEntered(e -> {
            double differentX = imageView.getFitWidth() * 1.1 - imageView.getFitWidth();
            double differentY = imageView.getFitHeight() * 1.1 - imageView.getFitHeight();
            imageView.setFitWidth(imageView.getFitWidth() * 1.1);
            imageView.setFitHeight(imageView.getFitHeight() * 1.1);
            imageView.setLayoutX(imageView.getLayoutX() - differentX/2);
            imageView.setLayoutY(imageView.getLayoutY() - differentY/2);
        });
        imageView.setOnMouseExited(e -> {
            double differentX = imageView.getFitWidth() - imageView.getFitWidth() / 1.1;
            double differentY = imageView.getFitHeight() - imageView.getFitHeight() / 1.1;
            imageView.setFitWidth(imageView.getFitWidth() / 1.1);
            imageView.setFitHeight(imageView.getFitHeight() / 1.1);
            imageView.setLayoutX(imageView.getLayoutX() + differentX/2);
            imageView.setLayoutY(imageView.getLayoutY() + differentY/2);
        });

        return imageView;
    }

    private Plant getPlant(int row, int col){
        if (selectedButton < 0 || selectedButton > 5) return null;
        return getPlant(row,col, cards.get(selectedButton).getPlantName());
    }

    private Plant getPlant(int row, int col, String selectedPlant) {
        switch (selectedPlant) {
            case "PeaShooter" -> {
                return new PeaShooter(row, col);
            }
            case "SunFlower" -> {
                return new SunFlower(row, col);
            }
            case "WallNut" -> {
                return new WallNut(row, col);
            }
            case "TallNut" -> {
                return new TallNut(row, col);
            }
            case "Repeater" -> {
                return new Repeater(row, col);
            }
            case "SnowPea" -> {
                return new SnowPea(row, col);
            }
            case "CherryBomb" -> {
                return new CherryBomb(row, col);
            }
            case "Jalapeno" -> {
                return new Jalapeno(row, col);
            }
            case "PuffShroom" -> {
                return new PuffShroom(row, col, mode);
            }
            case "ScaredyShroom" -> {
                return new ScaredyShroom(row, col, mode);
            }case "IceShroom" -> {
                return new IceShroom(row, col, mode);
            }case "DoomShroom" -> {
                return new DoomShroom(row, col, mode);
            }
            case "CoffeeBean" -> {
                if(gameLogic.getPottedPlants()[row][col] instanceof Shroom shroom && shroom.isSleep())
                    return new CoffeeBean(row, col, shroom);
                else return null;
            }
            case null, default -> {
                return null;
            }
        }
    }

    public void updateGame(){
        winOrLose();
        garbageImages();
        characterActions();
        addObjectImages();
        rechargeCheck();
        timeHandler();
    }
    //manages win or lose visuals
    public void winOrLose() {
        if(gameLogic.checkLose()) {
            Label lose = new Label("You lost");
            lose.setTextFill(Color.RED);
            lose.setFont(Font.font("Arial", FontWeight.BOLD, 100));
            lose.setEffect(new DropShadow(50, Color.BLACK));
            pane.getChildren().add(lose);
            tl.stop();
        }
        if(gameLogic.checkWin()) {
            Label win = new Label("You win");
            win.setTextFill(Color.RED);
            win.setFont(Font.font("Arial", FontWeight.BOLD, 100));
            win.setEffect(new DropShadow(10, Color.BLACK));
            pane.getChildren().add(win);
        }

    }
    //removes garbage images of struck bullets,dead zombies and eaten plants
    private void garbageImages(){
        for (Bullet bullet : gameLogic.checkBulletStrike()) pane.getChildren().remove(bullet.getPicture());
        for (Zombie zombie : gameLogic.zombieToRemove()) pane.getChildren().remove(zombie.getPicture());
        for (Plant plantToRemove : gameLogic.plantsToRemove()) bPane.getChildren().remove(plantToRemove.getGif());
    }

    private void characterActions(){
        for(Zombie z : gameLogic.getZombies()) z.action();
        for(Bullet b : gameLogic.getBullets()) b.move();
        gameLogic.setZombieState();
        scoreBoard.sunHandler();
    }

    private void addObjectImages(){
        for(SunFlower sunFlower : gameLogic.sunFlowers()) {
            scoreBoard.addSun(sunFlower.givenSun());
        }

        for(PeaPlant shooter : gameLogic.plantsAligned()) {
            Bullet b = shooter.shoot(shooter.getRow(), shooter.getCol());
            if(b != null) {
                gameLogic.addBullet(b);
                pane.getChildren().addAll(b.getPicture());
            }
        }
    }

    private void rechargeCheck(){
        for(Card card : cards) card.rechargeCheck();
    }

    //controls the general timing of zombies entering and attack waves
    private void timeHandler(){
        Random rdm = new Random();
        if(GlobalState.gameTime <= 20000);
        else if(GlobalState.gameTime <= 50000){
            if(GlobalState.gameTime % 5000 == 1000) zombieGetter(0, rdm.nextInt(5));
        }
        else if(GlobalState.gameTime < 70000){
            if(GlobalState.gameTime % 4000 == 0) zombieGetter(rdm.nextInt(2), rdm.nextInt(5));
        }
        else if(GlobalState.gameTime < 80000){
            if (GlobalState.gameTime == 70000) zombieGetter(4, rdm.nextInt(5));
            if(GlobalState.gameTime % 4000 == 0 || GlobalState.gameTime % 4000 == 200){
                for (int i = 0; i < 5; i++) {
                    zombieGetter(rdm.nextInt(2), i);
                }
            }
        }
        else if(GlobalState.gameTime < 130000){
            if(GlobalState.gameTime % 3000 == 0) {
                zombieGetter(rdm.nextInt(3), rdm.nextInt(5));
                zombieGetter(rdm.nextInt(3), rdm.nextInt(5));
            }
        }
        else if (GlobalState.gameTime < 150000){
            if(GlobalState.gameTime == 130000)zombieGetter(4, rdm.nextInt(5));
            if(GlobalState.gameTime % 4000 == 0 || GlobalState.gameTime % 4000 == 200){
                for (int i = 0; i < 5; i++) {
                    zombieGetter(rdm.nextInt(4), i);
                }
            }
        }
    }

    //add zombies to the pane
    private void addZombie(Zombie z){
        gameLogic.addZombie(z);
        pane.getChildren().add(z.getPicture());
    }

    //determines what type of zombie to add
    private void zombieGetter(int z, int row){
        switch (z){
            case 0 -> addZombie(new OriginalZombie(row));
            case 1 -> addZombie(new ConeheadZombie(row));
            case 2 -> addZombie(new BucketheadZombie(row));
            case 3 -> addZombie(new Imp(row));
            case 4 -> addZombie(new FlagZombie(row));
        }
    }

    //saves the game
    public void save(){
        GameState state = new GameState(gameLogic, cards, scoreBoard.getScore(), mode);

        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("savegame.dat"))) {
            out.writeObject(state);
            System.out.println("Game saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //generates the plants matrix
    private Plant[][] setPottedPlants(ArrayList<PlantData> plantData){
        Plant[][] pottedPlants = new Plant[5][9];
        for (PlantData data : plantData){
            pottedPlants[data.getRow()][data.getCol()] = getPlant(data.getRow(), data.getCol(), data.getType());
        }
        return pottedPlants;
    }
}
