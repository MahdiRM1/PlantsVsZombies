package main.plantsvszombies.Game.PlayModes;

import javafx.scene.layout.Pane;
import main.plantsvszombies.Items.Grave;
import main.plantsvszombies.Zombies.Zombie;

import java.util.List;

public class DefaultMode extends PlayMode{
    public DefaultMode(Pane pane, List<Zombie> zombies, List<Grave> graves) {
        super(pane, zombies, graves);
    }

    @Override
    public void updateGame(){
        action(timeHandler());
    }

    @Override
    public String WinOrLose(){
        if (checkWin()) return "win";
        else if (checkLose()) return "lose";
        return "null";
    }
}