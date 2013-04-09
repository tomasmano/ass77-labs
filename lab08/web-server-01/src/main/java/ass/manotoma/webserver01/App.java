package ass.manotoma.webserver01;

import ass.manotoma.webserver01.server.core.Server;
import ass.manotoma.webserver01.server.core.EchoServer;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        Server server = new EchoServer();
        server.serve();
    }
}
