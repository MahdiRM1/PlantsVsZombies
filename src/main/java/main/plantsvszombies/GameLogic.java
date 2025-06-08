package main.plantsvszombies;

import java.util.ArrayList;

public class GameLogic {
    private final Plant[][] pottedPlants = new Plant[5][9];
    private final ArrayList<Zombie> zombies = new ArrayList<>();

    public GameLogic(){
    }

    public void setPlant(int i, int j, Plant plant){
        if(pottedPlants[i][j] == null) pottedPlants[i][j] = plant;
    }//doroste

    public void addZombie(Zombie z){
        zombies.add(z);
    }//doroste

    public void checkCorrespondence(){
        for(Zombie zombie: zombies) {
            if(pottedPlants[zombie.getRow()][zombie.getCol()] != null) zombie.eatPlant();
        }
    }

    public void checkDied(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    if(pottedPlants[i][j].getHp() <= 0) pottedPlants[i][j] = null;
                } catch (NullPointerException e) {}
            }
        }
        for (int i = 0; i < zombies.size(); i++) {
            if(zombies.get(i).getHp() <= 0) zombies.remove(i);
        }
    }

    public ArrayList<Integer[]> plantsAligned() {
        ArrayList<Integer[]> shooterCoordination = new ArrayList<>();
        for (Zombie z : zombies){
            ArrayList<Integer> plantsAhead = checkRow(z.getRow(), z.getCol());
            for(Integer integer : plantsAhead) shooterCoordination.add(new Integer[]{z.getRow(), integer});
        }
        return shooterCoordination;
    }//doroste

    private ArrayList<Integer> checkRow(int row, int col){
        ArrayList<Integer> plantsX = new ArrayList<>();
        if(col == 9) col--;
        for (int i = 0; i < col; i++) {
            if(pottedPlants[row][i] instanceof PeaPlant) plantsX.add(i);
        }
        return plantsX;
    }//doroste
}
