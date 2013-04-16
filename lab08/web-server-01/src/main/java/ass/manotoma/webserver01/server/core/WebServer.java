package ass.manotoma.webserver01.server.core;

import ass.manotoma.webserver01.io.HttpRequestReader;
import ass.manotoma.webserver01.server.support.HttpServerTask;
import ass.manotoma.webserver01.server.support.ServerTask;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class WebServer implements Server {
    
    private static final int PORT = 4444;
    public static final Logger LOG = LoggerFactory.getLogger(WebServer.class);

    public void serve() {
        LOG.info("Launching web server..");
        // listener socket
        ServerSocket server = null;
        try {
            LOG.info("Using port [{}]..", PORT);
            server = new ServerSocket(PORT);
            LOG.info("Server bound to port [{}] succesfully..", PORT);
        } catch (IOException ex) {
            LOG.error("An error occured during binding server on port [{}]: {}", PORT, ex);
            LOG.info("Shuting down..");
            System.exit(1);
        }
        // client connection socket
        Socket client = null;

        while (true) {
            try {
                LOG.info("Waiting for the clients' requests on the address: [{}/{}]...", InetAddress.getLocalHost().getHostAddress(), server.getLocalPort());
                client = server.accept();
                LOG.info("Accepted connection from client [{}].", client.getInetAddress().getHostAddress());
                
                ServerTask task = new HttpServerTask(client, new HttpRequestReader(client.getInputStream()));
                task.run();
                
            } catch (IOException e) {
                LOG.error("Fail to accept connection from client [{}].", client.getInetAddress().getHostAddress());
            }
        }
    }
    
}
