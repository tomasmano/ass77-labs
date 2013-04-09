package ass.manotoma.webserver01;

/**
 * Represents server job template. Example of Template Pattern.
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpServerJobTemplate implements ServerJobTemplate {

    public Request parse(RequestParser parser) {
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
