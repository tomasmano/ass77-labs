package ass.manotoma.webserver01;

import ass.manotoma.webserver01.http.HttpMsgsFactory;
import ass.manotoma.webserver01.http.HttpRequest;
import ass.manotoma.webserver01.http.HttpResponse;
import ass.manotoma.webserver01.http.util.HttpResponsePrinter;
import ass.manotoma.webserver01.http.util.StatusCode;
import ass.manotoma.webserver01.io.HttpRequestReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import static org.junit.Assert.*;
import org.junit.Assume;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpServerTaskTest {

    @Test
    @Ignore
    public void create_response_test() throws Exception{
        //given
        String request = "GET /test.html HTTP/1.1 \nHost: test.cz \nUser-Agent: firefox \n";
        InputStream is = new ByteArrayInputStream(request.getBytes());
        HttpRequestReader reader = new HttpRequestReader(is);
        HttpRequest req = HttpMsgsFactory.createRequest(reader);
        HttpResponse res = HttpMsgsFactory.createResponse(req);
        
        //when
        String response = HttpResponsePrinter.getAsString(res);
        
        //then
        File exp = new File("expected_response");
        String responseExp = IOUtils.toString(new FileInputStream(exp));
        Assume.assumeTrue(exp.exists());
        assertEquals(responseExp, response);
        assertTrue(StringUtils.contains(response, responseExp));
    }

 
}
