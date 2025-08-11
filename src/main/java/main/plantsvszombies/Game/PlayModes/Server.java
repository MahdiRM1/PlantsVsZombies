package main.plantsvszombies.Game.PlayModes;

import main.plantsvszombies.Game.Tools.Constants;

import java.io.*;
import java.net.*;

public class Server extends PlayMode implements Runnable {
    private String serverMessage;
    private Socket socket;
    BufferedReader in;
    PrintWriter out;

    public Server(Socket socket) {
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
        String msg = timeHandler();
        out.println(msg);
    }

//    private void connection(){
//        try{
//            ServerSocket serverSocket = new ServerSocket(5000);
//            System.out.println("Server is waiting for connection...");
//
//            socket = serverSocket.accept();
//            System.out.println("Client connected: " + socket.getInetAddress());
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//    }

    @Override
    public void updateGame() {}
    @Override
    public String WinOrLose() {return "";}
}
