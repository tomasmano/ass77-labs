package ass.manotoma.webserver01.http;

import ass.manotoma.webserver01.http.exception.NotFoundException;
import ass.manotoma.webserver01.server.ContentFinder;
import ass.manotoma.webserver01.server.model.Request;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpRequest implements Request {

    private Method method;
    private String requestTargetName;
    private File requestTarget;
    private boolean securedTarget;
    private boolean authenticated;
    private Map<String, String> headers = new HashMap<String, String>();

    public HttpRequest() {
    }

    public HttpRequest(String method, String requestTargetName) {
        this.method = Method.valueOf(method);
        this.requestTargetName = requestTargetName;
    }
    
    public HttpRequest(String method, File file) {
        this.method = Method.valueOf(method);
        this.requestTarget = file;
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

    public String getRequestTargetName() {
        return requestTargetName;
    }

    public File getTarget() {
        if (requestTarget == null) {
            throw new IllegalStateException("Target has not been loaded yet.");
        }
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
        return method + " " + requestTargetName;
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
