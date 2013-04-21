package ass.manotoma.webserver01.server.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
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
        Response res = template.doTemplate();
        LOG.debug("Job finished: Request processing succesfully finished");
    }
    
}
