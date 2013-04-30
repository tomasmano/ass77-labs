package ass.manotoma.webserver01.server.connector;

import ass.manotoma.webserver01.Bootstrap;
import ass.manotoma.webserver01.server.support.HttpServerJobCacheableTemplate;
import ass.manotoma.webserver01.server.support.ServerTask;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class PoolingCachingWebServer implements Server {

    private int port = 4444; //default value
    private int poolSize = 10; //default value
    public static final Logger LOG = LoggerFactory.getLogger(PoolingCachingWebServer.class);
    private ExecutorService executors;
    private ServerSocket server;

    public PoolingCachingWebServer() {
        init();
    }

    public void init() {
        // set properties
        LOG.info("Initializing {}..", this.getClass().getSimpleName());
        port = new Integer(Bootstrap.properties.getProperty("port"));
        LOG.info("Will use port [{}]", port);
        poolSize = new Integer(Bootstrap.properties.getProperty("pool_size"));
        LOG.info("Will use pool size [{}]", poolSize);
        executors = Executors.newFixedThreadPool(poolSize);

        // launch server
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

        while (true) {
            Socket client = null;
            try {
                LOG.debug("Waiting for the clients' requests on the address: [{}/{}]...", InetAddress.getLocalHost().getHostAddress(), server.getLocalPort());
                client = server.accept();
                LOG.debug("Accepted connection from client [{}].", client.getInetAddress().getHostAddress());

                executors.submit(new ServerTask(
                            new HttpServerJobCacheableTemplate(
                                client.getInputStream(), 
                                client.getOutputStream())
                            )
                        );

            } catch (IOException e) {
                LOG.error("Fail to accept connection from client: {}", e);
            } finally {
//                IOUtils.closeQuietly(client);
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
