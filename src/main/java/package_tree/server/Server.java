package package_tree.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final static int PORT = 8080;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(100);

    public void start() {
        System.out.println("Starting server...");
        System.out.println("Listening on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                threadPool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.threadPool.shutdown();
        }
    }
}