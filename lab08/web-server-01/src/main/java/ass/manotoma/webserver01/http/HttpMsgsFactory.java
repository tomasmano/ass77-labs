package ass.manotoma.webserver01.http;

import ass.manotoma.webserver01.http.exception.BadSyntaxException;
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

    public static HttpRequest createRequest(RequestReader reader) {
        HttpRequest request = null;
        try {
            String firstLine = reader.read();
            LOG.debug("Parsing request..");
            if (isEmpty(firstLine)) {
                throw new BadSyntaxException("No input");
            }
            // 1. Read the first line
            String[] first = firstLine.split("\\s+");
            String targetName = first[1];
            request = new HttpRequest(first[0], targetName);
            // 2. Read the request header line by line till you got the blank line, for each header line parse [fieldName: fieldValue]
            String line = null;
            String[] header;
            while(!isEmpty(line = reader.read())){
                header = line.split(": ");
                request.addHeader(header[0], header[1]);
            }
            // 3. Read the entity body (not implemented, we don't support POST requests)
            // nothing
            LOG.debug("Parsing finished.");
        } catch (Exception ex) {
            LOG.error("Error occured during parsing: {}", ex);
            throw new BadSyntaxException("Bad syntax");
        } 
        return request;
    }

    /**
     * Create response from the given request.
     *
     * @param input input req
     * @return HttpResponse
     */
    public static HttpResponse createResponse(HttpRequest input) {
        HttpResponseSuccess response = new HttpResponseSuccess(input.getTarget());
        return response;
    }

    /**
     * Create response with the given data (usually retrieved from cache).
     * 
     * @param input input req
     * @param data cached data
     * @return HttpResponse
     */
    public static HttpResponse createResponse(HttpRequest input, byte[] data) {
        HttpResponse res = createResponse(input);
        res.setBody(data); // set cached data
        res.setCached(true);
        return res;
    }
 
    /**
     * Create error response for the given request with given code.
     * 
     * @param code input req
     * @param data cached data
     * @return HttpResponseError
     */
    public static HttpResponse createErrorResponse(StatusCode code, String targetName) {
        HttpResponse res = new HttpResponseError(code, targetName, StatusCode.Title.valueOf(code.name()).getText(), StatusCode.Page.valueOf(code.name()).getText());
        return res;
    }

}
