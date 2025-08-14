package main.plantsvszombies.Game.PlayModes;

import main.plantsvszombies.Game.Tools.Constants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MultiServer extends PlayMode implements Runnable {

    String serverCommand;
    List<Server> servers;
    List<Thread> threads;
    ServerSocket serverSocket;

    public MultiServer() {
        try {
            serverSocket = new ServerSocket(5000);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        servers = new CopyOnWriteArrayList<>();
        threads = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 1; i++) {
            connect();
        }
    }

    public void connect() {
        try {
            Socket socket = serverSocket.accept();
            System.out.println("connected: " + socket.getInetAddress());
            Server server = new Server(socket);
            Thread thread = new Thread(server);
            threads.add(thread);
            servers.add(server);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Client innerConnection() {
        return new Client("127.0.0.1");
    }

    @Override
    public void run() {
        for (Thread thread : threads) {
            thread.start();
        }
        try{
            while (!checkAllReady()) Thread.sleep(100);
            allReady();
            Thread.sleep(100);
            do {
                serverCommand = timeHandler();
                updateServers();
                Thread.sleep(1000);
            } while (!serverCommand.equals("win") && !serverCommand.equals("lose"));
        } catch (InterruptedException e){
            System.out.println(e.getMessage());
        } finally {
            cleanup();
        }
    }

    private void updateServers() {
        System.out.println("server: " + serverCommand + ", " + Constants.gameTime);
        for (Server server : servers) {
            server.setServerMessage(serverCommand);
        }
    }

    public boolean checkAllReady() {
        for (Server server : servers) {
            if (!server.getServerMessage().equals("ready")) {
                return false;
            }
        }
        return true;
    }

    private void allReady() {
        for (Server server : servers) {
            server.allReady();
        }
    }

    private void cleanup() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Shutdown error: " + e.getMessage());
        }
    }

    @Override
    public void updateGame() {
    }

    @Override
    public String WinOrLose() {
        return "";
    }

    @Override
    protected String handleZombie(long base, int zombieTypes) {
        if (Math.abs(Constants.gameTime % base - 1000) < 500) {
            return (int) (Math.random() * zombieTypes) + "," + (int) (Math.random() * 5);
        }
        return "execute no moves";
    }

    @Override
    protected String wave() {
        int zombieTypes = Constants.gameTime < 100_000 ? 4 : 5;
        int attackType = zombieTypes - 3;
        if (Constants.gameTime - (long) attackType * 70_000 < 1000) return finalWave(attackType);
        else if (Math.abs(Constants.gameTime % 4000 - 1000) < 500) return normalWave(zombieTypes);
        return "execute no moves";
    }
}
