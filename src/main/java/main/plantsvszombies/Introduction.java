package main.plantsvszombies;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Introduction {

    private Stage stage;
    private final List<String> selectedCards = new ArrayList<>();
    private HBox cardBar;
    private GameMode mode;

    public void firstPage(Stage stage){
        this.stage = stage;
        Scene scene = new Scene(MainMenuPane(), Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT - 35);
        stage.setScene(scene);
        stage.show();
    }

    private void load(){
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("savegame.dat"))) {
            GameState state = (GameState) input.readObject();
            new GameUI(stage, state);
            System.out.println("game loaded");
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void PlantSelectionPage(){
        Pane pane = new Pane();
        pane.getChildren().add(Constants.setBackGround(
                (mode == GameMode.DAY) ? "plantSelectionDay" : "plantSelectionNight"));

        cardBar = new HBox(0);
        cardBar.setLayoutX(Constants.CARD_BAR_X);
        cardBar.setLayoutY(Constants.CARD_BAR_Y);

        double cardSpacing = Constants.SCREEN_WIDTH / 37.5;
        double layoutX = Constants.SCREEN_WIDTH / 14;

        HBox box1 = new HBox(cardSpacing,
                getCardButton("PeaShooter"), getCardButton("SunFlower") ,
                getCardButton("WallNut") ,getCardButton("TallNut")
        );
        positionHBox(box1, layoutX, Constants.SCREEN_HEIGHT/4);

        HBox box2 = new HBox(cardSpacing,
                getCardButton("Repeater"), getCardButton("SnowPea"),
                getCardButton("CherryBomb") ,getCardButton("Jalapeno")
        );
        positionHBox(box2, layoutX, Constants.SCREEN_HEIGHT/2.5);

        HBox box3 = new HBox(cardSpacing,
                getCardButton("PuffShroom"), getCardButton("CoffeeBean"),
                getCardButton("ScaredyShroom"), getCardButton("IceShroom")
        );
        positionHBox(box3, layoutX, Constants.SCREEN_HEIGHT/1.8);

        HBox box4 = new HBox(cardSpacing,
                getCardButton("DoomShroom")
        );
        positionHBox(box4, layoutX, Constants.SCREEN_HEIGHT/1.4);

        pane.getChildren().addAll(Constants.setScoreBoardPicture(),
                cardBar, box1, box2, box3, box4, startGameBtn());
        for (int i = 0; i < 8; i++) pane.getChildren().add(createZombie());
        Scene scene = new Scene(pane, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT - 35);
        stage.setScene(scene);
        stage.show();
    }

    private void positionHBox(HBox box, double x, double y) {
        box.setLayoutX(x);
        box.setLayoutY(y);
    }

    private ImageView startGameBtn(){
        Image letsRock1 = new Image("file:Pictures/ui/LetsRock1.png");
        Image letsRock2 = new Image("file:Pictures/ui/LetsRock2.png");
        ImageView start = new ImageView(letsRock1);
        start.setFitWidth(Constants.SCREEN_WIDTH/7.5);
        start.setFitHeight(Constants.SCREEN_HEIGHT/16);
        start.setLayoutX(Constants.SCREEN_WIDTH/5.65);
        start.setLayoutY(Constants.SCREEN_HEIGHT/1.122);

        start.setOnMouseEntered(event -> {
            if (selectedCards.size() != 6) return;

            start.setImage(letsRock2);
            Constants.changeScale(start, 1.05);
        });
        start.setOnMouseExited(event -> {
            if (selectedCards.size() != 6) return;

            start.setImage(letsRock1);
            Constants.changeScale(start, 1/1.05);
        });
        start.setOnMouseClicked(event -> {
            if (selectedCards.size() == 6) new GameUI(stage, selectedCards, mode);
        });
        return start;
    }

    private ImageView createZombie(){
        Random rdm = new Random();
        String[] zombieTypes = {"OriginalZombie", "ConeheadZombie", "BucketheadZombie", "Imp"};
        String chosen = zombieTypes[rdm.nextInt(4)];

        ImageView image = new ImageView(new Image("file:Pictures/ZombieGif/" + chosen + ".gif"));
        image.setFitHeight(Constants.ZOMBIE_PIC_HEIGHT);
        image.setFitWidth(Constants.ZOMBIE_PIC_WEIGHT);
        image.setLayoutX(Constants.SCREEN_WIDTH/1.8 + rdm.nextDouble(Constants.SCREEN_WIDTH/3));
        image.setLayoutY(rdm.nextDouble(Constants.SCREEN_HEIGHT/1.5));
        return image;
    }


    private Button getCardButton(String plantName){
        Button btn = new Button();
        btn.setGraphic(Constants.setCard(plantName));
        btn.setStyle("-fx-background-color: transparent");

        btn.setOnAction(event -> {
            if(selectedCards.contains(plantName)) {
                selectedCards.remove(plantName);
                cardBar.getChildren().removeIf(node ->
                        ((ImageView) ((Button) node).getGraphic()).getImage()//image on button in cardBar
                                .equals(((ImageView) btn.getGraphic()).getImage()));//image on clicked btn
            }
            else if(selectedCards.size() < 6) {
                selectedCards.add(plantName);
                Button btn2 = getCardButton(plantName);
                ImageView imageView = new ImageView(((ImageView)btn.getGraphic()).getImage());
                imageView.setFitWidth(Constants.PLANT_CARD_WIDTH);
                imageView.setFitHeight(Constants.PLANT_CARD_HEIGHT);
                btn2.setGraphic(imageView);
                cardBar.getChildren().add(btn2);
            }
            else btn.setStyle("-fx-background-color: rgb(150, 0, 0);");
        });
        btn.setOnMouseEntered(event -> Constants.changeScale(btn.getGraphic(), 1.05));
        btn.setOnMouseExited(event -> {
            Constants.changeScale(btn.getGraphic(), 1/ 1.05);
            btn.setStyle("-fx-background-color: transparent;");
        });
        return btn;
    }

    private Pane MainMenuPane(){
        Pane pane = new Pane();
        pane.getChildren().addFirst(Constants.setBackGround("MainMenu"));

        ImageView adventure = menuItem("Adventure", Constants.SCREEN_WIDTH/2.6, Constants.SCREEN_HEIGHT/4.2);
        adventure.setLayoutX(Constants.SCREEN_WIDTH / 1.97);
            adventure.setLayoutY(Constants.SCREEN_HEIGHT / 8);
        adventure.setOnMouseClicked(event -> {
            mode = GameMode.DAY;
            PlantSelectionPage();
        });

            ImageView newGame = menuItem("NewGame", Constants.SCREEN_WIDTH/2.7, Constants.SCREEN_HEIGHT/4.35);
        newGame.setLayoutX(Constants.SCREEN_WIDTH/1.98);
        newGame.setLayoutY(Constants.SCREEN_HEIGHT/3.1);
        newGame.setOnMouseClicked(event -> {
            mode = GameMode.NIGHT;
            PlantSelectionPage();
        });

        ImageView loadGame = menuItem("LoadGame", Constants.SCREEN_WIDTH/3, Constants.SCREEN_HEIGHT/4.8);
        loadGame.setLayoutX(Constants.SCREEN_WIDTH / 1.94);
        loadGame.setLayoutY(Constants.SCREEN_HEIGHT/2);
        loadGame.setOnMouseClicked(event -> load());

        ImageView quit = menuItem("Quit", Constants.SCREEN_WIDTH/8.4, Constants.SCREEN_HEIGHT/6);
        quit.setLayoutX(Constants.SCREEN_WIDTH / 1.14);
        quit.setLayoutY(Constants.SCREEN_HEIGHT / 1.405);
        quit.setOnMouseClicked(event -> stage.close());

        ImageView help = menuItem("help", Constants.SCREEN_WIDTH/8.4, Constants.SCREEN_HEIGHT/4);
        help.setLayoutX(Constants.SCREEN_WIDTH / 1.28);
        help.setLayoutY(Constants.SCREEN_HEIGHT / 1.54);

        ImageView options = menuItem("option", Constants.SCREEN_WIDTH/6.55, Constants.SCREEN_HEIGHT/5.4);
        options.setLayoutX(Constants.SCREEN_WIDTH / 1.475);
        options.setLayoutY(Constants.SCREEN_HEIGHT / 1.47);

        pane.getChildren().addAll(adventure, newGame, loadGame, quit, options, help);
        return pane;
    }

    private ImageView menuItem(String str, double width, double height){
        ImageView imageView = new ImageView(new Image("file:Pictures/ui/" + str + ".png"));
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setOnMouseEntered(event -> Constants.changeScale(imageView, 1.05));
        imageView.setOnMouseExited(event -> Constants.changeScale(imageView, 1/1.05));
        return imageView;
    }
}
