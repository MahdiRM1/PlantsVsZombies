package main.plantsvszombies;

interface Shroom {

    default boolean setIsSleep(GameMode mode) {
        return mode == GameMode.DAY;
    }

    void wakeUp();

    boolean isSleep();
}
