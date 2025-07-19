package main.plantsvszombies;

enum GameMode {
    DAY(), NIGHT();

    private int fogLength;

    public void setFogLength(int fogLength){
        if (this == NIGHT) this.fogLength = fogLength;
    }

    public int getFogLength() {
        return fogLength;
    }
}

enum ZombieState {
    WALKING, EATING, FREEZE, HYPNOTIZED, BOOM_DIE, DIE, DEAD
}

enum NutState {
    FULL_LIFE, HALF_LIFE, END_LIFE
}

enum BulletType {
    NORMAL_BULLET, ICE_BULLET, SHROOM_BULLET
}

enum SunType {
    BASE_FALLEN,
    FLOWER_FALLEN,
    RISEN(),
    COLLECTED;

    private int row, col;

    public SunType setCoordination(int row, int col) {
        SunType type = RISEN;
        this.row = row;
        this.col = col;
        return type;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
