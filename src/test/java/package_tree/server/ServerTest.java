package package_tree.server;

import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static junit.framework.TestCase.assertEquals;

public class ServerTest {
    @Test
    public void givenClientMessage_whenServerRespondsWhenStarted_thenCorrect() throws IOException {
        Socket client = new Socket("127.0.0.1",8080);
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String message = "test message";
        out.println(message);
        String response = in.readLine();
        in.close();
        out.close();
        client.close();
        assertEquals(message, response);
    }
}
