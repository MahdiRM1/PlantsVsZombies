package main.plantsvszombies.Enums;

public enum GameMode {
    DAY(), NIGHT();

    private int fogLength;

    public void setFogLength(int fogLength){
        if (this == NIGHT) this.fogLength = fogLength;
    }

    public int getFogLength() {
        return fogLength;
    }
}