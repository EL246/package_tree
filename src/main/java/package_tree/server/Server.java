package package_tree.server;

import package_tree.message.MessageHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final static int PORT = 8080;
    private ServerSocket serverSocket;
    private Socket channel;
    private BufferedReader in;
    private PrintWriter out;

    public void start() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
        this.channel = serverSocket.accept();
        this.in = new BufferedReader(new InputStreamReader(channel.getInputStream()));
        this.out = new PrintWriter(channel.getOutputStream(),true);
        String message = in.readLine();
        out.println(message);
        MessageHandler messageHandler = new MessageHandler();
        out.println(messageHandler.handle(message));
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        channel.close();
        serverSocket.close();
    }
}
