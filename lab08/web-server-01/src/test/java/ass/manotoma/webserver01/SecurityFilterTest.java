package ass.manotoma.webserver01;

import ass.manotoma.webserver01.http.HttpMsgsFactory;
import ass.manotoma.webserver01.http.HttpRequest;
import ass.manotoma.webserver01.io.HttpRequestReader;
import ass.manotoma.webserver01.security.SecurityFilter;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.apache.commons.codec.binary.Base64;
import org.junit.BeforeClass;
import static org.junit.Assert.*;    
import org.junit.Test;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class SecurityFilterTest {

    @BeforeClass
    public static void setup() {
        Bootstrap.loadProperties();
    }

    @Test
    public void test_successfull_authentication() {
        //given
        String encoded = Base64.encodeBase64String("tomy:pass".getBytes());
        String request = "GET /test-secured.html HTTP/1.1\nHost: test.cz\nUser-Agent: firefox\nAuthorization: Basic " + encoded;
        InputStream is = new ByteArrayInputStream(request.getBytes());
        HttpRequestReader reader = new HttpRequestReader(is);

        //when
        HttpRequest req = HttpMsgsFactory.createRequest(reader);
        SecurityFilter filer = SecurityFilter.getInstance();
        HttpRequest filtered = filer.preProcess(req);
        
        //then
        assertTrue(filtered.isSecuredTarget());
        assertTrue(filtered.isAuthenticated());
    }
}
