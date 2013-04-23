package ass.manotoma.webserver01.http;

import ass.manotoma.webserver01.http.util.StatusCode;
import ass.manotoma.webserver01.io.RequestReader;
import java.io.IOException;
import org.slf4j.LoggerFactory;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import org.slf4j.Logger;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpMsgsFactory {
    
    public static final Logger LOG = LoggerFactory.getLogger(HttpMsgsFactory.class);

    private HttpMsgsFactory() {
        // to prevent instantiation
    }
    
    public static HttpRequest createRequest(RequestReader reader){
        HttpRequest request = null;
        try {
            String firstLine = reader.read();
            LOG.debug("Parsing request..");
            if (isEmpty(firstLine)) {
                throw new BadSyntaxException("No input");
            }
            String[] first = firstLine.split("\\s+");
            request = new HttpRequest(first[0], first[1]);
        } catch (IllegalArgumentException ex) {
            LOG.error("Error occured during parsing: {}", ex);
            throw new BadSyntaxException("Bad syntax");
        } catch (IOException ex) {
            LOG.error("Error occured during parsing: {}", ex);
        } catch (Exception ex) {
            LOG.error("Error occured during parsing: {}", ex);
            throw new BadSyntaxException("Bad syntax");
        }
        return request;
    }

    public static HttpResponse createResponse(HttpRequest input) throws Exception{
        LOG.debug("Creating response.. processing request [{}] ..", input);
        if (input == null) {
            LOG.debug("Request is null, creating 400 response..");
            return new HttpResponseError(StatusCode._400, "unknow");
        }
        if (!input.getTarget().exists()) {
            LOG.debug("Requestested targed doesn't exist, creating 404 response...");
            return new HttpResponseError(StatusCode._404, input.getTarget().getName());
        }
        HttpResponseSuccess response = new HttpResponseSuccess(input.getTarget());
        response.buildResponse();
        return response;
    }
    
    public static HttpResponse createResponse(HttpRequest input, byte[] data) throws Exception{
        LOG.debug("Creating response.. processing request [{}] ..", input);
        if (input == null) {
            LOG.debug("Request is null, creating 400 response..");
            return new HttpResponseError(StatusCode._400, "unknow");
        }
        if (!input.getTarget().exists()) {
            LOG.debug("Requestested targed doesn't exist, creating 404 response...");
            return new HttpResponseError(StatusCode._404, input.getTarget().getName());
        }
        HttpResponseSuccess response = new HttpResponseSuccess(input.getTarget());
        response.setBody(data); // set cached data
        response.setCached(true);
        response.buildResponse();
        return response;
    }
}
