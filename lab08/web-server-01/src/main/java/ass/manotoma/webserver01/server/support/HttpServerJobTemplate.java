package ass.manotoma.webserver01.server.support;

import ass.manotoma.webserver01.cache.CacheFactory;
import ass.manotoma.webserver01.cache.CacheService;
import ass.manotoma.webserver01.cache.DataHolder;
import ass.manotoma.webserver01.http.exception.BadSyntaxException;
import ass.manotoma.webserver01.http.HttpRequest;
import ass.manotoma.webserver01.http.HttpMsgsFactory;
import ass.manotoma.webserver01.http.HttpResponse;
import ass.manotoma.webserver01.http.util.StatusCode;
import ass.manotoma.webserver01.io.HttpRequestReader;
import ass.manotoma.webserver01.io.HttpResponseOutputStream;
import ass.manotoma.webserver01.io.RequestReader;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents server job template. Example of Template Pattern.
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpServerJobTemplate extends ServerJobTemplate {

    public static final Logger LOG = LoggerFactory.getLogger(HttpServerJobTemplate.class);

    public HttpServerJobTemplate(InputStream input, OutputStream output) {
        super(new HttpRequestReader(input), output);
    }

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

    public Response serve(Request req) {
        LOG.debug("Serving request {}..", req);
        HttpResponse res = null;
        try {
            res = HttpMsgsFactory.createResponse((HttpRequest) req);
            send(res);
        } catch (Exception ex) {
            LOG.error("An error occured during serving: {}", ex);
        }
        return res;
    }

    public void postProcess(Response res) {
//        HttpResponsePrinter.print((HttpResponse) res);
    }

    private void send(Response res) {
        LOG.debug("Sending response [{}].. ", res);
        HttpResponseOutputStream httpOutputStream = null;
        try {
            httpOutputStream = new HttpResponseOutputStream(new BufferedOutputStream(getOutputStream()));
            httpOutputStream.write((HttpResponse) res);
            httpOutputStream.close();
        } catch (Exception ex) {
            LOG.error("An error occured while sending response: " + ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }
}
