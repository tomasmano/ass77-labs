package ass.manotoma.webserver01.server.support;

import ass.manotoma.webserver01.server.model.Response;
import java.net.Socket;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Server task. Command object.
 * 
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class ServerTask implements Runnable {
    
    public static final Logger LOG = LoggerFactory.getLogger(ServerTask.class);
    
    private ProtocolTemplate template;
    private Socket client;

    public ServerTask(ProtocolTemplate template, Socket client) {
        this.template = template;
        this.client = client;
    }
    
    public void run() {
        process();
    }

    public void process() {
        long start = System.currentTimeMillis();
        Response res = template.doTemplate();
        IOUtils.closeQuietly(client);
        long end = System.currentTimeMillis();
        long diff = end - start;
        LOG.info("Finished [{} ms]: {}", diff, res);
    }
    
}
