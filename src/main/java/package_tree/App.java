package package_tree;

import package_tree.server.Server;

class App
{
    public static void main( String[] args ) {
        Server s = new Server();
        s.start();
    }
}
