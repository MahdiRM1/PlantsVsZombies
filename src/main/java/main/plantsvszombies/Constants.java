package main.plantsvszombies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;

public final class Constants {
    public static final double width = Screen.getPrimary().getVisualBounds().getWidth();
    public static final double height = Screen.getPrimary().getVisualBounds().getHeight();
    public static final double TILE_SIZE = height / 7.1;
    public static final double ZOMBIE_PIC_HEIGHT = height / 4;
    public static final double ZOMBIE_PIC_WEIGHT = height / 5;
    public static final double BULLET_SIZE = height / 21.5;
    public static final double PLANT_CARD_HEIGHT = height / 7.3;
    public static final double PLANT_CARD_WIDTH = height / 11;
    public static final double SUN_SIZE = height / 8.5;
    public static final int ROWS = 5;
    public static final int COLS = 9;

    public static ImageView setScoreBoardPicture(){
        ImageView board = new ImageView(new Image("file:Pictures/ScoreBoard/ChooserBackground.png"));
        board.setFitWidth(height);
        board.setFitHeight(height/5);
        return board;
    }

    public static ImageView setBackGround(String str){
        ImageView bg = new ImageView(new Image("file:Pictures/backGround/" + str + ".jpg"));
        bg.setFitHeight(height - 35);
        bg.setFitWidth(width);
        return bg;
    }

    public static ImageView setCard(String plantName){
        ImageView picture = new ImageView(new Image("file:Pictures/plantPictures/" + plantName + "Image.jpg"));
        picture.setFitWidth(PLANT_CARD_WIDTH);
        picture.setFitHeight(PLANT_CARD_HEIGHT);
        return picture;
    }

    public static ImageView setSunPicture(SunType type){
        ImageView picture = new ImageView(new Image("file:Pictures/sun/sun.png"));
        picture.setFitWidth(SUN_SIZE);
        picture.setFitHeight(SUN_SIZE);
        if(type == SunType.FALLEN){
            picture.setLayoutX(height / 2.62 + TILE_SIZE * COLS * Math.random());
            picture.setLayoutY(0);
        }
        else {
            picture.setLayoutX(type.getCol() * (TILE_SIZE + 5) + (height / 2.6));
            picture.setLayoutY(height - ((6 - type.getRow()) * TILE_SIZE) + (height / 14));
        }
        return picture;
    }

    public static double sunMaxY(SunType type){
        if(type == SunType.FALLEN) return height - (height/12.8) - (TILE_SIZE * 5 * Math.random());
        else return height - ((6 - type.getRow()) * TILE_SIZE) + (height / 8);
    }

    public static ImageView setPlantPicture(String plantName, int row, int col){
        ImageView picture = new ImageView(new Image("file:Pictures/plantsGifs/" + plantName + ".gif"));
        picture.setFitWidth(TILE_SIZE * 0.8);
        picture.setLayoutX((height / 2.5) + (col * TILE_SIZE));
        if(plantName.equals("TallNut")) {
            picture.setFitHeight(TILE_SIZE * 1.2);
            picture.setLayoutY((height / 4.5) + ((row - 0.5) * TILE_SIZE));
        }
        else {
            picture.setLayoutY((height / 4.5) + (row * TILE_SIZE));
            picture.setFitHeight(TILE_SIZE * 0.8);
        }
        return picture;
    }

    public static ImageView setBulletPicture(int row, int col, BulletType bulletType){
        ImageView picture = new ImageView(new Image("file:Pictures/bullets/" + bulletType.toString() + ".png"));
        picture.setFitWidth(BULLET_SIZE);
        picture.setFitHeight(BULLET_SIZE);
        picture.setLayoutX((col*TILE_SIZE) + height/2.1);
        if (bulletType == BulletType.SHROOM_BULLET) picture.setLayoutY(height - ((ROWS-row) * TILE_SIZE)- (height/35));
        else picture.setLayoutY(height - ((ROWS-row) * TILE_SIZE) - (height/15));
        return picture;
    }

    public static void setZombiePicture(ImageView picture, int row){
        picture.setFitWidth(ZOMBIE_PIC_WEIGHT);
        picture.setFitHeight(ZOMBIE_PIC_HEIGHT);
        picture.setLayoutY(height - picture.getFitHeight() - ((4-row) * TILE_SIZE) - (height/10));
        picture.setLayoutX(width);
    }

    public static int getColumnZombie(ImageView picture){
        double gridStartX = height / 2.5;
        double relativeX = picture.getLayoutX() + picture.getFitWidth() / 1.5 - gridStartX;
        if(relativeX / TILE_SIZE > 0) return (int)(relativeX / TILE_SIZE);
        else return -1;

    }
}