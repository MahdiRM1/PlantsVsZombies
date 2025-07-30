package main.plantsvszombies;

import javafx.scene.media.AudioClip;

interface Shroom {

    AudioClip wakeUpSound = new AudioClip("file:Audio/wakeup.mp3");

    default boolean setIsSleep(GameMode mode) {
        return mode == GameMode.DAY;
    }

    void wakeUp();

    boolean isSleep();
}
