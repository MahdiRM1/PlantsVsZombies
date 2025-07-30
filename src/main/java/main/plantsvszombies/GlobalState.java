package main.plantsvszombies;

import javafx.scene.media.AudioClip;

public class GlobalState {
    public static long gameTime = 0;
    private static final AudioClip click = Constants.setSound("buttonclick", false);
    private static final AudioClip wrongClick = Constants.setSound("buzzer", false);
    private static final AudioClip correctClick = Constants.setSound("plant", false);
    private static final AudioClip shovelClick = Constants.setSound("shovel", false);

    public static void playClickTrack(){
        click.play();
    }

    public static void playWrongClick(){
        wrongClick.play();
    }

    public static void playCorrectClick(){
        correctClick.play();
    }

    public static void playShovelClick(){
        shovelClick.play();
    }
}
