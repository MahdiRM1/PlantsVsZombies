package main.plantsvszombies.Game.PlayModes;

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