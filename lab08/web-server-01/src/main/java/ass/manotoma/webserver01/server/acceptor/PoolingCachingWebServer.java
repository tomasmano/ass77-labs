package ass.manotoma.webserver01.server.acceptor;

import ass.manotoma.webserver01.Bootstrap;
import ass.manotoma.webserver01.server.support.HttpProtocolCacheableTemplate;
import ass.manotoma.webserver01.server.support.ServerTask;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Waits for and accepts incoming requests. It encapsulates the different
 * concurrency policies for simultaneous access etc.
 * 
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class PoolingCachingWebServer implements Server {

    private int port = 4444; //default value
    private int poolSize = 10; //default value
    private int backlog = 10; //default value
    public static final Logger LOG = LoggerFactory.getLogger(PoolingCachingWebServer.class);
    private ExecutorService executors;
    private ServerSocket server;

    public PoolingCachingWebServer() {
        init();
    }

    public void init() {
        // set properties 
        LOG.info("Initializing the {}..", this.getClass().getSimpleName());
        port = new Integer(Bootstrap.properties.getProperty("port"));
        LOG.info("Will use the port [{}]", port);
        backlog = new Integer(Bootstrap.properties.getProperty("backlog"));
        LOG.info("Will use the backlog size [{}]", backlog);
        poolSize = new Integer(Bootstrap.properties.getProperty("pool_size"));
        LOG.info("Will use the pool size [{}]", poolSize);
        executors = Executors.newFixedThreadPool(poolSize);

        // launch server
        LOG.info("Launching the web server now..");
        try {
            server = new ServerSocket(port, backlog);
            LOG.info("Server bound to the port [{}] succesfully.", port);
            LOG.info("Waiting for the clients' requests on the address: [{}/{}]...", InetAddress.getLocalHost().getHostAddress(), server.getLocalPort());
        } catch (IOException ex) {
            LOG.error("An error occured during binding server on port [{}]: {}", port, ex);
            LOG.info("Shuting down..");
            System.exit(1);
        }
    }

    public void serve() {

        while (true) {
            Socket client = null;
            try {
                client = server.accept();
                LOG.debug("Accepted connection from client [{}].", client.getInetAddress().getHostAddress());

                executors.submit(new ServerTask(
                            new HttpProtocolCacheableTemplate(
                                client.getInputStream(), 
                                client.getOutputStream()),
                            client)
                        );

            } catch (IOException e) {
                LOG.error("Fail to accept connection from client: {}", e);
            } 
        }

    }

    public void stop() {
        //Stop the executor service.
        executors.shutdownNow();
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
