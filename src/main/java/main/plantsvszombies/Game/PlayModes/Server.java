package main.plantsvszombies.Game.PlayModes;

import java.io.*;
import java.net.*;

public class Server extends PlayMode implements Runnable {
    private String serverMessage;
    private Socket socket;
    BufferedReader in;
    PrintWriter out;
    boolean allReady = false;

    public Server(Socket socket) {
        serverMessage = "not ready";
        this.socket = socket;
        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        }catch(IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void run() {
        try {
            while (!serverMessage.equals("ready")){
                serverMessage = in.readLine();
            }
            while (!allReady) System.out.println(allReady);
            out.println("allready");
            while (!(serverMessage = in.readLine()).equals("wave")) {
                out.println(serverMessage);
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void setServerMessage(String serverMessage) {
        this.serverMessage = serverMessage;
    }

    public String getServerMessage() {
        return serverMessage;
    }

    public void allReady(){
        allReady = true;
    }

    @Override
    public void updateGame() {}
    @Override
    public String WinOrLose() {return "";}
}
