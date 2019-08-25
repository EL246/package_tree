package package_tree.server;

import java.io.*;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final static int PORT = 8080;
    private ServerSocket serverSocket;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public void start() throws IOException {
        System.out.println("Starting server...");
        this.serverSocket = new ServerSocket(PORT);
        while(true) {
            threadPool.execute(new ClientHandler(serverSocket.accept()));
        }
    }

    public void stop() throws IOException {
        this.threadPool.shutdown();
        serverSocket.close();
    }
}
