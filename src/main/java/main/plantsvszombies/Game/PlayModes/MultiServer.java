package main.plantsvszombies.Game.PlayModes;

import main.plantsvszombies.Enums.GameMode;
import main.plantsvszombies.Game.Tools.Constants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MultiServer extends PlayMode implements Runnable {

    private String serverCommand;
    private final List<Server> servers;
    private final List<Thread> threads;
    private final ServerSocket serverSocket;
    private final GameMode mode;

    public MultiServer(GameMode mode) {
        this.mode = mode;
        try {
            serverSocket = new ServerSocket(5000);
        } catch (IOException e) {
            System.out.println("line 22 MultiServer: " + e.getMessage());
            throw new RuntimeException();
        }
        servers = new ArrayList<>();
        threads = new ArrayList<>();
    }

    public void connect() {
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("connected: " + socket.getInetAddress());
                Server server = new Server(socket, mode);
                Thread thread = new Thread(server);
                threads.add(thread);
                servers.add(server);
                if (server.getInetAddress().equals("127.0.0.1")) break;
            }
        } catch (IOException e) {
            System.out.println("line 41 MultiServer: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public void run() {
        for (Thread thread : threads) thread.start();
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
            System.out.println("line 61 MultiServer: " + e.getMessage());
            throw new RuntimeException();
        } finally {
            cleanup();
        }
    }

    private void updateServers() {
        for (Server server : servers)
            if (!server.getGameState().equals("playing")){
                informServers(server.getGameState());
                servers.remove(server);
                break;
            }
        for (Server server : servers)
            server.setServerMessage(serverCommand);
    }

    private void informServers(String state){
        int player = isFinish();
        if (player == -1) return;
        if (state.equals("win")) servers.get(player).setServerMessage("lose");
        else servers.get(player).setServerMessage("win");
    }

    private int isFinish(){
        int player = -1;
        for (int i = 0; i < servers.size(); i++)
            if (servers.get(i).getGameState().equals("playing")) {
                if (player == -1) player = i;
                else return -1;
            }
        return player;
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

    public void cleanup() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) serverSocket.close();
            for (Server server : servers) server.cleanup();
        } catch (IOException e) {
            System.out.println("line 115 MultiServer: " + e.getMessage());
            throw new RuntimeException();
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

    public List<Server> getServers() {
        return servers;
    }
}
