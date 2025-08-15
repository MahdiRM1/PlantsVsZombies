package main.plantsvszombies.Game.Tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import main.plantsvszombies.Enums.SunType;
import main.plantsvszombies.GameState.GameState;
import main.plantsvszombies.Plants.BombPlants.CherryBomb;
import main.plantsvszombies.Plants.BombPlants.DoomShroom;
import main.plantsvszombies.Plants.BombPlants.HypnoShroom;
import main.plantsvszombies.Plants.BombPlants.IceShroom;
import main.plantsvszombies.Plants.BombPlants.Jalapeno;
import main.plantsvszombies.Plants.BombPlants.PotatoMine;
import main.plantsvszombies.Plants.NutPlants.TallNut;
import main.plantsvszombies.Plants.NutPlants.WallNut;
import main.plantsvszombies.Plants.OtherPlants.Blover;
import main.plantsvszombies.Plants.OtherPlants.Plantern;
import main.plantsvszombies.Plants.OtherPlants.SunFlower;
import main.plantsvszombies.Plants.PeaPlants.PeaShooter;
import main.plantsvszombies.Plants.PeaPlants.PuffShroom;
import main.plantsvszombies.Plants.PeaPlants.Repeater;
import main.plantsvszombies.Plants.PeaPlants.ScaredyShroom;
import main.plantsvszombies.Plants.PeaPlants.SnowPea;
import main.plantsvszombies.Plants.Plant;

public class Utils {

    private Utils() {}

    public static double sunMaxY(SunType type) {
        return (type == SunType.BASE_FALLEN)
                ? Constants.BOARD_Y + (Constants.TILE_SIZE * 5 * Math.random())
                : Constants.BOARD_Y + (type.getRow() * Constants.TILE_SIZE) + (Constants.SCREEN_HEIGHT / 20);
    }

    public static Effect effect(double hue, double saturation, double brightness, double contrast) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(hue);
        colorAdjust.setSaturation(saturation);
        colorAdjust.setBrightness(brightness);
        colorAdjust.setContrast(contrast);
        return colorAdjust;
    }

    public static Pane createMenu(){
        Pane menuPane = new Pane();

        ImageView optionImg = new ImageView(new Image(Utils.class.getResource("/Pictures/ui/optionPic.png").toExternalForm()));
        ImageFactory.setNodePosition(optionImg, Constants.SCREEN_WIDTH / 3.3, Constants.SCREEN_HEIGHT / 10);
        ImageFactory.setNodeSize(optionImg, Constants.SCREEN_WIDTH / 2.5, Constants.SCREEN_HEIGHT / 1.25);

        Slider music = new Slider(0, 1, SoundManager.music);
        styleSlider(music);
        music.setLayoutY(Constants.SCREEN_HEIGHT / 3);
        music.valueProperty().addListener((obs, oldVal, newVal)
                -> SoundManager.music = newVal.doubleValue());
        Label musicLabel = sliderLabel("MUSIC", music);

        Slider volume = new Slider(0, 1, SoundManager.volume);
        styleSlider(volume);
        volume.setLayoutY(Constants.SCREEN_HEIGHT / 3 + Constants.SCREEN_HEIGHT / 10);
        volume.valueProperty().addListener((obs, oldVal, newVal)
                -> SoundManager.volume = newVal.doubleValue());
        Label volumeLabel = sliderLabel("VOLUME", volume);

        menuPane.getChildren().addAll(optionImg, music, musicLabel, volume, volumeLabel);
        return menuPane;
    }

    private static void styleSlider(Slider slider){
        slider.setPrefWidth(Constants.SCREEN_WIDTH / 5);
        slider.setLayoutX(Constants.SCREEN_WIDTH / 2.3);
        slider.getStyleClass().add("slider");
    }

    private static Label sliderLabel(String str, Slider slider){
        Label label = new Label(str);
        label.setLayoutX(Constants.SCREEN_WIDTH / 2.75);
        label.setLayoutY(slider.getLayoutY());
        Font font = Font.loadFont(SoundManager.class.getResource("/fonts/BreakdownPG.otf").toExternalForm(), Constants.SCREEN_HEIGHT/27);
        label.setFont(font);
        label.setStyle("-fx-text-fill: rgb(25, 25, 25);");
        return label;
    }

    public static boolean checkCollision(double l1, double l2, int row1, int row2){
        return checkCollision(Constants.TILE_SIZE/4, l1, l2, row1, row2);
    }

    public static boolean checkCollision(double bound, double l1, double l2, int row1, int row2){
        return Math.abs(l1- l2) <= bound && row1 == row2;
    }

    public static int getColumnZombie(double layoutX) {
        double relativeX = layoutX - Constants.BOARD_X + Constants.ZOMBIE_PIC_WIDTH / 8;
        return relativeX > -Constants.ZOMBIE_PIC_WIDTH / 4 ?  (int) (relativeX / Constants.TILE_SIZE): -1;
    }

    public static Plant buildPlant(int row, int col, String selectedPlant, boolean isSleep) {
        return switch (selectedPlant) {
            case "PeaShooter" -> new PeaShooter(row, col);
            case "SunFlower" -> new SunFlower(row, col);
            case "WallNut" -> new WallNut(row, col);
            case "TallNut" -> new TallNut(row, col);
            case "Repeater" -> new Repeater(row, col);
            case "SnowPea" -> new SnowPea(row, col);
            case "CherryBomb" -> new CherryBomb(row, col);
            case "Jalapeno" -> new Jalapeno(row, col);
            case "PotatoMine" -> new PotatoMine(row, col);
            case "Plantern" -> new Plantern(row, col);
            case "Blover" -> new Blover(row, col);
            case "HypnoShroom" -> new HypnoShroom(row, col, isSleep);
            case "PuffShroom" -> new PuffShroom(row, col, isSleep);
            case "ScaredyShroom" -> new ScaredyShroom(row, col, isSleep);
            case "IceShroom" -> new IceShroom(row, col, isSleep);
            case "DoomShroom" -> new DoomShroom(row, col, isSleep);
            default -> null;
        };
    }

    // deletes the save data if the game is finished
    public static void deleteSaveData() {
        Path path = Paths.get("savegame.dat");
        try {
            Files.delete(path);
            System.out.println("save data deleted");
        } catch (IOException e) {
            System.out.println("cant delete save data");
        }
    }

    public static void writeState(GameState state){
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("savegame.dat"))) {
            out.writeObject(state);
            System.out.println("Game saved");
        } catch (IOException e) {
            System.out.println("cant save data");
        }
    }

    public static GameState readState(){
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("savegame.dat"))) {
            GameState state = (GameState) input.readObject();
            System.out.println("game loaded");
            return state;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("cant load data");
        }
        return null;
    }

    public static boolean dataExists(){
        File file = new File("savegame.dat");
        return file.exists();
    }

    public static Button createMenuButton(String text, double width, double height) {
        Button btn = new Button(text);
        Font font = Font.loadFont(SoundManager.class.getResource("/fonts/Coold.ttf").toExternalForm(), Constants.SCREEN_HEIGHT/36);
        btn.setFont(font);
        btn.getStyleClass().add("menuBtn");
        ImageFactory.setNodeSize(btn, width, height);
        btn.setOnMouseEntered(e -> ImageFactory.changeScale(btn, 1.1));
        btn.setOnMouseExited(e -> ImageFactory.changeScale(btn, 1));
        return btn;
    }

    public static Button createButton(String text, double width, double height) {
        Button btn = new Button(text);
        Font font = Font.loadFont(SoundManager.class.getResource("/fonts/BreakdownPG.otf").toExternalForm(), Constants.SCREEN_HEIGHT/30);
        btn.setFont(font);
        btn.getStyleClass().add("btns");
        ImageFactory.setNodeSize(btn, width, height);
        btn.setOnMouseEntered(e -> ImageFactory.changeScale(btn, 1.1));
        btn.setOnMouseExited(e -> ImageFactory.changeScale(btn, 1));
        return btn;
    }

    public static Button submitButton(String text) {
        Button btn = new Button(text);
        Font font = Font.loadFont(SoundManager.class.getResource("/fonts/BreakdownPG.otf").toExternalForm(), Constants.SCREEN_HEIGHT/14.4);
        btn.setFont(font);
        btn.getStyleClass().add("submitBtn");
        ImageFactory.setNodeSize(btn, Constants.SCREEN_WIDTH / 3, Constants.SCREEN_HEIGHT / 7);
        ImageFactory.setNodePosition(btn, Constants.SCREEN_WIDTH / 2.96, Constants.SCREEN_HEIGHT / 1.36);
        btn.setOnMouseEntered(e -> ImageFactory.changeScale(btn, 1.05));
        btn.setOnMouseExited(e -> ImageFactory.changeScale(btn, 1));
        return btn;
    }
}
