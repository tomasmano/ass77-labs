package ass.manotoma.webserver01.server.support;

import ass.manotoma.webserver01.server.support.sender.HttpResponseSender;
import ass.manotoma.webserver01.http.HttpRequest;
import ass.manotoma.webserver01.http.HttpMsgsFactory;
import ass.manotoma.webserver01.http.HttpResponse;
import ass.manotoma.webserver01.io.HttpRequestReader;
import ass.manotoma.webserver01.io.RequestReader;
import java.io.InputStream;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents server job template. Example of Template Pattern.
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpProtocolTemplate extends ProtocolTemplate<HttpRequest, HttpResponse> {

    public static final Logger LOG = LoggerFactory.getLogger(HttpProtocolTemplate.class);

    public HttpProtocolTemplate(InputStream input, OutputStream output) {
        super(new HttpRequestReader(input), output);
    }

    public HttpRequest parse(RequestReader parser) {
        HttpRequest req = HttpMsgsFactory.createRequest(parser);
        return req;
    }

    public void preProcess(HttpRequest req) {
    }

    public HttpResponse serve(HttpRequest req) {
        LOG.debug("Serving: {}", req);
        HttpResponse res = HttpMsgsFactory.createResponse(req);
        send(res);
        return res;
    }

    public void postProcess(HttpRequest req, HttpResponse res) {
    }

    private void send(HttpResponse res) {
        HttpResponseSender.getInstance().send(res, getOutputStream());
    }
}
