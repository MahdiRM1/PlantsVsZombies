package main.plantsvszombies.Game.PlayModes;

import main.plantsvszombies.Enums.GameMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends PlayMode implements Runnable {

    private String serverCommand;
    private Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;
    private GameMode gameMode;

    public Client(String IP) {
        super();
        gameState = "playing";
        serverCommand = "execute no moves";
        connection(IP);
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            mode();
        } catch (IOException e) {
            System.out.println("line 25 Client: " + e.getMessage());
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
        if (!gameState.equals("playing")) return gameState;
        return gameState = checkGameState();
    }

    public void run() {
        try {
            do {
                String command = in.readLine();
                if (command == null) {
                    break;
                }
                serverCommand = command;
                out.println(gameState);

                Thread.sleep(10);
            } while (!serverCommand.equals("win") && !serverCommand.equals("lose") &&
                !gameState.equals("win") && !gameState.equals("lose"));
        } catch (IOException | InterruptedException e) {
            System.out.println("line 57 Client: " + e.getMessage());
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
            System.out.println("line 57 Client: " + e.getMessage());
            throw new RuntimeException();
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
                Thread.sleep(100);
            } while (!serverCommand.equals("allready"));
        } catch (IOException | InterruptedException e) {
            System.out.println("line 85 Client: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    private void cleanup() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            System.out.println("line 57 Client: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    private void mode() throws IOException{
        String msg = "mode";
        while (msg.equals("mode")) msg = in.readLine();
        if (msg.equals("NIGHT")) gameMode = GameMode.NIGHT;
        else gameMode = GameMode.DAY;
    }

    public GameMode getGameMode() {
        return gameMode;
    }
}
