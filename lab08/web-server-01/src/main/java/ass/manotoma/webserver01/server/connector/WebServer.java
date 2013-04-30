package ass.manotoma.webserver01.server.connector;

import ass.manotoma.webserver01.Bootstrap;
import ass.manotoma.webserver01.io.HttpRequestReader;
import ass.manotoma.webserver01.server.support.HttpServerJobTemplate;
import ass.manotoma.webserver01.server.support.ServerTask;
import ass.manotoma.webserver01.server.support.ServerTask;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class WebServer implements Server {

    private int port = 4444;
    public static final Logger LOG = LoggerFactory.getLogger(WebServer.class);
    private ServerSocket server;

    public WebServer() {
        init();
    }

    public void init() {
        // set properties
        LOG.info("Initializing {}..", this.getClass().getSimpleName());
        port = new Integer(Bootstrap.properties.getProperty("port"));
        LOG.info("Will use port [{}]", port);

        // launch
        LOG.info("Launching web server..");
        try {
            LOG.info("Using port [{}]..", port);
            server = new ServerSocket(port);
            LOG.info("Server bound to port [{}] succesfully..", port);
        } catch (IOException ex) {
            LOG.error("An error occured during binding server on port [{}]: {}", port, ex);
            LOG.info("Shuting down..");
            System.exit(1);
        }
    }

    public void serve() {
        Socket client = null;
        while (true) {
            try {
                LOG.info("Waiting for the clients' requests on the address: [{}/{}]...", InetAddress.getLocalHost().getHostAddress(), server.getLocalPort());
                client = server.accept();
                LOG.info("Accepted connection from client [{}].", client.getInetAddress().getHostAddress());

                Runnable task = new ServerTask(
                        new HttpServerJobTemplate(
                        client.getInputStream(),
                        client.getOutputStream()));
                task.run();

            } catch (IOException e) {
                LOG.error("Fail to accept connection from client [{}].", client.getInetAddress().getHostAddress());
            } finally {
//                client.close();
            }
        }
    }

    public void stop() {
        try {
            //Stop accepting requests.
            server.close();
        } catch (IOException e) {
            LOG.error("An error occured in server shutdown: {}", e);
            e.printStackTrace();
        }
        System.exit(0);
    }
}
