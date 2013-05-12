package ass.manotoma.webserver01.http;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.io.File;

/**
 * Creates and manages HttpResponse objects. Ensures that HttpResponses
 * (flyweights) are shared properly. When a client requests a response, the
 * HttpResponsePool object supplies an existing instance or creates one, if none
 * exists.
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpResponsePool {

    private static Cache<File, HttpResponse> cache = CacheBuilder.newBuilder().softValues().build();

    private HttpResponsePool() {
    }

    /**
     * Supplies an existing instance or creates one, if none exists.
     *
     * @param key target of reponse
     * @return response
     */
    public static HttpResponse getRespose(File key) {
        HttpResponse found = null;
        if ((found = cache.getIfPresent(key)) == null) {
            found = new HttpResponseSuccess(key);
            cache.put(key, found);
        }
        return found;
    }
}
