package ass.manotoma.webserver01.http;

import ass.manotoma.webserver01.io.RequestReader;
import java.io.IOException;
import org.slf4j.LoggerFactory;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpMsgsFactory {
    
    public static final org.slf4j.Logger LOG = LoggerFactory.getLogger(HttpMsgsFactory.class);

    private HttpMsgsFactory() {
        // to prevent instantiation
    }
    
    public static HttpRequest createRequest(RequestReader reader){
        HttpRequest request = null;
        try {
            String firstLine = reader.read();
            if (isEmpty(firstLine)) {
                throw new BadSyntaxException("No input");
            }
            String[] first = firstLine.split("\\s+");
            request = new HttpRequest(first[0], first[1]);
        } catch(IllegalArgumentException ex) {
            throw new BadSyntaxException("Bad syntax");
        }
        catch (IOException ex) {
            LOG.error("Error occured during parsing");
        }
        return request;
    }

    public static HttpResponse createResponse(HttpRequest input){
        return null;
    }
}
