package ass.manotoma.webserver01;

import ass.manotoma.webserver01.cache.CacheFactory;
import ass.manotoma.webserver01.cache.CacheService;
import ass.manotoma.webserver01.server.connector.PoolingCachingWebServer;
import ass.manotoma.webserver01.server.connector.PoolingWebServer;
import ass.manotoma.webserver01.server.connector.Server;
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
    public static final Logger LOG = LoggerFactory.getLogger(Bootstrap.class);

    public static void main(String[] args) {
        // load properties
        try {
            properties.load(Bootstrap.class.getClassLoader().getResourceAsStream(CONFIG_PROPERTIES_LOCATION));
            LOG.info("Using properties: {}", properties.entrySet());
        } catch (IOException ex) {
            LOG.error("An error occured during loading properties: {}", ex);
            System.exit(-1);
        }

        // prepare cache (if requested)
        if (Boolean.parseBoolean(properties.getProperty("cache"))) {
            if (properties.getProperty("cache_method").equals("in_memory")) {
                try {
                    String cacheProviderFQN = properties.getProperty("cache_provider");
                    Class clazz = Bootstrap.class.getClassLoader().loadClass(cacheProviderFQN);
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
