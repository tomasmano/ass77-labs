package ass.manotoma.webserver01;

import ass.manotoma.webserver01.server.core.Server;
import ass.manotoma.webserver01.server.core.WebServer;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        Server server = new WebServer();
        server.serve();
    }
}
