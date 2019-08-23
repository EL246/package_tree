package package_tree;

import package_tree.server.Server;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        Server s = new Server();
        s.start();
        s.stop();
    }
}
