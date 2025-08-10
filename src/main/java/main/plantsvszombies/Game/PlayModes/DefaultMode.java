package main.plantsvszombies.Game.PlayModes;

import javafx.scene.layout.Pane;
import main.plantsvszombies.Items.Grave;
import main.plantsvszombies.Zombies.Zombie;

import java.util.List;

public class DefaultMode extends PlayMode{
    public DefaultMode() {
        super();
    }

    @Override
    public void updateGame(){
        action(timeHandler());
    }

    @Override
    public String WinOrLose(){
        return checkGameState();
    }
}