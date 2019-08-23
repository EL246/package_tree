package package_tree.server;

import package_tree.message.MessageHandler;

import java.io.*;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final static int PORT = 8080;
    private ServerSocket serverSocket;
    private MessageHandler messageHandler;
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public void start() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
        this.messageHandler = new MessageHandler();
        while(true) {
            threadPool.execute(new ClientHandler(serverSocket.accept(),messageHandler));
        }
    }

    public void stop() throws IOException {
        this.threadPool.shutdown();
        serverSocket.close();
    }
}
