package main.plantsvszombies.Game.PlayModes;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class MultiServer extends PlayMode implements Runnable {
    String serverCommand;
    List<Server> servers;
    List<Thread> threads;
    ServerSocket serverSocket;

    public MultiServer () {
        try {
            serverSocket = new ServerSocket(5000);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        servers = new ArrayList<>();
        threads = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            connect();
        }
    }

    public void connect(){
        try {
            Socket socket = serverSocket.accept();
            System.out.println("connected: " + socket.getInetAddress());
            Server server = new Server(socket);
            Thread thread = new Thread(server);
            threads.add(thread);
            servers.add(server);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public Client innerConnection(){
        Client client = new Client("127.0.0.1");
        return client;
    }

    @Override
    public void run() {
        System.out.println("It's running");
        for(Thread thread : threads) {
            thread.start();
        }
        while (!checkAllReady()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        allReady();
        while (!(serverCommand = timeHandler()).equals("wave")) updateServers();
    }

    private void updateServers(){
        for (Server server: servers) server.setServerMessage(serverCommand);
    }

    public boolean checkAllReady(){
        for (Server server: servers){
            if (!server.getServerMessage().equals("ready")) return false;
        }
        return true;
    }

    private void allReady(){
        for (Server server: servers){
            server.allReady();
        }
    }

    public List<Server> getServers() {
        return servers;
    }

    public List<Thread> getThreads() {
        return threads;
    }


    @Override
    public void updateGame() {}
    @Override
    public String WinOrLose() {return "";}
}
