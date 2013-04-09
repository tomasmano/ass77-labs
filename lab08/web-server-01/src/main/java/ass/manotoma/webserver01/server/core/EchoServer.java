package ass.manotoma.webserver01.server.core;

import ass.manotoma.webserver01.io.HttpRequestParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class EchoServer implements Server {
    
    private static final int PORT = 4444;
    public static final Logger LOG = LoggerFactory.getLogger(EchoServer.class);
    
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
                
                OutputStream os = client.getOutputStream();
                os.write(String.format("Hi client [%s]. How are you?%n", client.getInetAddress().getHostAddress()).getBytes());
                InputStream is = client.getInputStream();
                
                HttpRequestParser reader = new HttpRequestParser(is);
                String clientMsg = reader.read();
                os.write(String.format("Echoing your response: [%s]. Bye.%n", clientMsg).getBytes());
                
                os.close();
                is.close();
                
            } catch (IOException e) {
                LOG.error("Fail to accept connection from client [{}].", client.getInetAddress().getHostAddress());
            }
        }
    }
    
}
