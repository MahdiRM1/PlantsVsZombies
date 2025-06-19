package main.plantsvszombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class GameUI {

    private final GameLogic gameLogic;
    private final StackPane mainPane = new StackPane();
    private final BorderPane bPane = new BorderPane();
    private final Pane pane = new Pane();
    private final GridPane gPane = new GridPane();
    private ScoreBoard scoreBoard;
    private final Timeline tl;
    private String selectedPlant;
    private int selectedButton = -1;
    private final Stage stage;

    public GameUI(Stage stage, HBox cardBar){
        this.stage = stage;
        gameLogic = new GameLogic();
        initializeStackPane(cardBar);
        tl = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            GlobalState.gameTime += 50;
            updateGame();
        }));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
        Scene scene = new Scene(mainPane, Constants.width, Constants.height - 35);
        stage.setScene(scene);
        stage.show();
    }

    public GameUI(Stage stage, ArrayList<String> plantsName){
        this.stage = stage;
        gameLogic = new GameLogic();
        initializeStackPane(cardBar(plantsName));
        tl = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            GlobalState.gameTime += 50;
            updateGame();
        }));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
        Scene scene = new Scene(mainPane, Constants.width, Constants.height - 35);
        stage.setScene(scene);
        stage.show();
    }

    private void initializeStackPane(HBox cardBar){
        bPane.getChildren().add(Constants.setBackGround("backGroundDay"));
        bPane.setBottom(map());
        scoreBoard = new ScoreBoard(bPane);
        bPane.setTop(cardBar);
        zombieGetter(0, 2);
        pane.setMouseTransparent(true);
        mainPane.getChildren().add(bPane);
        mainPane.getChildren().add(pane);
        mainPane.getChildren().add(buttonsPane());
    }

    private HBox cardBar(ArrayList<String> plants){
        HBox cardBar = new HBox(0);
        for (int i = 0; i < 6; i++) {
            cardBar.getChildren().add(getCardButton(plants.get(i), i));
        }
        cardBar.setPadding(new Insets(Constants.height/50, 0, 0, Constants.height/5.2));
        return cardBar;
    }

    private Button getCardButton(String plantName, int index){
        Button btn = new Button();
        btn.setGraphic(Constants.setCard(plantName));
        btn.setStyle("-fx-background-color: transparent");
        btn.setOnAction(event -> {
            selectedPlant = plantName;
            selectedButton = index;
        });
        btn.setOnMouseEntered(event -> btn.setStyle("-fx-background-color: rgb(62, 177, 235);"));
        btn.setOnMouseClicked(event -> btn.setStyle("-fx-background-color: rgb(62, 177, 235);"));
        btn.setOnMouseExited(e -> {
            if(plantName.equals(selectedPlant)) btn.setStyle("-fx-background-color: rgb(62, 177, 235)");
            else btn.setStyle("-fx-background-color: transparent");
        });
        return btn;
    }

    private GridPane map(){
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 9; col++) {
                gPane.add(mapButtons(row, col), col, row);
            }
        }
        gPane.setPadding(new Insets(0,0,Constants.height/16,Constants.height/2.6));
        return gPane;
    }

    private Button mapButtons(int row, int col){
        Button btn = new Button();
        btn.setPrefSize(Constants.TILE_SIZE, Constants.TILE_SIZE);
        btn.setStyle("-fx-background-color: transparent");
        btn.setOnAction(event -> {
            if(selectedPlant != null) {
                Plant plant = getPlant(row, col);
                if(scoreBoard.purchasePlant(plant.getPrice()) && gameLogic.setPlant(row, col, plant)) {
                    bPane.getChildren().add(plant.getGif());
                    btn.setOnMouseClicked(event1 -> btn.setStyle("-fx-background-color: rgba(174, 255, 174, 0.7);"));
                }
                else btn.setOnMouseClicked(event2 -> btn.setStyle("-fx-background-color: rgba(245, 50, 50, 0.6);"));
                ((HBox)bPane.getTop()).getChildren().get(selectedButton).setStyle("-fx-background-color: transparent;");
                selectedPlant = null;
            }else btn.setOnMouseClicked(event2 -> btn.setStyle("-fx-background-color: rgba(245, 50, 50, 0.6);"));
        });
        btn.setOnMouseEntered(event -> btn.setStyle("-fx-background-color: rgba(140, 140, 140, 0.3);"));
        btn.setOnMouseExited(event -> btn.setStyle("-fx-background-color: transparent;"));
        btn.setOnMouseClicked(event -> btn.setStyle("-fx-background-color: rgba(161, 245, 163, 0.3);"));
        return btn;
    }

    private AnchorPane buttonsPane(){
        AnchorPane buttonsPane = new AnchorPane();
        buttonsPane.setPickOnBounds(false);

        ImageView menu = setButton("MenuBtn", Constants.height/5, Constants.height/16);
        menu.setOnMouseClicked(event -> {
            tl.pause();
            menu();
        });

        AnchorPane.setTopAnchor(menu, -5.0);
        AnchorPane.setRightAnchor(menu, 0.0);

        buttonsPane.getChildren().add(menu);

        return buttonsPane;
    }

    private void menu(){
        AnchorPane menuPane = new AnchorPane();

        ImageView backToMenu = setButton("MainMenu", Constants.height/5, Constants.height/20);
        backToMenu.setOnMouseClicked(event -> new Introduction().firstPage(stage));
        backToMenu.setX(Constants.width/2 - Constants.height/10);
        backToMenu.setY(Constants.height/1.85);

        ImageView restart = setButton("Restart", Constants.height/5, Constants.height/20);
        restart.setOnMouseClicked(event -> new GameUI(stage, (HBox) bPane.getTop()));
        restart.setX(Constants.width/2 - Constants.height/10);
        restart.setY(Constants.height/1.65);

        ImageView backToGame = setButton("BackToGame", Constants.height/1.85, Constants.height/6.5);
        backToGame.setOnMouseClicked(event -> {
            tl.play();
            mainPane.getChildren().removeLast();
        });
        backToGame.setX(Constants.width/2 - Constants.height/3.7);
        backToGame.setY(Constants.height/1.44);

        ImageView menuPic = new ImageView(new Image("file:Pictures/ui/menu.png"));
        menuPic.setX(Constants.width/6);
        menuPic.setFitWidth(Constants.width/1.5);
        menuPic.setFitHeight(Constants.height - 35);

        menuPane.setStyle("-fx-background-color: rgba(56, 56, 56, 0.7);");
        menuPane.getChildren().addAll(menuPic, backToMenu, restart, backToGame);
        mainPane.getChildren().add(menuPane);
    }

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

    private Plant getPlant(int row, int col) {
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
            default -> {
                return null;
            }
        }
    }

    public void updateGame(){
        winOrLose();
        garbageImages();
        characterActions();
        addObjectImages();
        timeHandler();
    }

    private void timeHandler(){
        Random rdm = new Random();
        if(GlobalState.gameTime <= 15000);
        else if(GlobalState.gameTime <= 50000){
            if(GlobalState.gameTime % 5000 == 1000) zombieGetter(0, rdm.nextInt(5));
        }
        else if(GlobalState.gameTime < 70000){
            if(GlobalState.gameTime % 3000 == 0) zombieGetter(rdm.nextInt(2), rdm.nextInt(5));
        }
        else if(GlobalState.gameTime < 80000){
            if (GlobalState.gameTime == 70000) zombieGetter(4, rdm.nextInt(5));
            if(GlobalState.gameTime % 3000 == 0 || GlobalState.gameTime % 3000 == 200){
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
            if(GlobalState.gameTime % 3000 == 0 || GlobalState.gameTime % 3000 == 200){
                    for (int i = 0; i < 5; i++) {
                        zombieGetter(rdm.nextInt(4), i);
                }
            }
        }
    }

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

    private void addZombie(Zombie z){
        gameLogic.addZombie(z);
        pane.getChildren().add(z.getPicture());
    }

    private void zombieGetter(int z, int row){
        switch (z){
            case 0 -> addZombie(new OriginalZombie(row));
            case 1 -> addZombie(new ConeheadZombie(row));
            case 2 -> addZombie(new BucketheadZombie(row));
            case 3 -> addZombie(new Imp(row));
            case 4 -> addZombie(new FlagZombie(row));
        }
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

    private void garbageImages(){
        for(Bullet bullet : gameLogic.checkBulletStrike()) pane.getChildren().remove(bullet.getPicture());
        for (Zombie zombie : gameLogic.zombieToRemove()) pane.getChildren().remove(zombie.getPicture());
        for(Plant plantToRemove : gameLogic.plantsToRemove()) bPane.getChildren().remove(plantToRemove.getGif());
    }
}
