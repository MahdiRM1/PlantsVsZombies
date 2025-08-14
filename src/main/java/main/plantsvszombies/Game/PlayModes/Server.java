package main.plantsvszombies.Game.PlayModes;

import java.io.*;
import java.net.*;

public class Server implements Runnable {

    private String serverMessage;
    private final Socket socket;
    BufferedReader in;
    PrintWriter out;
    boolean allReady = false;

    public Server(Socket socket) {
        serverMessage = "not ready";
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
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
            while (!allReady) {
                Thread.sleep(10);
            }
            out.println("allready");
            out.flush();
            while (!serverMessage.equals("wave")) {
//                out.println(serverMessage);
//                out.flush();
//                Thread.sleep(20);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            cleanup();
        }
    }

    public void setServerMessage(String serverMessage) {
        this.serverMessage = serverMessage;
        out.println(serverMessage);
    }

    public synchronized String getServerMessage() {
        return serverMessage;
    }

    public void allReady() {
        allReady = true;
    }

    private void cleanup() {
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Cleanup error: " + e.getMessage());
        }
    }
}
