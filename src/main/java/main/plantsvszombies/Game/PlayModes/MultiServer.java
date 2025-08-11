package main.plantsvszombies.Game.PlayModes;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class MultiServer implements Runnable {
    List<Server> servers;
    List<Thread> threads;

    public MultiServer () {
        servers = new ArrayList<>();
        threads = new ArrayList<>();
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            for (int i = 0; i < 1 ; i++) {
               Socket socket = serverSocket.accept();
                System.out.println("connected: " + socket.getInetAddress());
               Server server = new Server(socket);
               Thread thread = new Thread(server);
               threads.add(thread);
               servers.add(server);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Client innerConnection(){
        Client client = new Client();
        client.connection("127.0.0.1");
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
}
