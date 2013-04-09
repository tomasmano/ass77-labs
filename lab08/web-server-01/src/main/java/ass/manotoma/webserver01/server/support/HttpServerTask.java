package ass.manotoma.webserver01.server.support;

import ass.manotoma.webserver01.http.Request;
import ass.manotoma.webserver01.io.HttpRequestReader;

/**
 * 
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpServerTask implements ServerTask {
    
    private ServerJobTemplate template;
    
    private HttpRequestReader parser;

    public HttpServerTask() {
    }

    public HttpServerTask(ServerJobTemplate template) {
        this.template = template;
    }

    public HttpServerTask(ServerJobTemplate template, HttpRequestReader parser) {
        this.template = template;
        this.parser = parser;
    }

    public void processConnection() {
        Request req = template.parse(parser);
        template.preProcess(req);
        template.serve(req);
    }
    
}
