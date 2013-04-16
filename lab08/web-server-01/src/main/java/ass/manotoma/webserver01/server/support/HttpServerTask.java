package ass.manotoma.webserver01.server.support;

import ass.manotoma.webserver01.http.HttpResponse;
import ass.manotoma.webserver01.http.Request;
import ass.manotoma.webserver01.http.Response;
import ass.manotoma.webserver01.io.HttpRequestReader;
import ass.manotoma.webserver01.io.HttpResponseOutputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpServerTask implements ServerTask, Runnable {
    
    public static final Logger LOG = LoggerFactory.getLogger(HttpServerTask.class);
    
    private Socket client;
    
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

    public HttpServerTask(Socket client, HttpRequestReader parser) {
        this.client = client;
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
        send(res);
        LOG.debug("Job finished: Request [{}] processing succesfully finished", req);
    }
    
    public void send(Response res) {
        LOG.debug("Sending response [{}].. ", res);
        HttpResponseOutputStream httpOutputStream = null;
        try {
            httpOutputStream = new HttpResponseOutputStream(new BufferedOutputStream(client.getOutputStream()));
            httpOutputStream.write((HttpResponse) res);
            httpOutputStream.close();

        } catch (Exception ex) {
            LOG.error("An error occured while sending response: " + ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }
    
}
