package main.plantsvszombies;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class Fog {
    private Rectangle fogTiles[][];
    private Pane fogGrid;
    private int fogLength;
    private boolean hasFog[][];

    public Fog(Pane fogGrid) {
        this.fogGrid = fogGrid;
        fogTiles = new Rectangle[Constants.ROWS][Constants.COLS];
        hasFog = new boolean[Constants.ROWS][Constants.COLS];
        fogLength = (int) (Math.random() * 5 + 5);
        for (int row = 0; row < Constants.ROWS; row++) {
            for (int col = fogLength; col < Constants.COLS; col++) {
                fogTiles[row][col] = new Rectangle(
                        Constants.TILE_SIZE,
                        Constants.TILE_SIZE,
                        new Color(0.2, 0.2, 0.2, 0.7)
                );
                fogTiles[row][col].setLayoutX(Constants.BOARD_X + (col * Constants.TILE_SIZE));
                fogTiles[row][col].setLayoutY(Constants.BOARD_Y + (row * Constants.TILE_SIZE));
                fogGrid.getChildren().add(fogTiles[row][col]);
                hasFog[row][col] = true;
            }
        }

    }

    public void clearFogAt(int row, int col, List<Zombie> zombies) {
        if (row >= 0 && row < Constants.ROWS && col >= 0 && col < Constants.COLS && fogTiles[row][col] != null) {
            fogGrid.getChildren().remove(fogTiles[row][col]);
            fogTiles[row][col] = null;
            hasFog[row][col] = false;

            for (Zombie zombie : zombies) {
                if (zombie.getRow() == row && zombie.getCol() == col) {
                    zombie.getPicture().setVisible(true);
                }
            }
        }
    }


        public void clearFogArea ( int centerRow, int centerCol, int radius, List<Zombie > zombies){
            for (int r = centerRow - radius; r <= centerRow + radius; r++) {
                for (int c = centerCol - radius; c <= centerCol + radius; c++) {
                    clearFogAt(r, c, zombies);
                }
            }
        }

        public void clearAllFog () {
            for (int row = 0; row < 5; row++) {
                for (int col = 0; col < 9; col++) {
                    if (fogTiles[row][col] != null) {
                        fogGrid.getChildren().remove(fogTiles[row][col]);
                        fogTiles[row][col] = null;
                        hasFog[row][col] = false;
                    }
                }
            }
        }

    public void restoreFog() {
        for (int row = 0; row < Constants.ROWS; row++) {
            for (int col = fogLength; col < Constants.COLS; col++) {
                if (hasFog[row][col] && fogTiles[row][col] == null) {
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
    }

        public int getFogLength () {
            return fogLength;
        }

    public boolean getHasFog(int row, int col) {
        return hasFog[row][col];
    }
}
