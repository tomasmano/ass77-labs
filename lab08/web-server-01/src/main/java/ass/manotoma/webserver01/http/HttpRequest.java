package ass.manotoma.webserver01.http;

import ass.manotoma.webserver01.server.support.Request;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpRequest implements Request {

    private Method method;
    private File requestTarget;
    private boolean securedTarget;
    private boolean authenticated;
    private Map<String, String> headers = new HashMap<String, String>();

    public HttpRequest() {
    }

    public HttpRequest(Method method, String requestTarget) {
        this.method = method;
        this.requestTarget = new File(requestTarget);
    }

    public HttpRequest(String method, String requestTarget) {
        this.method = Method.valueOf(method);
        this.requestTarget = new File("."+requestTarget);
    }

    public HttpRequest addHeader(String header, String value){
        headers.put(header, value);
        return this;
    }
    
    //////////  Getters / Setters  //////////
    
    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public File getTarget() {
        return requestTarget;
    }

    public void setRequestTarget(File requestTarget) {
        this.requestTarget = requestTarget;
    }
    

    public Map<String, String> getHeaders() {
        return headers;
    }

    public boolean isSecuredTarget() {
        return securedTarget;
    }

    public void setSecuredTarget(boolean securedTarget) {
        this.securedTarget = securedTarget;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setIsAuthenticated(boolean isAuthenticated) {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String toString() {
        return "HttpRequest{" + "method=" + method + ", requestTarget=" + requestTarget + '}';
    }
    
    //////////  Inner Class  //////////
    
    public enum Method {
        GET
    }
    
    public enum Header {

        HOST("Host"), USER_AGENT("User-Agent"), CONTENT_TYPE("Content-Type"), AUTHORIZATION("Authorization");
        
        private final String formated;

        private Header(String formated) {
            this.formated = formated;
        }

        public String getFormated() {
            return formated;
        }
    }
}
