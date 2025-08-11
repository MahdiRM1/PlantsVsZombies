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
            for (int i = 0; i < 2 ; i++) {
               Socket socket = serverSocket.accept();
                System.out.println("connected");
               Server server = new Server(socket);
               Thread thread = new Thread(server);
               threads.add(thread);
               servers.add(server);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void run() {
        System.out.println("It's running");
        while(true) {
            for(Thread thread : threads) {
                thread.start();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
