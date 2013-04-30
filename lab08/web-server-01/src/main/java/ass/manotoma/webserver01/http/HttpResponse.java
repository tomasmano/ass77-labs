package ass.manotoma.webserver01.http;

import ass.manotoma.webserver01.server.support.Response;
import ass.manotoma.webserver01.http.util.HeaderBuilder;
import ass.manotoma.webserver01.http.util.StatusCode;
import java.io.OutputStream;
import java.util.Map;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public abstract class HttpResponse implements Response {
    
    public static final String PROTOCOL = "HTTP/1.1";
    private final static String CRLF = "\r\n";
    
    private HeaderBuilder builder = new HeaderBuilder();
    
    //////////  Methods  //////////

    public abstract String getStatusLine();
    
    public abstract StatusCode getStatusCode();
    
    public abstract String getTargetName();

    public abstract Map<Header, String> getHeaders();
    
    public HttpResponse addHeader(Header header, String value) {
        getHeaders().put(header, value);
        return this;
    }

    public String getFormatedHeader() {
        for (Map.Entry<Header, String> entry : getHeaders().entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    public String getFormatedStatusLine() {
        return getStatusLine() + CRLF;
    }

    public abstract byte[] getBody();
    
    public abstract void setBody(byte[] data);
    
    public abstract void feedBodyToOutput(OutputStream os);

    public abstract boolean targetExists();
    
    public abstract boolean isCached();

    public abstract void buildResponse() throws Exception;

    @Override
    public String toString() {
        return "HttpResponse{" + getStatusLine() + '}';
    }
    
    //////////  Inner Enum  //////////

    public enum Header {

        CONTENT_TYPE("Content-Type"),
        WWW_AUTHENTICATE("WWW-Authenticate");
        
        private final String formated;

        private Header(String formated) {
            this.formated = formated;
        }

        public String getFormated() {
            return formated;
        }
    }
}
