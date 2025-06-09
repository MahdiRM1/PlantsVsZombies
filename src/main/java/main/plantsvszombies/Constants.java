package main.plantsvszombies;

import javafx.stage.Screen;

public final class Constants {
    public static final double width = Screen.getPrimary().getVisualBounds().getWidth();
    public static final double height = Screen.getPrimary().getVisualBounds().getHeight();
    public static final double TILE_HEIGHT = height/7;
    public static final double TILE_WIDTH = width/10;
    public static final double ZOMBIE_PIC_HEIGHT = TILE_HEIGHT * 1.8;
    public static final double ZOMBIE_PIC_WEIGHT = TILE_WIDTH * 1.2;
    public static final double BULLET_SIZE = TILE_HEIGHT/3;
}