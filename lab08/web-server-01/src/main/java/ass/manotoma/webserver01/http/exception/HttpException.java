package ass.manotoma.webserver01.http.exception;

import ass.manotoma.webserver01.http.exception.handler.HttpExceptionVisitor;
import ass.manotoma.webserver01.http.HttpRequest;
import ass.manotoma.webserver01.http.HttpResponse;
import java.io.OutputStream;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public abstract class HttpException extends RuntimeException {

    protected HttpRequest request;

    public HttpException() {
    }

    public HttpException(HttpRequest request) {
        this.request = request;
    }
    
    public HttpException(String message) {
        super(message);
    }

    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpException(Throwable cause) {
        super(cause);
    }

    protected HttpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public HttpRequest getRequest() {
        return request;
    }
  
    public abstract HttpResponse accept(HttpExceptionVisitor visitor, OutputStream os);
}
