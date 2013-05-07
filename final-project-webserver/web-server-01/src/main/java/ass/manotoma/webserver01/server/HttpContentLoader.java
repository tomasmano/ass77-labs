package ass.manotoma.webserver01.server;

import ass.manotoma.webserver01.http.HttpRequest;
import ass.manotoma.webserver01.http.exception.NotFoundException;
import ass.manotoma.webserver01.server.processor.PreProcessor;
import java.io.File;

/**
 * Loads content to http request.
 * 
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpContentLoader implements PreProcessor<HttpRequest> {
    
    private static HttpContentLoader INSTANCE = new HttpContentLoader();

    public HttpRequest preProcess(HttpRequest req) {
        File target = ContentFinder.find(req.getRequestTargetName());
        if (!target.exists()) {
            throw new NotFoundException(req);
        }
        req.setRequestTarget(target);
        return req;
    }

    public static HttpContentLoader getInstance() {
        return INSTANCE;
    }
    
}
