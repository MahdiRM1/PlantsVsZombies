package main.plantsvszombies.Game.PlayModes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends PlayMode implements Runnable {

    private String serverCommand;
    private Socket socket;
    PrintWriter out;
    BufferedReader in;

    public Client(String IP) {
        super();
        gameState = "playing";
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
        synchronized (this) {
            action(serverCommand);
            if (!serverCommand.equals("wave")) serverCommand = "execute no moves";
        }
    }

    @Override
    public String WinOrLose() {
        if (!gameState.equals("playing")) {
            return gameState;
        }
        return checkGameState();
    }

    public void run() {
        try {
            do {
                String command = in.readLine();
                if (command == null) {
                    break;
                }
                serverCommand = command;

                Thread.sleep(10);
            } while (!serverCommand.equals("win") && !serverCommand.equals("lose"));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            cleanup();
        }
    }

    private void connection(String IP) {
        int port = 5000;
        try {
            socket = new Socket(IP, port);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void ready() {
        out.println("ready");
        out.flush();
    }

    public void waitForPlayers() {
        try {
            do {
                serverCommand = in.readLine();
            } while (!serverCommand.equals("allready"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void cleanup() {
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Cleanup error: " + e.getMessage());
        }
    }
}
