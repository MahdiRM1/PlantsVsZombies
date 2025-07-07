package main.plantsvszombies;

interface Shroom {
    default boolean setIsSleep(GameMode mode) {
        if(mode == GameMode.DAY) return true;
        return false;
    }

    void wakeUp();
    boolean isSleep();
}
