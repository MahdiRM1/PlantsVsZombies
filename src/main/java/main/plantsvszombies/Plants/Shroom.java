package main.plantsvszombies.Plants;

import javafx.scene.media.AudioClip;
import main.plantsvszombies.Game.Tools.SoundManager;

public interface Shroom {

    AudioClip wakeUpSound = SoundManager.setSound("wakeup", false);

    void wakeUp();

    boolean isSleep();
}
