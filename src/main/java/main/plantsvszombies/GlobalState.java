package main.plantsvszombies;

import javafx.scene.media.AudioClip;

public class GlobalState {
    public static long gameTime = 0;
    public static double volume = 1;
    public static double music = 0.5;
    private static final AudioClip click = new AudioClip("file:Audio/buttonclick.mp3");
    private static final AudioClip wrongClick = new AudioClip("file:Audio/buzzer.mp3");
    private static final AudioClip correctClick = new AudioClip("file:Audio/plant.mp3");
    private static final AudioClip shovelClick = new AudioClip("file:Audio/shovel.mp3");

    public static void playClickTrack(){
        click.setVolume(volume);
        click.play();
    }

    public static void playWrongClick(){
        wrongClick.setVolume(volume);
        wrongClick.play();
    }

    public static void playCorrectClick(){
        correctClick.setVolume(volume);
        correctClick.play();
    }

    public static void playShovelClick(){
        shovelClick.setVolume(volume);
        shovelClick.play();
    }
}
