package ass.manotoma.webserver01.http;

import ass.manotoma.webserver01.http.util.StatusCode;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.EnumMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponseError extends HttpResponse {

    public static final Logger LOG = LoggerFactory.getLogger(HttpResponseError.class);
    private Map<Header, String> headers = new EnumMap<Header, String>(Header.class);
    private StatusCode code;
    private String targetName;
    private String title;
    private String page;
    

    public HttpResponseError(StatusCode code, String targetName) {
        this.code = code;
        this.targetName = targetName;
    }

    public HttpResponseError(StatusCode code, String targetName, String title, String page) {
        this.code = code;
        this.targetName = targetName;
        this.title = title;
        this.page = page;
    }

    @Override
    public StatusCode getStatusCode() {
        return code;
    }

    @Override
    public String getTargetName() {
        return targetName;
    }

    @Override
    public String getStatusLine() {
        StringBuilder sb = new StringBuilder();
        sb.append(HttpResponse.PROTOCOL).append(" ").append(code.code()).append(" ").append(code.description());
        return sb.toString();
    }

    @Override
    public Map<Header, String> getHeaders() {
        return headers;
    }

    @Override
    public byte[] getBody() {
        StringBuilder body = new StringBuilder("");
        body.append(title).append(code.code()).append(page);
        return new String(body).getBytes();
    }

    @Override
    public void setBody(byte[] data) {
        // do nothing
    }

    @Override
    public void feedBodyToOutput(OutputStream out) {
        ByteArrayInputStream in = null;
        try {
            byte[] body = getBody();
            in = new ByteArrayInputStream(body);
            IOUtils.copy(in, out);
        } catch (IOException ex) {
            LOG.error("An error occured during reading and writing bytes: {}", ex);
        }
    }

    @Override
    public boolean targetExists() {
        return false;
    }

    @Override
    public boolean isCached() {
        return true;
    }

    
}
