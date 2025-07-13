package main.plantsvszombies;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Fog {
    private Rectangle fogTiles[][];
    private Pane fogGrid;

    public Fog(Pane fogGrid) {
        this.fogGrid = fogGrid;
        fogTiles = new Rectangle[Constants.ROWS][Constants.COLS];
        int fogLength = (int)(Math.random() * 5 + 5);
        for(int row = 0; row < Constants.ROWS; row++) {
            for(int col = fogLength; col < Constants.COLS; col++) {
                fogTiles[row][col] = new Rectangle(
                        Constants.TILE_SIZE,
                        Constants.TILE_SIZE,
                        new Color(0.2, 0.2, 0.2, 0.7)
                );
                fogTiles[row][col].setLayoutX(Constants.BOARD_X + (col * Constants.TILE_SIZE));
                fogTiles[row][col].setLayoutY(Constants.BOARD_Y + (row * Constants.TILE_SIZE));
                fogGrid.getChildren().add(fogTiles[row][col]);
            }
        }

    }

    public void clearFogAt(int row, int col) {
        if (row >= 0 && row < 5 && col >= 0 && col < 9) {
            fogGrid.getChildren().remove(fogTiles[row][col]);
        }
    }

    public void clearAllFog() {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 9; col++) {
                if (fogTiles[row][col] != null) {
                    fogGrid.getChildren().remove(fogTiles[row][col]);
                }
            }
        }
    }
}
