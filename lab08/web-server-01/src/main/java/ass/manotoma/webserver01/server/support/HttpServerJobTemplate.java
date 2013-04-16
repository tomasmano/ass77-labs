package ass.manotoma.webserver01.server.support;

import ass.manotoma.webserver01.http.BadSyntaxException;
import ass.manotoma.webserver01.http.Request;
import ass.manotoma.webserver01.http.Response;
import ass.manotoma.webserver01.http.HttpRequest;
import ass.manotoma.webserver01.http.HttpMsgsFactory;
import ass.manotoma.webserver01.http.HttpResponse;
import ass.manotoma.webserver01.http.util.HttpResponsePrinter;
import ass.manotoma.webserver01.io.RequestReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents server job template. Example of Template Pattern.
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpServerJobTemplate implements ServerJobTemplate {
    
    public static final Logger LOG = LoggerFactory.getLogger(HttpServerJobTemplate.class);
    
    public Request parse(RequestReader parser) {
        HttpRequest req = null;
        try {
            req = HttpMsgsFactory.createRequest(parser);
        } catch (BadSyntaxException e) {
            
        }
        return req;
    }

    public void preProcess(Request req) {
    }

    public Response serve(Request req){
        LOG.debug("Serving request {}..", req);
        HttpResponse res = null;
        try {
            res = HttpMsgsFactory.createResponse((HttpRequest) req);
        } catch (Exception ex) {
            LOG.error("An error occured during serving: {}", ex);
        }
        return res;
    }

    public void postProcess(Response res) {
//        HttpResponsePrinter.print((HttpResponse) res);
    }
}
