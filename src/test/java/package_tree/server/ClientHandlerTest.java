package package_tree.server;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import package_tree.message.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ClientHandlerTest {

    @Test
    public static void runReturnsErrorResponseOnBadMessage() throws IOException {
        Server server = Mockito.mock(Server.class);
        server.start();
        Socket client = new Socket("127.0.0.1", 8080);
        new ClientHandler(client).run();
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out.println("invalid message");
        String response = in.readLine();
        assertEquals(Response.ERROR.getMessage(),response);
    }

}