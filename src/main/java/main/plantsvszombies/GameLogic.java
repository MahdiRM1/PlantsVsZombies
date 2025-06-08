package main.plantsvszombies;

import java.util.ArrayList;

public class GameLogic {
    private final Plant[][] pottedPlants = new Plant[5][9];
    private ArrayList<Zombie> zombies = new ArrayList<>();

    public GameLogic(){
    }

    public void setPlant(int i, int j, Plant plant){
        if(pottedPlants[i][j] == null) pottedPlants[i][j] = plant;
    }

    public void addZombie(int i){
        System.out.println("zombie in line " + i + "added");
        zombies.add(new OriginalZombie(i));
    }

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
            ArrayList<Integer> plantsAhead = checkRow(z.row, z.col);
            for(Integer integer : plantsAhead) shooterCoordination.add(new Integer[]{z.row, integer});
        }
        return shooterCoordination;
    }

    private ArrayList<Integer> checkRow(int row, int col){
        ArrayList<Integer> plantsX = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if(pottedPlants[row][i] instanceof PeaPlant) plantsX.add(i);
        }
        return plantsX;
    }
}
