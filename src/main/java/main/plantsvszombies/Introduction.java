package main.plantsvszombies;

import javafx.geometry.Pos;
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
import java.util.Random;

public class Introduction {

    private Stage stage;
    private final ArrayList<String> selectedCards = new ArrayList<>();
    private HBox cardBar;

    public void firstPage(Stage stage){
        this.stage = stage;
        Scene scene = new Scene(Pane(), Constants.width, Constants.height - 35);
        stage.setScene(scene);
        stage.show();
    }

    private void load(){
        try (ObjectInputStream input = new ObjectInputStream(
                new FileInputStream("savegame.dat"))) {
            GameState state = (GameState) input.readObject();
            new GameUI(stage, state);
            System.out.println("game loaded");
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void chooseCardPage(){
        Pane pane = new Pane();
        pane.getChildren().addFirst(Constants.setBackGround("plantSelectionBG"));

        cardBar = new HBox(0);
        cardBar.setLayoutX(Constants.height/5.2);
        cardBar.setLayoutY(Constants.height/50);

        HBox box1 = new HBox(Constants.height/20,
                getCardButton("PeaShooter"), getCardButton("SunFlower") ,
                getCardButton("WallNut") ,getCardButton("TallNut")
        );
        HBox box2 = new HBox(Constants.height/20,
                getCardButton("Repeater"), getCardButton("SnowPea"),
                getCardButton("CherryBomb") ,getCardButton("Jalapeno")
        );

        box1.setLayoutX(Constants.height/7.5);
        box2.setLayoutX(Constants.height/7.5);
        box1.setLayoutY(Constants.height/4);
        box2.setLayoutY(Constants.height/2.5);
        pane.getChildren().addAll(Constants.setScoreBoardPicture(), cardBar, box1, box2, startGameBtn());
        for (int i = 0; i < 8; i++) pane.getChildren().add(addZombie());
        Scene scene = new Scene(pane, Constants.width, Constants.height - 35);
        stage.setScene(scene);
        stage.show();
    }

    private Button startGameBtn(){
        Button btn = new Button("Let's Rock");
        btn.setPrefSize(Constants.height/4.3, Constants.height/30);
        btn.setStyle(
            "-fx-background-radius: 3; " +
            "-fx-background-color: rgb(100, 50, 0);" +
            "-fx-text-fill: green; " +
            "-fx-font-size: 30px; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0.5, 0, 1);"
        );
        btn.setOnMouseEntered(event -> {
            if(selectedCards.size() != 6) btn.setStyle(btn.getStyle() + "-fx-background-color: rgb(100, 0, 0);");
            else {
                double differentX = btn.getPrefWidth() * 1.1 - btn.getPrefWidth();
                double differentY = btn.getPrefHeight() * 1.1 - btn.getPrefHeight();
                btn.setPrefSize(btn.getPrefWidth() * 1.1, btn.getPrefHeight() * 1.1);
                btn.setLayoutX(btn.getLayoutX() - differentX/2);
                btn.setLayoutY(btn.getLayoutY() - differentY/2);
            }
        });
        btn.setOnMouseExited(event -> {
            if(selectedCards.size() == 6) {
                double differentX = btn.getPrefWidth() / 1.1 - btn.getPrefWidth();
                double differentY = btn.getPrefHeight() / 1.1 - btn.getPrefHeight();
                btn.setPrefSize(btn.getPrefWidth() / 1.1, btn.getPrefHeight() / 1.1);
                btn.setLayoutX(btn.getLayoutX() - differentX/2);
                btn.setLayoutY(btn.getLayoutY() - differentY/2);
            }
            else btn.setStyle(btn.getStyle() + "-fx-background-color: rgb(100, 50, 0);");
        });
        btn.setLayoutX(Constants.width/5.65);
        btn.setLayoutY(Constants.height/1.12);
        btn.setOnAction(event -> {
            if (selectedCards.size() == 6) new GameUI(stage, selectedCards);
        });
        return btn;
    }

    private ImageView addZombie(){
        Random rdm = new Random();
        ImageView image = new ImageView();
        switch (rdm.nextInt(4)){
            case 0 -> image.setImage(new Image("file:Pictures/ZombieGif/OriginalZombie.gif"));
            case 1 -> image.setImage(new Image("file:Pictures/ZombieGif/ConeheadZombie.gif"));
            case 2 -> image.setImage(new Image("file:Pictures/ZombieGif/BucketheadZombie.gif"));
            case 3 -> image.setImage(new Image("file:Pictures/ZombieGif/Imp.gif"));
        }
        image.setFitHeight(Constants.ZOMBIE_PIC_HEIGHT);
        image.setFitWidth(Constants.ZOMBIE_PIC_WEIGHT);
        image.setLayoutX(Constants.width/1.8 + rdm.nextDouble(Constants.width/3));
        image.setLayoutY(rdm.nextDouble(Constants.height/1.5));
        return image;
    }


    private Button getCardButton(String plantName){
        Button btn = new Button();
        btn.setGraphic(Constants.setCard(plantName));
        btn.setStyle("-fx-background-color: transparent");
        btn.setOnAction(event -> {
            if(selectedCards.contains(plantName)) {
                selectedCards.remove(plantName);
                for (int i = 0; i < cardBar.getChildren().size(); i++) {
                    Button checkBtn = (Button)cardBar.getChildren().get(i);
                    ImageView imageView = (ImageView) checkBtn.getGraphic();
                    Image image = imageView.getImage();
                    if(((ImageView) btn.getGraphic()).getImage().equals(image)) {
                        cardBar.getChildren().remove(i);
                        break;
                    }
                }
            }
            else if(selectedCards.size() < 6) {
                selectedCards.add(plantName);
                Button btn2 = getCardButton(plantName);
                Image image = ((ImageView)btn.getGraphic()).getImage();
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(Constants.PLANT_CARD_WIDTH);
                imageView.setFitHeight(Constants.PLANT_CARD_HEIGHT);
                btn2.setGraphic(imageView);
                cardBar.getChildren().add(btn2);
            }
            else btn.setStyle("-fx-background-color: rgb(150, 0, 0);");
        });
        btn.setOnMouseEntered(event -> {
            ImageView imageView = (ImageView) btn.getGraphic();
            double differentX = imageView.getFitWidth() * 1.05 - imageView.getFitWidth();
            double differentY = imageView.getFitHeight() * 1.05 - imageView.getFitHeight();
            imageView.setFitWidth(imageView.getFitWidth() * 1.05);
            imageView.setFitHeight(imageView.getFitHeight() * 1.05);
            imageView.setLayoutX(imageView.getLayoutX() - differentX/2);
            imageView.setLayoutY(imageView.getLayoutY() - differentY/2);
        });
        btn.setOnMouseExited(event -> {
            ImageView imageView = (ImageView) btn.getGraphic();
            double differentX = imageView.getFitWidth() - imageView.getFitWidth() / 1.05;
            double differentY = imageView.getFitHeight() - imageView.getFitHeight() / 1.05;
            imageView.setFitWidth(imageView.getFitWidth() / 1.05);
            imageView.setFitHeight(imageView.getFitHeight() / 1.05);
            imageView.setLayoutX(imageView.getLayoutX() + differentX/2);
            imageView.setLayoutY(imageView.getLayoutY() + differentY/2);
            btn.setStyle("-fx-background-color: transparent;");
        });
        return btn;
    }

//    private Effect chooseCardEffect(){
//        ColorAdjust choose = new ColorAdjust();
//        choose.setBrightness(-0.5);
//        choose.setContrast(-0.3);
//        return choose;
//    }

    private Pane Pane(){
        Pane pane = new Pane();
        pane.getChildren().addFirst(Constants.setBackGround("MainMenu"));
        double firstX = Constants.width/1.97, firstY = Constants.height/8, diffY = Constants.height/5, diffX = Constants.height/100;
        ImageView adventure = initializeImageViews("Adventure", Constants.height/1.4, Constants.height/4.2);
        adventure.setOnMouseClicked(event -> chooseCardPage());
        adventure.setLayoutX(firstX);
        adventure.setLayoutY(firstY);

        ImageView newGame = initializeImageViews("NewGame", Constants.height/1.45, Constants.height/4.35);
        newGame.setOnMouseClicked(event -> chooseCardPage());
        newGame.setLayoutX(firstX - 2);
        newGame.setLayoutY(firstY + diffY);

        ImageView loadGame = initializeImageViews("LoadGame", Constants.height/1.6, Constants.height/4.8);
        loadGame.setOnMouseClicked(event -> load());
        loadGame.setLayoutX(firstX + 1.5*diffX);
        loadGame.setLayoutY(firstY + 1.85*diffY);

        ImageView quit = initializeImageViews("Quit", Constants.height/4.5, Constants.height/6);
        quit.setOnMouseClicked(event -> stage.close());
        quit.setLayoutX(Constants.width / 1.14);
        quit.setLayoutY(Constants.height / 1.405);

        ImageView help = initializeImageViews("help", Constants.height/4.5, Constants.height/4);
        help.setLayoutX(Constants.width / 1.28);
        help.setLayoutY(Constants.height / 1.54);

        ImageView options = initializeImageViews("option", Constants.height/3.5, Constants.height/5.4);
        options.setLayoutX(Constants.width / 1.475);
        options.setLayoutY(Constants.height / 1.47);
        pane.getChildren().addAll(adventure, newGame, loadGame, quit, options, help);
        return pane;
    }

    private ImageView initializeImageViews(String str, double width, double height){
        Image image = new Image("file:Pictures/ui/" + str + ".png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        imageView.setOnMouseEntered(event -> {
            double differentX = imageView.getFitWidth() * 1.05 - imageView.getFitWidth();
            double differentY = imageView.getFitHeight() * 1.05 - imageView.getFitHeight();
            imageView.setFitWidth(imageView.getFitWidth() * 1.05);
            imageView.setFitHeight(imageView.getFitHeight() * 1.05);
            imageView.setLayoutX(imageView.getLayoutX() - differentX/2);
            imageView.setLayoutY(imageView.getLayoutY() - differentY/2);
        });

        imageView.setOnMouseExited(event -> {
            double differentX = imageView.getFitWidth() - imageView.getFitWidth() / 1.05;
            double differentY = imageView.getFitHeight() - imageView.getFitHeight() / 1.05;
            imageView.setFitWidth(imageView.getFitWidth() / 1.05);
            imageView.setFitHeight(imageView.getFitHeight() / 1.05);
            imageView.setLayoutX(imageView.getLayoutX() + differentX/2);
            imageView.setLayoutY(imageView.getLayoutY() + differentY/2);
        });
        return imageView;
    }
}
