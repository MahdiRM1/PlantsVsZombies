package main.plantsvszombies;

import javafx.scene.media.AudioClip;

interface Shroom {

    AudioClip wakeUpSound = Constants.setSound("wakeup", false);

    default boolean setIsSleep(GameMode mode) {
        return mode == GameMode.DAY;
    }

    void wakeUp();

    boolean isSleep();
}
