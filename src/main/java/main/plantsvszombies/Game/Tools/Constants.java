package main.plantsvszombies.Game.Tools;

import javafx.stage.Screen;

public final class Constants {

    private Constants() {}

    public static long gameTime = 0;

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
}
