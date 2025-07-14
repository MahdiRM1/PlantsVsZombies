package main.plantsvszombies;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;

public final class Constants {

    public static final double SCREEN_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
    public static final double SCREEN_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();

    public static final double TILE_SIZE = SCREEN_HEIGHT / 7.1;
    public static final double ZOMBIE_PIC_HEIGHT = SCREEN_HEIGHT / 4;
    public static final double ZOMBIE_PIC_WEIGHT = SCREEN_WIDTH / 9.4;
    public static final double BULLET_SIZE = SCREEN_HEIGHT / 24.5;
    public static final double PLANT_CARD_HEIGHT = SCREEN_HEIGHT / 7.3;
    public static final double PLANT_CARD_WIDTH = SCREEN_WIDTH / 20.6;
    public static final double SUN_SIZE = SCREEN_HEIGHT / 8.5;

    public static final int ROWS = 5;
    public static final int COLS = 9;

    public static final double BOARD_X = SCREEN_WIDTH / 4.6;
    public static final double BOARD_Y = SCREEN_HEIGHT / 4.5;
    public static final double CARD_BAR_X = SCREEN_WIDTH / 9.75;
    public static final double CARD_BAR_Y = SCREEN_HEIGHT / 50;

    public static ImageView setScoreBoardPicture() {
        return createImageView("Pictures/ScoreBoard/ChooserBackground.png",
                SCREEN_WIDTH / 1.87, SCREEN_HEIGHT / 5);
    }

    public static ImageView setBackGround(String str) {
        return createImageView("Pictures/backGround/" + str + ".jpg",
                SCREEN_WIDTH, SCREEN_HEIGHT - 35);
    }

    public static ImageView setCard(String plantName) {
        return createImageView("Pictures/plantPictures/" + plantName + "Image.jpg",
                PLANT_CARD_WIDTH, PLANT_CARD_HEIGHT);
    }

    public static ImageView setSunPicture(SunType type) {
        ImageView sun = createImageView("Pictures/sun/sun.png",
                SUN_SIZE, SUN_SIZE);
        if (type == SunType.BASE_FALLEN) {
            sun.setLayoutX(BOARD_X + TILE_SIZE * COLS * Math.random());
            sun.setLayoutY(0);
        } else {
            sun.setLayoutX(type.getCol() * (TILE_SIZE + 5) + BOARD_X);
            sun.setLayoutY(BOARD_Y + (type.getRow() * TILE_SIZE));
        }
        return sun;
    }

    public static double sunMaxY(SunType type) {
        return (type == SunType.BASE_FALLEN)
                ? BOARD_Y + (TILE_SIZE * 5 * Math.random())
                : BOARD_Y + (type.getRow() * TILE_SIZE) + (SCREEN_HEIGHT / 20);
    }

    public static ImageView setPlantPicture(String plantName, int row, int col) {
        ImageView plant = createImageView("Pictures/plantsGifs/" + plantName + ".gif",
                TILE_SIZE * 0.8,
                plantName.equals("TallNut") ? TILE_SIZE * 1.2 : TILE_SIZE * 0.8);
        plant.setLayoutX(BOARD_X + (col * TILE_SIZE));
        plant.setLayoutY(plantName.equals("TallNut")
                ? (BOARD_Y) + ((row - 0.5) * TILE_SIZE)
                : (BOARD_Y) + (row * TILE_SIZE));
        plant.setMouseTransparent(true);
        return plant;
    }

    public static ImageView setGravePicture(int row, int col, int i) {
        ImageView grave = createImageView("Pictures/graves/" + i + ".png",
                TILE_SIZE * 0.8, TILE_SIZE * 0.8);
        grave.setLayoutX(BOARD_X + (col * TILE_SIZE));
        grave.setLayoutY(BOARD_Y + (row * TILE_SIZE));
        grave.setMouseTransparent(true);
        return grave;
    }

    public static ImageView setBulletPicture(int row, int col, BulletType bulletType) {
        ImageView bullet = createImageView("Pictures/bullets/" + bulletType.toString() + ".png",
                BULLET_SIZE, BULLET_SIZE);
        bullet.setLayoutX((col * TILE_SIZE) + SCREEN_WIDTH / 3.93);
        bullet.setLayoutY(bulletType == BulletType.SHROOM_BULLET
                ? BOARD_Y + ((row + 0.35) * TILE_SIZE)
                : BOARD_Y + ((row + 0.15) * TILE_SIZE));
        return bullet;
    }

    public static void setZombiePicture(ImageView picture, int row, int col) {
        picture.setFitWidth(ZOMBIE_PIC_WEIGHT);
        picture.setFitHeight(ZOMBIE_PIC_HEIGHT);
        picture.setLayoutY(SCREEN_HEIGHT - picture.getFitHeight() - ((4 - row) * TILE_SIZE) - (SCREEN_HEIGHT / 10));
        picture.setLayoutX(BOARD_X + (col - 0.5) * TILE_SIZE);
    }

    public static int getColumnZombie(ImageView picture) {
        double relativeX = picture.getLayoutX() + picture.getFitWidth() / 1.5 - BOARD_X;
        return relativeX > -ZOMBIE_PIC_WEIGHT / 4 ? (int) (relativeX / TILE_SIZE) : -1;
    }

    public static Image[] getArrayImage(String path, int max) {
        Image[] images = new Image[max];
        for (int i = 0; i < max; i++) {
            images[i] = new Image("file:" + path + i + ".png");
        }
        return images;
    }

    private static ImageView createImageView(String path, double width, double height) {
        ImageView imageView = new ImageView(new Image("file:" + path));
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }

    public static void changeScale(Node node, double resize) {
        double currentWidth = node.getBoundsInLocal().getWidth();
        double currentHeight = node.getBoundsInLocal().getHeight();

        double newWidth = currentWidth * resize;
        double newHeight = currentHeight * resize;

        if (node instanceof Button btn) {
            btn.setPrefSize(newWidth, newHeight);
        } else if (node instanceof ImageView imgView) {
            imgView.setFitWidth(newWidth);
            imgView.setFitHeight(newHeight);
        }

        node.setLayoutX(node.getLayoutX() - (newWidth - currentWidth) / 2);
        node.setLayoutY(node.getLayoutY() - (newHeight - currentHeight) / 2);
    }

    public static Plant getPlant(int row, int col, String selectedPlant, GameMode mode) {
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
            }
            case "IceShroom" -> {
                return new IceShroom(row, col, mode);
            }
            case "DoomShroom" -> {
                return new DoomShroom(row, col, mode);
            }
            case "Plantern" -> {
                return new Plantern(row, col);
            }
            case "Blover" -> {
                return new Blover(row, col);
            }

        }
        return null;
    }
}
