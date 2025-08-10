package main.plantsvszombies.Game.PlayModes;

import main.plantsvszombies.Game.Tools.Constants;

import java.io.*;
import java.net.*;

public class Server extends PlayMode implements Runnable {
    private String serverMessage;
    private Socket socket;

    @Override
    public void run() {
        connection();
        try {
//            ServerSocket serverSocket = new ServerSocket(5000);
//            System.out.println("Server is waiting for connection...");
//
//            Socket socket = serverSocket.accept();
//            System.out.println("Client connected: " + socket.getInetAddress());

            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String msg;
            while (!(msg = timeHandler()).equals("wave")) {
                out.println(msg);
                if (!msg.equals("execute no moves")) System.out.println(msg);
                System.out.println(Constants.gameTime);
            }

            socket.close();
        } catch (IOException e){
            throw new RuntimeException();
        }
    }

    private void connection(){
        try{
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server is waiting for connection...");

            socket = serverSocket.accept();
            System.out.println("Client connected: " + socket.getInetAddress());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateGame() {}
    @Override
    public String WinOrLose() {return "";}
}
