package ass.manotoma.webserver01.server.support;

import ass.manotoma.webserver01.http.Request;
import ass.manotoma.webserver01.http.Response;
import ass.manotoma.webserver01.io.HttpRequestReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpServerTask implements ServerTask, Runnable {
    
    public static final Logger LOG = LoggerFactory.getLogger(HttpServerTask.class);
    
    private ServerJobTemplate template = new HttpServerJobTemplate();
    
    private HttpRequestReader parser;

    public HttpServerTask() {
    }

    public HttpServerTask(HttpRequestReader parser) {
        this.parser = parser;
    }

    public HttpServerTask(ServerJobTemplate template) {
        this.template = template;
    }

    public HttpServerTask(ServerJobTemplate template, HttpRequestReader parser) {
        this.template = template;
        this.parser = parser;
    }

    public void run() {
        process();
    }

    public void process() {
        Request req = template.parse(parser);
        template.preProcess(req);
        Response res = template.serve(req);
        template.postProcess(res);
        LOG.debug("Job finished. Request [{}] processing finished", req);
    }
    
}
