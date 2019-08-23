package package_tree.server;

import package_tree.message.MessageHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private MessageHandler messageHandler;

    public ClientHandler(Socket clientSocket, MessageHandler messageHandler) {
        this.clientSocket = clientSocket;
        this.messageHandler = messageHandler;
    }

    public void run() {
        try {
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);

            String message;
//            message = in.readLine();
            while ((message = in.readLine()) != null) {
                //out.println(message);
                out.println(messageHandler.handle(message));
            }

            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {

        }
    }
}
