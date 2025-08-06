package main.plantsvszombies.Enums;

public enum SunType {
    BASE_FALLEN,
    FLOWER_FALLEN,
    RISEN(),
    COLLECTED;

    private int row, col;

    public SunType setCoordination(int row, int col) {
        this.row = row;
        this.col = col;
        return RISEN;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}