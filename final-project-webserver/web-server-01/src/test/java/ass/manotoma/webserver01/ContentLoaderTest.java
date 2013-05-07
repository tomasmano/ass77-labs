package ass.manotoma.webserver01;

import ass.manotoma.webserver01.http.HttpMsgsFactory;
import ass.manotoma.webserver01.http.HttpRequest;
import ass.manotoma.webserver01.http.exception.NotFoundException;
import ass.manotoma.webserver01.io.HttpRequestReader;
import ass.manotoma.webserver01.server.HttpContentLoader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class ContentLoaderTest {

    @Test(expected = NotFoundException.class)
    public void test_not_found() {
        //given
        String request = "GET /index.xhtml HTTP/1.1\nHost: test.cz\nUser-Agent: firefox\n";
        InputStream is = new ByteArrayInputStream(request.getBytes());
        HttpRequestReader reader = new HttpRequestReader(is);

        //when
        HttpRequest req = HttpMsgsFactory.createRequest(reader);

        //when
        HttpContentLoader.getInstance().preProcess(req);

        //then
        // exception
    }

    @Test
    public void test_file_found() {
        //given
        String request = "GET /test.html HTTP/1.1\nHost: test.cz\nUser-Agent: firefox\n";
        InputStream is = new ByteArrayInputStream(request.getBytes());
        HttpRequestReader reader = new HttpRequestReader(is);

        //when
        HttpRequest req = HttpMsgsFactory.createRequest(reader);
        //when
        HttpRequest reqExp = HttpContentLoader.getInstance().preProcess(req);

        //then
        assertTrue(reqExp.getTarget().exists());
        assertEquals("test.html", reqExp.getTarget().getName());
    }
}
