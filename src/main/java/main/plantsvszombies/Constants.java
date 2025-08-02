package main.plantsvszombies;

import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.stage.Screen;

public final class Constants {

    public static final double SCREEN_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    public static final double SCREEN_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();

    public static final double TILE_SIZE = SCREEN_HEIGHT / 7.1;
    public static final double ZOMBIE_PIC_HEIGHT = SCREEN_HEIGHT / 4.2;
    public static final double ZOMBIE_PIC_WIDTH = SCREEN_WIDTH / 10;
    public static final double BULLET_SIZE = SCREEN_HEIGHT / 24.5;
    public static final double PLANT_CARD_HEIGHT = SCREEN_HEIGHT / 7.3;
    public static final double PLANT_CARD_WIDTH = SCREEN_WIDTH / 20.6;
    public static final double SUN_SIZE = SCREEN_HEIGHT / 8.5;

    public static final int ROWS = 5;
    public static final int COLS = 9;

    public static final double BOARD_X = SCREEN_WIDTH / 4.7;
    public static final double BOARD_Y = SCREEN_HEIGHT / 4.9;
    public static final double CARD_BAR_X = SCREEN_WIDTH / 9.75;
    public static final double CARD_BAR_Y = SCREEN_HEIGHT / 50;

    public static ImageView setScoreBoardPicture() {
        return createImageView("Pictures/ui/ChooserBackground.png",
                SCREEN_WIDTH / 1.87, SCREEN_HEIGHT / 5);
    }

    public static ImageView setBackGround(String str) {
        return createImageView("Pictures/backGround/" + str + ".jpg",
                SCREEN_WIDTH, SCREEN_HEIGHT - 35);
    }

    public static ImageView setCard(String plantName) {
        return createImageView("Pictures/plantPictures/" + plantName + "/Image.jpg",
                PLANT_CARD_WIDTH, PLANT_CARD_HEIGHT);
    }

    public static ImageView setSunPicture(SunType type) {
        ImageView sun = createImageView("Pictures/ui/sun.png",
                SUN_SIZE, SUN_SIZE);
        if (type == SunType.BASE_FALLEN) positionNode(sun, BOARD_X + TILE_SIZE * COLS * Math.random(), 0);
        else positionNode(sun, BOARD_X + (type.getCol() * TILE_SIZE), BOARD_Y + (type.getRow() * TILE_SIZE));
        return sun;
    }

    public static double sunMaxY(SunType type) {
        return (type == SunType.BASE_FALLEN)
                ? BOARD_Y + (TILE_SIZE * 5 * Math.random())
                : BOARD_Y + (type.getRow() * TILE_SIZE) + (SCREEN_HEIGHT / 20);
    }

    public static ImageView setPlantPicture(String plantName, int row, int col) {
        ImageView plant = createImageView("Pictures/plantPictures/" + plantName + "/frame_0.png",
                TILE_SIZE * 0.8,
                plantName.equals("TallNut") ? TILE_SIZE * 1.2 : TILE_SIZE * 0.8);
        positionNode(plant, BOARD_X + (col * TILE_SIZE),
                plantName.equals("TallNut") ? (BOARD_Y) + ((row - 0.5) * TILE_SIZE) : (BOARD_Y) + (row * TILE_SIZE));
        plant.setMouseTransparent(true);
        return plant;
    }

    public static ImageView setGravePicture(int row, int col, int i) {
        ImageView grave = createImageView("Pictures/graves/" + i + ".png",
                TILE_SIZE * 0.8, TILE_SIZE * 0.8);
        positionNode(grave, BOARD_X + (col * TILE_SIZE), BOARD_Y + (row * TILE_SIZE));
        grave.setMouseTransparent(true);
        return grave;
    }

    public static ImageView setBulletPicture(int row, int col, BulletType bulletType) {
        ImageView bullet = createImageView("Pictures/bullets/" + bulletType.toString() + ".png",
                BULLET_SIZE, BULLET_SIZE);
        positionNode(bullet, BOARD_X + ((col + 0.6) * TILE_SIZE),
                bulletType == BulletType.SHROOM_BULLET ?
                        BOARD_Y + ((row + 0.35) * TILE_SIZE) : BOARD_Y + ((row + 0.15) * TILE_SIZE));
        return bullet;
    }

    public static ImageView setFogPicture(int fogLength){
        double fogSize = TILE_SIZE * (ROWS + 1);
        ImageView picture = createImageView("Pictures/ui/fog.png", fogSize, fogSize);
        positionNode(picture, (Constants.BOARD_X + fogLength * Constants.TILE_SIZE), Constants.BOARD_Y * 0.5);
        return picture;
    }

    public static void setZombiePicture(ImageView picture, ImageView headPicture, int row, int col) {
        sizeNode(picture, ZOMBIE_PIC_WIDTH, ZOMBIE_PIC_HEIGHT);
        sizeNode(headPicture, ZOMBIE_PIC_WIDTH, ZOMBIE_PIC_HEIGHT);
        positionNode(picture, BOARD_X + ((col - 0.5) * TILE_SIZE), BOARD_Y + ((row - 0.8) * TILE_SIZE));
        positionNode(headPicture, BOARD_X + ((col - 0.5) * TILE_SIZE), BOARD_Y + ((row - 0.8) * TILE_SIZE));
        changeScale(headPicture, 1.2);
    }

    public static boolean aliveZombie(Zombie zombie){
        return zombie.getState() != ZombieState.DIE && zombie.getState() != ZombieState.BOOM_DIE &&
                zombie.getState() != ZombieState.DEAD;
    }

    public static boolean checkCollision(double l1, double l2, int row1, int row2){
        return checkCollision(TILE_SIZE/4, l1, l2, row1, row2);
    }

    public static boolean checkCollision(double bound, double l1, double l2, int row1, int row2){
        return Math.abs(l1- l2) <= bound && row1 == row2;
    }

    public static int getColumnZombie(double layoutX) {
        double relativeX = layoutX - BOARD_X + ZOMBIE_PIC_WIDTH / 8;
        return relativeX > -ZOMBIE_PIC_WIDTH / 4 ?  (int) (relativeX / TILE_SIZE): -1;
    }

    public static Image[] getArrayImage(String path, int max) {
        Image[] images = new Image[max];
        for (int i = 0; i < max; i++) images[i] = new Image("file:" + path + i + ".png");
        return images;
    }

    public static AudioClip setSound(String name, boolean repeat){
        AudioClip sound = new AudioClip("file:Audio/" + name + ".mp3");
        if (repeat) sound.setCycleCount(Timeline.INDEFINITE);
        sound.setVolume(GlobalState.volume);
        return sound;
    }

    private static ImageView createImageView(String path, double width, double height) {
        ImageView imageView = new ImageView(new Image("file:" + path));
        sizeNode(imageView, width, height);
        return imageView;
    }

    public static void changeScale(Node node, double resize) {
        node.setScaleX(resize);
        node.setScaleY(resize);
        node.setScaleZ(resize);
    }


    public static void sizeNode(Node node, double width, double height){
        switch (node){
            case Button btn -> btn.setPrefSize(width, height);
            case ImageView imageView -> {
                imageView.setFitWidth(width);
                imageView.setFitHeight(height);
            }
            default -> {}
        }
    }

    public static void positionNode(Node node, double x, double y) {
        node.setLayoutX(x);
        node.setLayoutY(y);
    }

    public static Effect effect(double hue, double saturation, double brightness, double contrast) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(hue);
        colorAdjust.setSaturation(saturation);
        colorAdjust.setBrightness(brightness);
        colorAdjust.setContrast(contrast);
        return colorAdjust;
    }

    public static Plant getPlant(int row, int col, String selectedPlant, boolean isSleep) {
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
}
