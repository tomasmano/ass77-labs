package ass.manotoma.webserver01;

import ass.manotoma.webserver01.server.core.PoolingWebServer;
import ass.manotoma.webserver01.server.core.Server;
import java.io.IOException;
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
        try {
            properties.load(Bootstrap.class.getClassLoader().getResourceAsStream(CONFIG_PROPERTIES_LOCATION));
        } catch (IOException ex) {
            LOG.error("An error occured during loading properties: {}", ex);
        }
        Server server = new PoolingWebServer();
        server.serve();
    }
}
