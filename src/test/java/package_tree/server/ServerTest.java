package package_tree.server;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import package_tree.message.Response;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServerTest {
    private static Socket client;
    private static PrintWriter out;
    private static BufferedReader in;

    @BeforeAll
    public static void setup() throws IOException {
        client = new Socket("127.0.0.1", 8080);
        out = new PrintWriter(client.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    @Test
    public void givenClientMessage_whenServerRespondsWhenStarted_thenCorrect() throws IOException {
        String message = "test message";
        out.println(message);
        String response = in.readLine();
        assertEquals(Response.ERROR.getMessage(), response);
    }

    @Test
    public void givenMultipleClientMessage_whenServerRespondsWhenStarted_thenCorrect() {
    }

    @AfterAll
    public static void tearDown() throws IOException {
        in.close();
        out.close();
        client.close();
    }
}
