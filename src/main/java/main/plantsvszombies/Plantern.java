package main.plantsvszombies;

import java.util.List;


public class Plantern extends Plant{

    public Plantern(int row, int col) {
        super(row, col);
        price = 50;
        HP = 100;
        rechargeTime = 10;
    }

    @Override
    public boolean actionHappens(List<Zombie> zombies) {
        return false;
    }

    public void action(GameUI gameui) {
        if(gameui.getFog() != null) {
            gameui.getFog().clearFogAt(row, col);
        }
    }



//    public void activate(GameGrid grid, int row, int col) {
//        // Clear fog in surrounding tiles
//        for (int r = row - RANGE; r <= row + RANGE; r++) {
//            for (int c = col - RANGE; c <= col + RANGE; c++) {
//                if (r >= 0 && r < 5 && c >= 0 && c < 9) {
//                    grid.getFog().clearFogAt(r, c);
//                }
//            }
//        }
//    }
}
