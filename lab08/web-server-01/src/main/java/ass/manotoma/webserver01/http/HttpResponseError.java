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

    public HttpResponseError(StatusCode code) {
        this.code = code;
    }

    @Override
    public StatusCode getStatusCode() {
        return code;
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
        body.append("<html><head><title>File not found</title></head><body><h1>").append(code.code()).append("</h1><p>File not found.</p></body></html>");
        return new String(body).getBytes();
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
    public void buildResponse() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
