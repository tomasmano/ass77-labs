package ass.manotoma.webserver01;

/**
 * 
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpServerTask implements ServerTask {
    
    private ServerJobTemplate template;
    
    private byte[] bytes;

    public HttpServerTask() {
    }

    public HttpServerTask(ServerJobTemplate template) {
        this.template = template;
    }

    public HttpServerTask(ServerJobTemplate template, byte[] bytes) {
        this.template = template;
        this.bytes = bytes;
    }

    public void processConnection() {
        Request req = template.parse(bytes);
        template.preProcess(req);
        template.serve(req);
    }
    
}
