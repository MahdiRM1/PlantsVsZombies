package main.plantsvszombies.Game.PlayModes;

import java.io.*;
import java.net.*;

public class Client extends PlayMode implements Runnable{
    private String serverCommand;
    private Socket socket;

    public Client() {
        super();
        gameState = "playing";
        socket = null;
        connection();
        serverCommand = "execute no moves";
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
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            do {
                serverCommand = in.readLine();
            } while(!serverCommand.equals("win") && !serverCommand.equals("lose"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void connection() {
        String IP = "192.168.223.30";
        int port = 5000;
        try {
            socket = new Socket(IP, port);
            System.out.println("vasl shodam");
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
    }
}
