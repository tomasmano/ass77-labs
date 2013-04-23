package ass.manotoma.webserver01;

import ass.manotoma.webserver01.cache.CacheFactory;
import ass.manotoma.webserver01.cache.CacheService;
import ass.manotoma.webserver01.server.core.PoolingCachingWebServer;
import ass.manotoma.webserver01.server.core.PoolingWebServer;
import ass.manotoma.webserver01.server.core.Server;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bootstrap for server.
 *
 * @author Tomas Mano
 */
public class Bootstrap {

    public static Properties properties = new Properties();
    public static final String CONFIG_PROPERTIES_LOCATION = "server-config.properties";
    private static final String IN_MEMORY_CACHE_SERVICE_CLASS = "ass.manotoma.webserver01.cache.InMemoryCacheService";
    public static final Logger LOG = LoggerFactory.getLogger(Bootstrap.class);

    public static void main(String[] args) {
        // load properties
        try {
            properties.load(Bootstrap.class.getClassLoader().getResourceAsStream(CONFIG_PROPERTIES_LOCATION));
        } catch (IOException ex) {
            LOG.error("An error occured during loading properties: {}", ex);
        }

        // prepare cache
        if (Boolean.parseBoolean(properties.getProperty("cache"))) {
            if (properties.getProperty("cache_method").equals("in_memory")) {
                try {
                    Class clazz = Bootstrap.class.getClassLoader().loadClass(IN_MEMORY_CACHE_SERVICE_CLASS);
                    Constructor<CacheService> constr = clazz.getDeclaredConstructor(new Class[0]);
                    constr.setAccessible(true);
                    CacheService instance = constr.newInstance(new Object[0]);
                    Field field = CacheFactory.class.getDeclaredField("cache");
                    field.setAccessible(true);
                    field.set(null, instance);
                } catch (InstantiationException ex) {
                    LOG.error("Couldn't instantiate class: {}", ex);
                } catch (IllegalAccessException ex) {
                    LOG.error("An exception occured during instantiaion: {}", ex);
                } catch (ClassNotFoundException ex) {
                    LOG.error("An exception occured during instantiaion: {}", ex);
                } catch (NoSuchMethodException ex) {
                    LOG.error("An exception occured during instantiaion: {}", ex);
                } catch (NoSuchFieldException ex) {
                    LOG.error("No field [cache] found in [{}]: {}", ex, CacheFactory.class.getCanonicalName());
                } catch (InvocationTargetException ex) {
                    LOG.error("An exception occured during instantiaion: {}", ex);
                }
            } else {
                throw new Error("Caching enabled, but no valid caching method specified.");
            }
        }
        
        // create server instance
        Server server = null;
        if (Boolean.parseBoolean(properties.getProperty("cache"))) {
            server = new PoolingCachingWebServer();
        } else {
            // default
            server = new PoolingWebServer();
        }
        server.serve();
    }
}
