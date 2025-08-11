package main.plantsvszombies.Game.PlayModes;

import java.io.*;
import java.net.*;

public class Client extends PlayMode implements Runnable{
    private String serverCommand;
    private Socket socket;
    PrintWriter out;
    BufferedReader in;

    public Client(String IP) {
        super();
        gameState = "playing";
        socket = null;
        serverCommand = "execute no moves";
        connection(IP);
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void updateGame() {
        if(!serverCommand.equals("execute no moves")) System.out.println(serverCommand);
        action(serverCommand);
        serverCommand = "execute no moves";
    }


    @Override
    public String WinOrLose() {
        if(!gameState.equals("playing")) return gameState;
        return checkGameState();
    }

    public void run() {
        System.out.println("man clientammmmmm");
        try {
            do {
                serverCommand = in.readLine();
            } while(!serverCommand.equals("win") && !serverCommand.equals("lose"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void connection(String IP) {
        int port = 5000;
        try {
            socket = new Socket(IP, port);
            System.out.println("vasl shodam");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void ready(){
        out.println("ready");
    }

    public void waitForPlayers() {
        try{
            do{
                serverCommand = in.readLine();
                System.out.println(serverCommand);
            } while (!"allready".equals(serverCommand)) ;
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
