package ass.manotoma.webserver01;

/**
 * 
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpServerTask implements ServerTask {
    
    private ServerJobTemplate template;
    
    private HttpRequestParser parser;

    public HttpServerTask() {
    }

    public HttpServerTask(ServerJobTemplate template) {
        this.template = template;
    }

    public HttpServerTask(ServerJobTemplate template, HttpRequestParser parser) {
        this.template = template;
        this.parser = parser;
    }

    public void processConnection() {
        Request req = template.parse(parser);
        template.preProcess(req);
        template.serve(req);
    }
    
}
