package main.plantsvszombies.Plants;

import javafx.scene.media.AudioClip;
import main.plantsvszombies.Enums.GameMode;
import main.plantsvszombies.Game.Constants;

public interface Shroom {

    AudioClip wakeUpSound = Constants.setSound("wakeup", false);

    void wakeUp();

    boolean isSleep();
}
