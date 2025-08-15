package main.plantsvszombies.Game.PlayModes;

import java.io.*;
import java.net.*;

public class Server implements Runnable {

    private String serverMessage;
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    private boolean allReady = false;
    private String gameState = "playing";

    public Server(Socket socket) {
        serverMessage = "not ready";
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("line 22 Server: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public void run() {
        try {
            while (!serverMessage.equals("ready")) {
                serverMessage = in.readLine();
                Thread.sleep(10);
            }
            while (!allReady) Thread.sleep(10);

            out.println("allready");
            out.flush();

            while (!gameState.equals("win") && !gameState.equals("lose"));
        } catch (IOException | InterruptedException e) {
            System.out.println("line 41 Server: " + e.getMessage());
            throw new RuntimeException();
        } finally {
            cleanup();
        }
    }

    public void setServerMessage(String serverMessage) {
        this.serverMessage = serverMessage;
        out.println(serverMessage);
        try {
            gameState = in.readLine();
        } catch (IOException e) {
            System.out.println("line 54 Server: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    public synchronized String getServerMessage() {
        return serverMessage;
    }

    public void allReady() {
        allReady = true;
    }

    private void cleanup() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            System.out.println("line 73 Server: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    public String getGameState() {
        return gameState;
    }

    public String getInetAddress(){
        return socket.getInetAddress().toString();
    }
}
