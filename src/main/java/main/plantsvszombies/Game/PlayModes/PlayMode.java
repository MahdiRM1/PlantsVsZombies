package main.plantsvszombies.Game.PlayModes;

import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import main.plantsvszombies.Game.*;
import main.plantsvszombies.Items.Grave;
import main.plantsvszombies.Zombies.*;

public abstract class PlayMode{
    private final Pane pane;
    private final List<Zombie> zombies;
    private final List<Grave> graves;

    public PlayMode(Pane pane, List<Zombie> zombies, List<Grave> graves){
        this.pane = pane;
        this.zombies = zombies;
        this.graves = graves;
    }

    public abstract void updateGame();
    public abstract String WinOrLose();

    protected void action(String str){
        switch (str.toLowerCase()) {
            case ("win") -> {}
            case ("lose") -> {}
            case ("wave") -> wave();
            case ("execute no moves") -> {}
            default -> addZombie(str);
        }
    }

    // controls the general timing of zombies entering and attack waves
    protected String timeHandler() {
        int time = (int) GlobalState.gameTime / 1000;
        if (GlobalState.gameTime == 20_000) {
            AudioClip sound = Constants.setSound("awooga", false);
            sound.play();
        }
        if (time <= 20) return "execute no moves";
        else if (time <= 40) return handleZombie(5000, 1000, 1);
        else if (time < 60) return handleZombie(4000, 0, 2);
        else if (time < 70); 
        else if (time < 80) return "wave";
        else if (time < 130) return handleZombie(3000, 0, 4);
        else if (time < 140); else if (time < 155) return "wave";
        return "execute no moves";
    }

    private void addZombie(String str){
        String[] parts = str.split(",");
        int z = Integer.parseInt(parts[0]);
        int row = Integer.parseInt(parts[1]);
        spawnZombie(z, row);
    }

    // handles the zombie entering
    private String handleZombie(long base, long mode, int zombieTypes) {
        if (GlobalState.gameTime % base == mode)
            return (int)(Math.random() * zombieTypes) + "," + (int) (Math.random() * 5);
        return "execute no moves";
    }

    // handles the attack waves
    private void wave() {
        int zombieTypes = GlobalState.gameTime < 100_000 ? 4 : 5;
        int attackType = zombieTypes - 3;
        if (GlobalState.gameTime == (long) attackType * 70_000) {
            spawnZombie(5, (int)(Math.random()*5));
            AudioClip attackWave = Constants.setSound("hugewave", false);
            if (attackType > 1) {
                attackWave = Constants.setSound("siren", false);
                AudioClip sound = Constants.setSound("awooga", false);
                sound.play();
                for (Grave grave : graves) {
                    spawnZombie((int) (Math.random() * 4), grave.getRow(), grave.getCol());
                }
            }
            attackWave.play();
        } else if (GlobalState.gameTime % 4000 == 0 || GlobalState.gameTime % 4000 == 200) {
            for (int i = 0; i < 5; i++) {
                spawnZombie((int) (Math.random() * zombieTypes), i);
            }
        }
    }

    // spawns a zombie
    private void spawnZombie(int z, int row) {
        spawnZombie(z, row, 11);
    }

    // determines what type of zombie to add
    private void spawnZombie(int z, int row, int col) {
        Zombie zombie = switch (z) {
            case 0 -> new OriginalZombie(row, col);
            case 1 -> new ConeheadZombie(row, col);
            case 2 -> new ScreenDoorZombie(row, col);
            case 3 -> new BucketheadZombie(row, col);
            case 4 -> new Imp(row, col);
            default -> new FlagZombie(row, col);
        };

        zombies.add(zombie);
        pane.getChildren().addAll(zombie.getPicture(), zombie.getSecondPicture());
    }

    // lose logic
    public boolean checkLose() {
        for (Zombie zombie : zombies) {
            if (zombie.getCol() < 0) {
                return true;
            }
        }
        return false;
    }

    // win logic
    public boolean checkWin() {
        for (Zombie z : zombies) {
            if (!z.isHypnotized()) {
                return false;
            }
        }
        return GlobalState.gameTime >= 155_000;
    }
}