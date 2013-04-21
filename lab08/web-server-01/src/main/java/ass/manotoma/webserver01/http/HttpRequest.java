package ass.manotoma.webserver01.http;

import ass.manotoma.webserver01.server.support.Request;
import java.io.File;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpRequest implements Request {

    private Method method;
    private File requestTarget;

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

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public File getRequestTarget() {
        return requestTarget;
    }

    public void setRequestTarget(File requestTarget) {
        this.requestTarget = requestTarget;
    }

    @Override
    public String toString() {
        return "HttpRequest{" + "method=" + method + ", requestTarget=" + requestTarget + '}';
    }
    
    //////////  Inner Class  //////////
    public enum Method {
        GET
    }
}
