package main.plantsvszombies;

import javafx.scene.media.AudioClip;

public class GlobalState {
    public static long gameTime = 0;
    private static AudioClip click = new AudioClip("file:Audio/buttonclick.mp3");

    public static void playClickTrack(){
        click.play();
    }
}
