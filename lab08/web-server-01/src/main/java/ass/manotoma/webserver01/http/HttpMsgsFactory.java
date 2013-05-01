package ass.manotoma.webserver01.http;

import ass.manotoma.webserver01.Bootstrap;
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
            request = new HttpRequest(first[0], first[1]);
            // 2. Read the request header line by line till you got the blank line, for each header line parse [fieldName: fieldValue]
            String line = null;
            String[] header;
            while(!isEmpty(line = reader.read())){
                header = line.split(": ");
                request.addHeader(header[0], header[1]);
            }
            // 3. Read the entity body (not implemented, we don't support POST requests)
            // nothing
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

    /**
     * Create response from the given request.
     *
     * @param input input req
     * @return HttpResponse
     */
    public static HttpResponse createResponse(HttpRequest input) {
        LOG.debug("Creating response.. processing request [{}] ..", input);
        if (input == null) {
            LOG.debug("Request is null, creating 400 response..");
            return new HttpResponseError(StatusCode._400, "unknow", Title._400.getText(), Page._400.getText());
        }
        if (!input.getTarget().exists()) {
            LOG.debug("Requestested targed doesn't exist, creating 404 response...");
            return new HttpResponseError(StatusCode._404, input.getTarget().getName(), Title._404.getText(), Page._404.getText());
        }
        if (input.isSecuredTarget() && !input.isAuthenticated()) {
            LOG.debug("Requestested targed is secured and client authentication was not successfull, creating 404 response...");
            return new HttpResponseError(StatusCode._401, input.getTarget().getName(), Title._401.getText(), Page._401.getText()).addHeader(HttpResponse.Header.WWW_AUTHENTICATE, Bootstrap.properties.getProperty("security_realm"));
        }
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

    //////////  Inner Enums  //////////
    
    public enum Title {

        _400("<html><head><title>Bad Request</title></head><body><h1>"), _401("<html><head><title>Authorization Required</title></head><body><h1>"), _404("<html><head><title>Not Found</title></head><body><h1>");
        private final String text;

        private Title(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    public enum Page {

        _400("</h1><p>Web server was unable to understand the request and process it.</p></body></html>"), _401("</h1><p>Authentication required.</p></body></html>"), _404("<html><head><title>Not Found</title></head><body><h1>");
       
        private final String text;

        private Page(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}
