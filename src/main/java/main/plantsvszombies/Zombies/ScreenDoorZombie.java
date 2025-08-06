package main.plantsvszombies.Zombies;

import javafx.scene.image.Image;
import main.plantsvszombies.Enums.ZombieState;
import main.plantsvszombies.Game.Constants;
import main.plantsvszombies.Game.GlobalState;
import main.plantsvszombies.GameState.ZombieData;

public class ScreenDoorZombie extends Zombie {

    private final static int WALK_FRAME_COUNT = 47;
    private final static int ATTACK_FRAME_COUNT = 40;
    private final static int BOOM_DIE_FRAME_COUNT = 37;
    private final static int DOOR_FRAME_COUNT = 10;
    private final static Image[] WALK_FRAMES;
    private final static Image[] ATTACK_FRAMES;
    private final static Image[] BOOM_DIE_FRAMES;
    private final static Image[] DOOR_FRAMES;

    static {
        WALK_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/ScreenDoorZombie/Walk/frame_", WALK_FRAME_COUNT);
        ATTACK_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/ScreenDoorZombie/Attack/frame_", ATTACK_FRAME_COUNT);
        DOOR_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/ScreenDoorZombie/door/frame_", DOOR_FRAME_COUNT);
        BOOM_DIE_FRAMES = Constants.getArrayImage("Pictures/ZombiePicture/BoomDie/frame_", BOOM_DIE_FRAME_COUNT);
    }

    public ScreenDoorZombie(ZombieData data) {
        super(data);
        speed = 5;
    }

    public ScreenDoorZombie(int row, int col) {
        super(row, col);
        HP = 260;
        speed = 5;
    }

    @Override
    public void updateFreezeTime(){
        if (HP > 100) freezeTime = -5000;
        else freezeTime = GlobalState.gameTime;
    }

    @Override
    protected Image[] getZombiePictures(){
        if (HP <= 100) return OriginalZombie.getimages(state);
        return switch (state){
            case WALKING -> WALK_FRAMES;
            case EATING -> ATTACK_FRAMES;
            case BOOM_DIE -> BOOM_DIE_FRAMES;
            default -> null;
        };
    }

    @Override
    protected Image[] getPictures() {
        if (state == ZombieState.DIE) return HEAD_FRAMES;
        return DOOR_FRAMES;
    }
}
