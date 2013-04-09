package ass.manotoma.webserver01.server.support;

import ass.manotoma.webserver01.http.Request;
import ass.manotoma.webserver01.http.Response;
import ass.manotoma.webserver01.http.HttpRequest;
import ass.manotoma.webserver01.http.HttpMsgsFactory;
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
        return HttpMsgsFactory.createRequest(parser);
    }

    public void preProcess(Request req) {
    }

    public Response serve(Request req) {
        LOG.debug("Serving request {}..", req);
        return HttpMsgsFactory.createResponse((HttpRequest) req);
    }

    public void postProcess(Response req) {
    }
}
