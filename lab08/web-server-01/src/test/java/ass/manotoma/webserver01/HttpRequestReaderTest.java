package ass.manotoma.webserver01;

import ass.manotoma.webserver01.io.HttpRequestParser;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpRequestReaderTest {

    @Test
    public void test_parsing() throws Exception{
        String request = "GET / HTTP/1.1 \nHost: test.cz \nUser-Agent: firefox \n";
        InputStream is = new ByteArrayInputStream(request.getBytes());
        HttpRequestParser reader = new HttpRequestParser(is);
        assertEquals("GET / HTTP/1.1 ", reader.read());
        assertEquals("Host: test.cz ", reader.read());
        assertEquals("User-Agent: firefox ", reader.read());
        assertEquals(null, reader.read());
    }
}
