package ass.manotoma.webserver01.server.support;

import ass.manotoma.webserver01.server.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Server task. Command object.
 * 
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class ServerTask implements Runnable {
    
    public static final Logger LOG = LoggerFactory.getLogger(ServerTask.class);
    
    private ServerJobTemplate template;

    public ServerTask(ServerJobTemplate template) {
        this.template = template;
    }
    
    public void run() {
        process();
    }

    public void process() {
        long start = System.currentTimeMillis();
        Response res = template.doTemplate();
        long end = System.currentTimeMillis();
        long diff = end - start;
        LOG.info("Job finished [{} ms]: {}", diff, res);
    }
    
}
