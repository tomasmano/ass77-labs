package ass.manotoma.webserver01.server.support;

import ass.manotoma.webserver01.cache.CacheFactory;
import ass.manotoma.webserver01.cache.CacheService;
import ass.manotoma.webserver01.cache.DataHolder;
import ass.manotoma.webserver01.cache.ResponseStorage;
import ass.manotoma.webserver01.http.exception.BadSyntaxException;
import ass.manotoma.webserver01.http.HttpRequest;
import ass.manotoma.webserver01.http.HttpMsgsFactory;
import ass.manotoma.webserver01.http.HttpResponse;
import ass.manotoma.webserver01.io.HttpRequestReader;
import ass.manotoma.webserver01.io.HttpResponseOutputStream;
import ass.manotoma.webserver01.io.RequestReader;
import ass.manotoma.webserver01.security.SecurityFilter;
import ass.manotoma.webserver01.server.processor.provider.HttpPostProcessorsProvider;
import ass.manotoma.webserver01.server.processor.provider.HttpPreProcessorsProvider;
import ass.manotoma.webserver01.server.processor.provider.PostProcessorsProvider;
import ass.manotoma.webserver01.server.processor.provider.PreProcessorsProvider;
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
public class HttpProtocolJobCacheableTemplate extends ServerJobTemplate<HttpRequest, HttpResponse> {

    public static final Logger LOG = LoggerFactory.getLogger(HttpProtocolJobCacheableTemplate.class);
    
    // Cache
    private static CacheService cache = CacheFactory.getCache();
    
    // Processors
    private static PreProcessorsProvider<HttpRequest> preProcessors = HttpPreProcessorsProvider.getInstance();
    private static PostProcessorsProvider<HttpRequest, HttpResponse> postProcessors = HttpPostProcessorsProvider.getInstance();
    
    // Set up processors
    static{
        preProcessors.add(SecurityFilter.getInstance());
        postProcessors.add(ResponseStorage.getInstance());
    }

    public HttpProtocolJobCacheableTemplate(InputStream input, OutputStream output) {
        // wrap InputStream to HttpRequestReader
        super(new HttpRequestReader(input), output);
    }
    
    //////////  Individual steps (placeholders)  //////////

    public HttpRequest parse(RequestReader parser) {
        HttpRequest req = null;
        try {
            req = HttpMsgsFactory.createRequest(parser);
        } catch (BadSyntaxException e) {
        }
        return req;
    }

    public void preProcess(HttpRequest req) {
        preProcessors.preProcess(req);
    }

    public HttpResponse serve(HttpRequest req) {
        LOG.debug("Serving request {}..", req);
        HttpResponse res = null;
        try {
            DataHolder data = cache.load(req.getTarget().getPath());
            if (data != null) { 
                // data not null - create response with cached data
                res = HttpMsgsFactory.createResponse(req, data.getBytes());
                LOG.debug("Returning response with data [{} bytes] from cache", data.getBytes().length);
            } else {
                res = HttpMsgsFactory.createResponse(req);
            }
            send(res);
        } catch (Exception ex) {
            LOG.error("An error occured during serving: {}", ex);
        }
        return res;
    }

    public void postProcess(HttpRequest req, HttpResponse res) {
        postProcessors.postProcess(req, res);
    }
    
    //////////  Helper method  //////////

    private void send(HttpResponse res) {
        LOG.debug("Sending response [{}].. ", res);
        HttpResponseOutputStream httpOutputStream = null;
        try {
            httpOutputStream = new HttpResponseOutputStream(new BufferedOutputStream(getOutputStream()));
            httpOutputStream.write(res);
            httpOutputStream.close();
        } catch (Exception ex) {
            LOG.error("An error occured while sending response: " + ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }
}
