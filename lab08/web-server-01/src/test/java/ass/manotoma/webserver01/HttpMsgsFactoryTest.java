package ass.manotoma.webserver01;

import ass.manotoma.webserver01.http.BadSyntaxException;
import ass.manotoma.webserver01.http.HttpMsgsFactory;
import ass.manotoma.webserver01.http.HttpRequest;
import ass.manotoma.webserver01.io.HttpRequestReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpMsgsFactoryTest {

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
        assertEquals("/index.xhtml", req.getRequestTarget());
    }

    @Test(expected=BadSyntaxException.class)
    public void bad_syntax_test() {
        //given
        String request = "VERYBADMETHOD /index.xhtml HTTP/1.1 \nHost: test.cz \nUser-Agent: firefox \n";
        InputStream is = new ByteArrayInputStream(request.getBytes());
        HttpRequestReader reader = new HttpRequestReader(is);
        
        //when
        HttpRequest req = HttpMsgsFactory.createRequest(reader);
        
        //then
    }

    @Test(expected=BadSyntaxException.class)
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
