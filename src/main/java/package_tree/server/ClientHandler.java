package package_tree.server;

import package_tree.message.MessageHandler;
import package_tree.message.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final Socket clientSocket;
    private MessageHandler messageHandler;

    ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.messageHandler = new MessageHandler();
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             clientSocket
        ) {

            String message;
            while ((message = in.readLine()) != null) {
                Response response = messageHandler.handle(message);
                out.println(response.getMessage() + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}