package ass.manotoma.webserver01.cache.provider;

import ass.manotoma.webserver01.cache.CacheService;
import ass.manotoma.webserver01.cache.ContentHolder;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class InMemoryGuavaCacheService implements CacheService {

    public static final Logger LOG = LoggerFactory.getLogger(InMemoryGuavaCacheService.class);
    private Cache<String, ContentHolder> cache = CacheBuilder.newBuilder().softValues().build();

    private InMemoryGuavaCacheService() {
        LOG.info("Instantianting {} using [{}] internally..", this.getClass().getSimpleName(), cache.getClass().getCanonicalName());
    }

    public ContentHolder load(String filename) {
        return cache.getIfPresent(filename);
    }

    public void store(String filename, ContentHolder data) {
        LOG.info("Storing [{}, size: {} bytes] to {} ..", new Object[]{filename, data.getBytes().length, this.getClass().getSimpleName()});
        cache.put(filename, data);
    }

    public void remove(String filename) {
//        cache.remove(filename);
    }
}
