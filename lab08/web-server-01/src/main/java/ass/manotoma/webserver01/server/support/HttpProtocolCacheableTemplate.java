package ass.manotoma.webserver01.server.support;

import ass.manotoma.webserver01.server.support.sender.HttpResponseSender;
import ass.manotoma.webserver01.cache.CacheFactory;
import ass.manotoma.webserver01.cache.CacheService;
import ass.manotoma.webserver01.cache.ContentHolder;
import ass.manotoma.webserver01.cache.HttpResponseStorage;
import ass.manotoma.webserver01.http.HttpRequest;
import ass.manotoma.webserver01.http.HttpMsgsFactory;
import ass.manotoma.webserver01.http.HttpResponse;
import ass.manotoma.webserver01.io.HttpRequestReader;
import ass.manotoma.webserver01.io.RequestReader;
import ass.manotoma.webserver01.security.HttpSecurityFilter;
import ass.manotoma.webserver01.server.HttpContentLoader;
import ass.manotoma.webserver01.server.processor.provider.HttpPostProcessorsProvider;
import ass.manotoma.webserver01.server.processor.provider.HttpPreProcessorsProvider;
import ass.manotoma.webserver01.server.processor.provider.PostProcessorsProvider;
import ass.manotoma.webserver01.server.processor.provider.PreProcessorsProvider;
import java.io.InputStream;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents server job template. Example of Template Pattern.
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpProtocolCacheableTemplate extends ServerJobTemplate<HttpRequest, HttpResponse> {

    public static final Logger LOG = LoggerFactory.getLogger(HttpProtocolCacheableTemplate.class);
    
    // Cache
    private static CacheService cache = CacheFactory.getCache();
    
    // Processors
    private static PreProcessorsProvider<HttpRequest> preProcessors = HttpPreProcessorsProvider.getInstance();
    private static PostProcessorsProvider<HttpRequest, HttpResponse> postProcessors = HttpPostProcessorsProvider.getInstance();
    
    // Add custom processors
    static{
        // pre
        preProcessors.add(HttpContentLoader.getInstance()); // it loads content to request
        preProcessors.add(HttpSecurityFilter.getInstance()); // it handles authentication if necessary
        // post
        postProcessors.add(HttpResponseStorage.getInstance()); // it stores data to cache if necessary
    }

    public HttpProtocolCacheableTemplate(InputStream input, OutputStream output) {
        // wrap InputStream to HttpRequestReader
        super(new HttpRequestReader(input), output);
    }
    
    //////////  Individual steps (placeholders)  //////////

    public HttpRequest parse(RequestReader parser) {
        return HttpMsgsFactory.createRequest(parser);
    }

    public void preProcess(HttpRequest req) {
        preProcessors.preProcess(req);
    }

    public HttpResponse serve(HttpRequest req) {
        LOG.debug("Serving: {}", req);
        HttpResponse res = lookupCacheOrLoadUncached(req);
        send(res);
        return res;
    }

    public void postProcess(HttpRequest req, HttpResponse res) {
        postProcessors.postProcess(req, res);
    }
    
    //////////  Helper method  //////////

    private void send(HttpResponse res) {
        HttpResponseSender.getInstance().send(res, getOutputStream());
    }

    private HttpResponse lookupCacheOrLoadUncached(HttpRequest req) {
        ContentHolder content = cache.load(req.getTarget().getPath());
        if (content != null) {
            // data not null => create response WITH content from cache
            LOG.debug("Returning response with data [{} bytes] from cache", content.getBytes().length);
            return HttpMsgsFactory.createResponse(req, content.getBytes());
        } else {
            return HttpMsgsFactory.createResponse(req);
        }
    }
}
