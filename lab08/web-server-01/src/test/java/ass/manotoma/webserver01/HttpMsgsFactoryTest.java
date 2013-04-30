package ass.manotoma.webserver01;

import ass.manotoma.webserver01.cache.CacheFactory;
import ass.manotoma.webserver01.cache.CacheService;
import ass.manotoma.webserver01.cache.DataHolder;
import ass.manotoma.webserver01.http.BadSyntaxException;
import ass.manotoma.webserver01.http.HttpMsgsFactory;
import ass.manotoma.webserver01.http.HttpRequest;
import ass.manotoma.webserver01.http.HttpResponse;
import ass.manotoma.webserver01.http.util.StatusCode;
import ass.manotoma.webserver01.io.HttpRequestReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import org.apache.commons.io.IOUtils;
import static org.junit.Assert.*;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpMsgsFactoryTest {

    public static final Logger LOG = LoggerFactory.getLogger(HttpMsgsFactoryTest.class);

    @BeforeClass
    public static void setup() {
        Properties properties = new Properties();
        // load properties
        try {
            properties.load(Bootstrap.class.getClassLoader().getResourceAsStream(Bootstrap.CONFIG_PROPERTIES_LOCATION));
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
    }

    @Test
    public void create_request_test() {
        //given
        String request = "GET /index.xhtml HTTP/1.1 \nHost: test.cz \nUser-Agent: firefox \n";
        InputStream is = new ByteArrayInputStream(request.getBytes());
        HttpRequestReader reader = new HttpRequestReader(is);

        //when
        HttpRequest req = HttpMsgsFactory.createRequest(reader);

        //then
        assertEquals(HttpRequest.Method.GET, req.getMethod());
        assertEquals("index.xhtml", req.getTarget().getName());
    }

    @Test
    public void create_response_test() throws Exception {
        //given
        String request = "GET /test.html HTTP/1.1 \nHost: test.cz \nUser-Agent: firefox \n";
        InputStream is = new ByteArrayInputStream(request.getBytes());
        HttpRequestReader reader = new HttpRequestReader(is);
        File file = new File("test.html");

        //when
        HttpRequest req = HttpMsgsFactory.createRequest(reader);
        HttpResponse res = HttpMsgsFactory.createResponse(req);

        //then
        Assume.assumeTrue(file.exists());
        byte[] bytes = IOUtils.toByteArray(new FileInputStream(file));
        assertEquals(StatusCode._200, res.getStatusCode());
        assertEquals(file.getPath(), res.getTargetName());
        assertEquals("text/html; charset=UTF-8", res.getHeaders().get(HttpResponse.Header.CONTENT_TYPE));
        assertArrayEquals(bytes, res.getBody());
    }

    @Test
    public void use_cached_response_test() throws Exception {
        //given
        String request = "GET /test.html HTTP/1.1 \nHost: test.cz \nUser-Agent: firefox \n";
        InputStream is = new ByteArrayInputStream(request.getBytes());
        HttpRequestReader reader = new HttpRequestReader(is);
        File file = new File("test.html");

        //when
        HttpRequest req = HttpMsgsFactory.createRequest(reader);
        HttpResponse res = HttpMsgsFactory.createResponse(req);
        CacheFactory.getCache().store(req.getTarget().getPath(), new DataHolder(res.getBody()));
        HttpResponse resCached = HttpMsgsFactory.createResponse(req, CacheFactory.getCache().load(req.getTarget().getPath()).getBytes());

        //then
        Assume.assumeTrue(file.exists());
        byte[] bytes = IOUtils.toByteArray(new FileInputStream(file));
        assertEquals(StatusCode._200, res.getStatusCode());
        assertEquals(StatusCode._200, resCached.getStatusCode());
        assertEquals(file.getPath(), res.getTargetName());
        assertEquals(file.getPath(), resCached.getTargetName());
        assertEquals("text/html; charset=UTF-8", res.getHeaders().get(HttpResponse.Header.CONTENT_TYPE));
        assertEquals("text/html; charset=UTF-8", resCached.getHeaders().get(HttpResponse.Header.CONTENT_TYPE));
        assertArrayEquals(bytes, res.getBody());
        assertArrayEquals(bytes, resCached.getBody());
    }

    @Test
    public void create_response_when_not_found_test() throws Exception {
        //given
        String request = "GET /nonexistingfile.html HTTP/1.1 \nHost: test.cz \nUser-Agent: firefox \n";
        InputStream is = new ByteArrayInputStream(request.getBytes());
        HttpRequestReader reader = new HttpRequestReader(is);

        //when
        HttpRequest req = HttpMsgsFactory.createRequest(reader);
        HttpResponse res = HttpMsgsFactory.createResponse(req);

        //then
        assertEquals(StatusCode._404, res.getStatusCode());
        assertEquals("nonexistingfile.html", res.getTargetName());
    }

    @Test(expected = BadSyntaxException.class)
    public void bad_syntax_test() {
        //given
        String request = "VERYBADMETHOD /index.xhtml HTTP/1.1 \nHost: test.cz \nUser-Agent: firefox \n";
        InputStream is = new ByteArrayInputStream(request.getBytes());
        HttpRequestReader reader = new HttpRequestReader(is);

        //when
        HttpRequest req = HttpMsgsFactory.createRequest(reader);

        //then
    }

    @Test(expected = BadSyntaxException.class)
    public void read_empty_input_test() {
        //given
        String request = "";
        InputStream is = new ByteArrayInputStream(request.getBytes());
        HttpRequestReader reader = new HttpRequestReader(is);

        //when
        HttpRequest req = HttpMsgsFactory.createRequest(reader);

        //then
    }
}
