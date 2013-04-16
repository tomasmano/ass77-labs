package ass.manotoma.webserver01.http;

import ass.manotoma.webserver01.http.util.StatusCode;
import java.util.EnumMap;
import java.util.Map;

public class HttpResponseError extends HttpResponse {

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
        System.out.println("");
        return new String(body).getBytes();
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
