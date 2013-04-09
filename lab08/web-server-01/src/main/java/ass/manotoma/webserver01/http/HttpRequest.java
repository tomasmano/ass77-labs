package ass.manotoma.webserver01.http;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpRequest implements Request {

    private Method method;
    private String requestTarget;

    public HttpRequest() {
    }

    public HttpRequest(Method method, String requestTarget) {
        this.method = method;
        this.requestTarget = requestTarget;
    }

    public HttpRequest(String method, String requestTarget) {
        this.method = Method.valueOf(method);
        this.requestTarget = requestTarget;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getRequestTarget() {
        return requestTarget;
    }

    public void setRequestTarget(String requestTarget) {
        this.requestTarget = requestTarget;
    }

    @Override
    public String toString() {
        return "HttpRequest{" + "method=" + method + ", requestTarget=" + requestTarget + '}';
    }
    
    //////////  Inner Class  //////////
    
    public enum Method{
        GET
    }
}
