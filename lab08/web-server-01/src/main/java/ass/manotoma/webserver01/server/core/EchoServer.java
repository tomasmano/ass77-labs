package ass.manotoma.webserver01.server.core;

import ass.manotoma.webserver01.Bootstrap;
import ass.manotoma.webserver01.io.HttpRequestReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
public class EchoServer implements Server {

    private int port = 4444;
    public static final Logger LOG = LoggerFactory.getLogger(EchoServer.class);
    private ServerSocket server;

    public EchoServer() {
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

                OutputStream os = client.getOutputStream();
                os.write(String.format("Hi client [%s]. How are you?%n", client.getInetAddress().getHostAddress()).getBytes());
                InputStream is = client.getInputStream();

                HttpRequestReader reader = new HttpRequestReader(is);
                String clientMsg = reader.read();
                os.write(String.format("Echoing your response: [%s]. Bye.%n", clientMsg).getBytes());

                os.close();
                is.close();

            } catch (IOException e) {
                LOG.error("Fail to accept connection from client [{}].", client.getInetAddress().getHostAddress());
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
