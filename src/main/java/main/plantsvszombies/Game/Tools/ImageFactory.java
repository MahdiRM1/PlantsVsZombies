package main.plantsvszombies.Game.Tools;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.plantsvszombies.Enums.BulletType;
import main.plantsvszombies.Enums.SunType;

public class ImageFactory {

    private ImageFactory(){}

    public static ImageView createScoreBoardPicture() {
        return createImageView("ui/ChooserBackground.png",
                Constants.SCREEN_WIDTH / 1.87, Constants.SCREEN_HEIGHT / 5);
    }

    public static ImageView createBackGround(String str) {
        return createImageView("backGround/" + str + ".jpg",
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
    }

    public static ImageView createCard(String plantName) {
        return createImageView("plantPictures/" + plantName + "/Image.jpg",
                Constants.PLANT_CARD_WIDTH, Constants.PLANT_CARD_HEIGHT);
    }

    public static ImageView createSunPicture(SunType type) {
        ImageView sun = createImageView("ui/sun.png",
                Constants.SUN_SIZE, Constants.SUN_SIZE);
        if (type == SunType.BASE_FALLEN) setNodePosition(sun, Constants.BOARD_X + Constants.TILE_SIZE * Constants.COLS * Math.random(), 0);
        else setNodePosition(sun, Constants.BOARD_X + (type.getCol() * Constants.TILE_SIZE), Constants.BOARD_Y + (type.getRow() * Constants.TILE_SIZE));
        return sun;
    }

    public static ImageView createPlantPicture(String plantName, int row, int col) {
        ImageView plant = createImageView("plantPictures/" + plantName + "/normal/frame_0.png",
                Constants.TILE_SIZE * 0.8,
                plantName.equals("TallNut") ? Constants.TILE_SIZE * 1.2 : Constants.TILE_SIZE * 0.8);
        setNodePosition(plant, Constants.BOARD_X + (col * Constants.TILE_SIZE),
                plantName.equals("TallNut") ? (Constants.BOARD_Y) + (row * Constants.TILE_SIZE) : (Constants.BOARD_Y) + ((row+0.2) * Constants.TILE_SIZE));
        plant.setMouseTransparent(true);
        return plant;
    }

    public static ImageView createGravePicture(int row, int col, int i) {
        ImageView grave = createImageView("graves/" + i + ".png",
                Constants.TILE_SIZE * 0.8, Constants.TILE_SIZE * 0.8);
        setNodePosition(grave, Constants.BOARD_X + (col * Constants.TILE_SIZE), Constants.BOARD_Y + (row * Constants.TILE_SIZE));
        grave.setMouseTransparent(true);
        return grave;
    }

    public static ImageView createBulletPicture(int row, int col, BulletType bulletType) {
        ImageView bullet = createImageView("bullets/" + bulletType.toString() + ".png",
                Constants.BULLET_SIZE, Constants.BULLET_SIZE);
        setNodePosition(bullet, Constants.BOARD_X + ((col + 0.6) * Constants.TILE_SIZE),
                bulletType == BulletType.SHROOM_BULLET ?
                        Constants.BOARD_Y + ((row + 0.55) * Constants.TILE_SIZE) : Constants.BOARD_Y + ((row + 0.35) * Constants.TILE_SIZE));
        return bullet;
    }

    public static ImageView createFogPicture(int fogLength){
        double fogSize = Constants.TILE_SIZE * (Constants.ROWS + 1);
        ImageView picture = createImageView("ui/fog.png", fogSize, fogSize);
        setNodePosition(picture, (Constants.BOARD_X + fogLength * Constants.TILE_SIZE), Constants.BOARD_Y * 0.5);
        return picture;
    }

    public static void createZombiePicture(ImageView picture, ImageView headPicture, int row, int col) {
        setNodeSize(picture, Constants.ZOMBIE_PIC_WIDTH, Constants.ZOMBIE_PIC_HEIGHT);
        setNodeSize(headPicture, Constants.ZOMBIE_PIC_WIDTH, Constants.ZOMBIE_PIC_HEIGHT);
        setNodePosition(picture, Constants.BOARD_X + ((col - 0.5) * Constants.TILE_SIZE), Constants.BOARD_Y + ((row - 0.5) * Constants.TILE_SIZE));
        setNodePosition(headPicture, picture.getLayoutX(), picture.getLayoutY());
        changeScale(headPicture, 1.2);
    }

    public static Image[] arrayImage(String str, int max) {
        Image[] images = new Image[max];
        for (int i = 0; i < max; i++) images[i] = new Image(ImageFactory.class.getResource("/Pictures/" + str + i + ".png").toExternalForm());
        return images;
    }

    // add shovel image
    public static ImageView shovelImage() {
        ImageView shovel = createButton("shovel", Constants.SCREEN_WIDTH / 19, Constants.SCREEN_HEIGHT / 10);
        shovel.setMouseTransparent(true);
        setNodePosition(shovel, Constants.SCREEN_WIDTH / 2.1, 0);
        return shovel;
    }

    private static ImageView createImageView(String path, double width, double height) {
        ImageView imageView = new ImageView(new Image(ImageFactory.class.getResource("/Pictures/" + path).toExternalForm()));
        setNodeSize(imageView, width, height);
        return imageView;
    }

    public static void changeScale(Node node, double resize) {
        node.setScaleX(resize);
        node.setScaleY(resize);
        node.setScaleZ(resize);
    }

    public static void setNodePosition(Node node, double x, double y) {
        node.setLayoutX(x);
        node.setLayoutY(y);
    }

    public static void setNodeSize(Node node, double width, double height){
        switch (node){
            case Button btn -> btn.setPrefSize(width, height);
            case Label label -> label.setPrefSize(width, height);
            case ImageView imageView -> {
                imageView.setFitWidth(width);
                imageView.setFitHeight(height);
            }
            default -> {}
        }
    }

    // generate buttons -> visuals
    public static ImageView createButton(String text, double width, double height) {
        ImageView imageView = new ImageView(new Image(ImageFactory.class.getResource("/Pictures/ui/" + text + ".png").toExternalForm()));
        setNodeSize(imageView, width, height);
        imageView.setOnMouseEntered(e -> {
            SoundManager.playHover();
            changeScale(imageView, 1.05);
        });
        imageView.setOnMouseExited(e -> changeScale(imageView, 1));
        return imageView;
    }
}
