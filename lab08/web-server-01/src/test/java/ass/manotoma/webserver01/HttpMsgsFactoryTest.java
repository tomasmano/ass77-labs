package ass.manotoma.webserver01;

import ass.manotoma.webserver01.cache.CacheFactory;
import ass.manotoma.webserver01.cache.CacheService;
import ass.manotoma.webserver01.cache.DataHolder;
import ass.manotoma.webserver01.http.exception.BadSyntaxException;
import ass.manotoma.webserver01.http.HttpMsgsFactory;
import ass.manotoma.webserver01.http.HttpRequest;
import ass.manotoma.webserver01.http.HttpResponse;
import ass.manotoma.webserver01.http.util.StatusCode;
import ass.manotoma.webserver01.io.HttpRequestReader;
import ass.manotoma.webserver01.security.SecurityFilter;
import ass.manotoma.webserver01.server.support.HttpProtocolJobCacheableTemplate;
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
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.authentication.BadCredentialsException;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpMsgsFactoryTest {

    public static final Logger LOG = LoggerFactory.getLogger(HttpMsgsFactoryTest.class);
    
    @BeforeClass
    public static void setup() {
        Bootstrap.loadProperties();
    }

    @Test
    public void create_request_test() {
        //given
        String request = "GET /index.xhtml HTTP/1.1\nHost: test.cz\nUser-Agent: firefox\n";
        InputStream is = new ByteArrayInputStream(request.getBytes());
        HttpRequestReader reader = new HttpRequestReader(is);

        //when
        HttpRequest req = HttpMsgsFactory.createRequest(reader);

        //then
        assertEquals(HttpRequest.Method.GET, req.getMethod());
        assertEquals("index.xhtml", req.getTarget().getName());
        assertEquals("test.cz", req.getHeaders().get(HttpRequest.Header.HOST.getFormated()));
        assertEquals("firefox", req.getHeaders().get(HttpRequest.Header.USER_AGENT.getFormated()));
        assertEquals(2, req.getHeaders().size());
    }

    @Test
    public void create_secured_request_test() {
        //given
        String encoded = Base64.encodeBase64String("tomy:pass".getBytes());
        String request = "GET /index.xhtml HTTP/1.1\nHost: test.cz\nUser-Agent: firefox\nAuthorization: Basic "+encoded;
        InputStream is = new ByteArrayInputStream(request.getBytes());
        HttpRequestReader reader = new HttpRequestReader(is);

        //when
        HttpRequest req = HttpMsgsFactory.createRequest(reader);

        //then
        assertEquals(HttpRequest.Method.GET, req.getMethod());
        assertEquals("index.xhtml", req.getTarget().getName());
        assertEquals("test.cz", req.getHeaders().get(HttpRequest.Header.HOST.getFormated()));
        assertEquals("firefox", req.getHeaders().get(HttpRequest.Header.USER_AGENT.getFormated()));
        assertEquals("Basic "+encoded, req.getHeaders().get(HttpRequest.Header.AUTHORIZATION.getFormated()));
        assertEquals(3, req.getHeaders().size());
    }

    @Test
    public void create_response_test() throws Exception {
        //given
        String request = "GET /test.html HTTP/1.1\nHost: test.cz\nUser-Agent: firefox\n";
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
    public void create_response_for_unauthenticated_secured_requests_test() throws Exception {
        //given
        String encoded = Base64.encodeBase64String("tomy:baaad".getBytes());
        String request = "GET /test-secured.html HTTP/1.1\nHost: test.cz\nUser-Agent: firefox\nAuthorization: Basic " + encoded;
        InputStream is = new ByteArrayInputStream(request.getBytes());
        HttpRequestReader reader = new HttpRequestReader(is);

        //when
        HttpRequest req = HttpMsgsFactory.createRequest(reader);
        SecurityFilter filter = SecurityFilter.getInstance();
        HttpRequest filtered = req;
        try {
            filter.preProcess(req);
        } catch (BadCredentialsException e) {

            req.setSecuredTarget(true);
            req.setIsAuthenticated(false);

            //then
            HttpResponse res = HttpMsgsFactory.createResponse(filtered);
            assertEquals(StatusCode._401, res.getStatusCode());
            assertEquals(Bootstrap.properties.getProperty("security_realm"), res.getHeaders().get(HttpResponse.Header.WWW_AUTHENTICATE));
        }
    }

    @Test
    public void use_cached_response_test() throws Exception {
        // cache must be enabled
        Assume.assumeTrue(Boolean.parseBoolean(Bootstrap.properties.getProperty("cache")));
        
        //given
        String request = "GET /test.html HTTP/1.1\nHost: test.cz\nUser-Agent: firefox\n";
        InputStream is = new ByteArrayInputStream(request.getBytes());
        HttpRequestReader reader = new HttpRequestReader(is);
        File file = new File("test.html");

        //when
        HttpRequest req = HttpMsgsFactory.createRequest(reader);
        HttpResponse res = HttpMsgsFactory.createResponse(req);
        System.out.println(">>>>>>>>. "+CacheFactory.getCache());
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
        String request = "GET /nonexistingfile.html HTTP/1.1\nHost: test.cz\nUser-Agent: firefox\n";
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
        String request = "VERYBADMETHOD /index.xhtml HTTP/1.1\nHost: test.cz\nUser-Agent: firefox\n";
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
