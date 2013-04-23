package ass.manotoma.webserver01.cache;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class InMemoryCacheService implements CacheService {
    
    public static final Logger LOG = LoggerFactory.getLogger(InMemoryCacheService.class);
    
    private Map<String, DataHolder> cache = new HashMap<String, DataHolder>();
    
    private InMemoryCacheService(){
        LOG.info("Instantianting {}..", this.getClass().getSimpleName());
    }

    public DataHolder load(String filename) {
        return cache.get(filename);
    }

    public void store(String filename, DataHolder data) {
        LOG.info("Storing [{}, size: {} bytes] to {} ..", new Object[]{filename, data.getBytes().length, this.getClass().getSimpleName()});
        cache.put(filename, data);
    }

    public void remove(String filename) {
        cache.remove(filename);
    }
    
}
