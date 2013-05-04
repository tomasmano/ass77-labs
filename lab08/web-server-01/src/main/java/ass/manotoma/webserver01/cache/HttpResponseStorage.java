package ass.manotoma.webserver01.cache;

import ass.manotoma.webserver01.http.HttpRequest;
import ass.manotoma.webserver01.http.HttpResponse;
import ass.manotoma.webserver01.http.util.StatusCode;
import ass.manotoma.webserver01.server.processor.PostProcessor;

/**
 * Cache the response if it is not cached yet.
 * 
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpResponseStorage implements PostProcessor<HttpRequest, HttpResponse> {
    
    private static CacheService cache = CacheFactory.getCache();
    
    private static PostProcessor<HttpRequest, HttpResponse> INSTANCE = new HttpResponseStorage();
    
    public static PostProcessor<HttpRequest, HttpResponse> getInstance(){
        return INSTANCE;
    }

    public HttpResponse postProcess(HttpRequest req, HttpResponse res) {
        if (res.getStatusCode().equals(StatusCode._200) && !res.isCached()) {
            cache.store(req.getTarget().getPath(), new ContentHolder(res.getBody()));
        }
        return res;
    }
}
