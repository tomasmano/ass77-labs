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
import ass.manotoma.webserver01.security.SecurityFilter;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

/**
 * Represents server job template. Example of Template Pattern.
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpServerJobCacheableTemplate extends ServerJobTemplate {

    public static final Logger LOG = LoggerFactory.getLogger(HttpServerJobCacheableTemplate.class);
    private CacheService cache = CacheFactory.getCache();
    private SecurityFilter securityFilter = SecurityFilter.getInstance();

    public HttpServerJobCacheableTemplate(InputStream input, OutputStream output) {
        // wrap input to HttpRequestReader
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
        try {
            securityFilter.filter((HttpRequest) req);
        } catch (BadCredentialsException e) {
        }
    }

    public Response serve(Request r) {
        LOG.debug("Serving request {}..", r);
        HttpResponse res = null;
        try {
            HttpRequest req = (HttpRequest) r;
            DataHolder data = cache.load(req.getTarget().getPath());
            if (data != null) {
                res = HttpMsgsFactory.createResponse(req, data.getBytes());
                LOG.debug("Returning response with data [{} bytes] from cache", data.getBytes().length);
            } else {
                res = HttpMsgsFactory.createResponse(req);
                if (res.getStatusCode().equals(StatusCode._200)) {
                    cache.store(req.getTarget().getPath(), new DataHolder(res.getBody()));
                }
            }
            send(res);
        } catch (Exception ex) {
            LOG.error("An error occured during serving: {}", ex);
        }
        return res;
    }

    public void postProcess(Response res) {
//        HttpResponsePrinter.printToConsole((HttpResponse) res);
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
