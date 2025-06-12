package main.plantsvszombies;

import javafx.stage.Screen;

public final class Constants {
    public static final double width = Screen.getPrimary().getVisualBounds().getWidth();
    public static final double height = Screen.getPrimary().getVisualBounds().getHeight();
    public static final double TILE_SIZE = height/7.1;
    public static final double ZOMBIE_PIC_HEIGHT = TILE_SIZE * 1.8;
    public static final double ZOMBIE_PIC_WEIGHT = TILE_SIZE * 1.5;
    public static final double BULLET_SIZE = TILE_SIZE/3;
    public static final double PLANT_CARD_HEIGHT = height/7.3;
    public static final double PLANT_CARD_WIDTH = height/13.5;
    public static final double SUN_SIZE = TILE_SIZE/1.2;
}