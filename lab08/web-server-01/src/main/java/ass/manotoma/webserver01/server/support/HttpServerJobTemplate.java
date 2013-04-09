package ass.manotoma.webserver01.server.support;

import ass.manotoma.webserver01.http.Request;
import ass.manotoma.webserver01.http.Response;
import ass.manotoma.webserver01.http.HttpRequest;
import ass.manotoma.webserver01.http.HttpMsgsFactory;
import ass.manotoma.webserver01.io.RequestReader;

/**
 * Represents server job template. Example of Template Pattern.
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpServerJobTemplate implements ServerJobTemplate {

    public Request parse(RequestReader parser) {
        return HttpMsgsFactory.createRequest(parser);
    }

    public void preProcess(Request req) {
    }

    public Response serve(Request req) {
        return HttpMsgsFactory.createResponse((HttpRequest) req);
    }

    public void postProcess(Response req) {
    }
}
