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
    public static final double PLANT_CARD_WIDTH = height / 13.5;
    public static final double SUN_SIZE = height / 8.5;

    public static ImageView setScoreBoardPicture(){
        ImageView board = new ImageView(new Image("file:Pictures/ScoreBoard/ChooserBackground.png"));
        board.setFitWidth(height/1.1);
        board.setFitHeight(height/5.5);
        board.setLayoutY(height/29.25);
        return board;
    }

    public static ImageView setIntroductionBackGround(){
        ImageView bg = new ImageView(new Image("file:Pictures/backGround/GameStartBackGround.jpg"));
        bg.setFitHeight(height);
        bg.setFitWidth(width);
        return bg;
    }

    public static ImageView setDayBackGround(){
        ImageView bg = new ImageView(new Image("file:Pictures/backGround/backGroundDay.jpg"));
        bg.setFitHeight(height);
        bg.setFitWidth(width);
        return bg;
    }

    public static ImageView setCard(String plantName){
        ImageView picture = new ImageView(new Image("file:Pictures/plantPictures/dayTime/" + plantName + "Image.jpg"));
        picture.setFitWidth(PLANT_CARD_WIDTH);
        picture.setFitHeight(PLANT_CARD_HEIGHT);
        return picture;
    }
    
    public static ImageView setSunPicture(SunType type){
        ImageView picture = new ImageView(new Image("file:Pictures/sun/sun.png"));
        picture.setFitWidth(SUN_SIZE);
        picture.setFitHeight(SUN_SIZE);
        if(type == SunType.FALLEN){
            picture.setLayoutX(height / 2.62 + TILE_SIZE * 9 * Math.random());
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
        ImageView picture = new ImageView(new Image("file:Pictures/plantsGifs/DayTime/" + plantName + ".gif"));
        picture.setFitWidth(TILE_SIZE * 0.8);
        picture.setLayoutX((height / 2.5) + (col * TILE_SIZE));
        if(plantName.equals("TallNut")) {
            picture.setFitHeight(TILE_SIZE * 1.2);
            picture.setLayoutY((height / 4.1) + ((row - 0.5) * TILE_SIZE));
        }
        else {
            picture.setLayoutY((height / 4.3) + (row * TILE_SIZE));
            picture.setFitHeight(TILE_SIZE * 0.8);
        }
        return picture;
    }

    public static ImageView setBulletPicture(int row, int col){
        ImageView picture = new ImageView(new Image("file:Pictures/bullets/normalBullet.png"));
        picture.setFitWidth(BULLET_SIZE);
        picture.setFitHeight(BULLET_SIZE);
        picture.setLayoutX((col*TILE_SIZE) + height/2.1);
        picture.setLayoutY(height - ((6-row)*TILE_SIZE) + (height/10.85));
        return picture;
    }

    public static void setZombiePicture(ImageView picture, int row){
        picture.setFitWidth(ZOMBIE_PIC_WEIGHT);
        picture.setFitHeight(ZOMBIE_PIC_HEIGHT);
        picture.setLayoutY(height - picture.getFitHeight() - ((4-row) * TILE_SIZE) - (height/12.8));
        picture.setLayoutX(width);
    }

    public static int getColumnZombie(ImageView picture){
        return (int)((picture.getLayoutX() + (picture.getFitWidth() / 1.5) - height/2.62) / TILE_SIZE);
    }
}