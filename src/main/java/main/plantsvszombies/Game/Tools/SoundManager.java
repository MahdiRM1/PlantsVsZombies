package main.plantsvszombies.Game.Tools;

import javafx.animation.Timeline;
import javafx.scene.media.AudioClip;

public class SoundManager {

    private SoundManager(){}

    public static double volume = 1;
    public static double music = 0.5;
    private static final AudioClip click = new AudioClip(SoundManager.class.getResource("/Audio/buttonclick.mp3").toExternalForm());
    private static final AudioClip wrongClick = new AudioClip(SoundManager.class.getResource("/Audio/buzzer.mp3").toExternalForm());
    private static final AudioClip correctClick = new AudioClip(SoundManager.class.getResource("/Audio/plant.mp3").toExternalForm());
    private static final AudioClip shovelClick = new AudioClip(SoundManager.class.getResource("/Audio/shovel.mp3").toExternalForm());

    public static AudioClip setSound(String name, boolean repeat){
        AudioClip sound = new AudioClip(SoundManager.class.getResource("/Audio/" + name + ".mp3").toExternalForm());
        if (repeat) sound.setCycleCount(Timeline.INDEFINITE);
        sound.setVolume(volume);
        return sound;
    }

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
