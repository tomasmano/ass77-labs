package ass.manotoma.webserver01.cache;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class CacheFactory {
    
    private static CacheService cache;

    private CacheFactory() {
    }

    public static CacheService getCache() {
        return cache;
    }
    
}
