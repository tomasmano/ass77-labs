package ass.manotoma.webserver01.cache.provider;

import ass.manotoma.webserver01.cache.CacheService;
import ass.manotoma.webserver01.cache.ContentHolder;
import ass.manotoma.webserver01.cache.SoftHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class InMemorySoftMapCacheService implements CacheService {

    public static final Logger LOG = LoggerFactory.getLogger(InMemorySoftMapCacheService.class);
    private Map<String, ContentHolder> cache = new SoftHashMap<String, ContentHolder>();

    private InMemorySoftMapCacheService() {
        LOG.info("Instantianting {} using [{}] internally..", this.getClass().getSimpleName(), cache.getClass().getCanonicalName());
    }

    public ContentHolder load(String filename) {
        return cache.get(filename);
    }

    public void store(String filename, ContentHolder data) {
        LOG.info("Storing [{}, size: {} bytes] to {} ..", new Object[]{filename, data.getBytes().length, this.getClass().getSimpleName()});
        cache.put(filename, data);
    }

    public void remove(String filename) {
//        cache.remove(filename);
    }
}
