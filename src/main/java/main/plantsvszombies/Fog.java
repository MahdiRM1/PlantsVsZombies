package main.plantsvszombies;

import java.util.Random;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Fog {

    private final Rectangle[][] fogTiles;
    private final int fogLength;
    private long bloverTime;

    public Fog(Pane fogGrid) {
        fogTiles = new Rectangle[Constants.ROWS][Constants.COLS + 1];
        fogLength = (new Random().nextInt(3) + 5);
        for (int row = 0; row < Constants.ROWS; row++) {
            for (int col = fogLength; col < Constants.COLS + 1; col++) {
                fogTiles[row][col] = fogTile(row, col);
                fogGrid.getChildren().add(fogTiles[row][col]);
            }
        }
    }

    private Rectangle fogTile(int row, int col) {
        Rectangle rect = new Rectangle(
                Constants.TILE_SIZE,
                Constants.TILE_SIZE,
                new Color(0.4, 0.4, 0.4, 1)
        );
        rect.setLayoutX(Constants.BOARD_X + (col * Constants.TILE_SIZE));
        rect.setLayoutY(Constants.BOARD_Y + (row * Constants.TILE_SIZE));
        return rect;
    }

    private void invisibleRect(Rectangle rect) {
        Color color = new Color(1, 1, 1, 0);
        rect.setFill(color);
    }

    private void visibleRect(Rectangle rect) {
        Color color = new Color(0.4, 0.4, 0.4, 1);
        rect.setFill(color);
    }

    public void clearFog(int centerRow, int centerCol) {
        for (int r = centerRow - 1; r <= centerRow + 1; r++)
            for (int c = centerCol - 1; c <= centerCol + 1; c++)
                clearFogAt(r, c);
    }

    public void clearFog() {
        for (int row = 0; row < Constants.ROWS; row++)
            for (int col = fogLength; col < Constants.COLS + 1; col++)
                clearFogAt(row, col);
    }

    public void clearFogAt(int row, int col) {
        if (row >= 0 && row < Constants.ROWS && col >= 0 && col < Constants.COLS + 1 && fogTiles[row][col] != null)
            invisibleRect(fogTiles[row][col]);
    }

    public void updateFog() {
        if (Math.abs(GlobalState.gameTime - bloverTime) <= 10000) return;

        for (int row = 0; row < Constants.ROWS; row++)
            for (int col = fogLength; col < Constants.COLS + 1; col++)
                visibleRect(fogTiles[row][col]);
    }

    public void setBloverTime(long time) {
        bloverTime = time;
    }
}
