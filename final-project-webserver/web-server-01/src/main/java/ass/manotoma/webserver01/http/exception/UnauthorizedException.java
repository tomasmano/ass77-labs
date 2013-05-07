package ass.manotoma.webserver01.http.exception;

import ass.manotoma.webserver01.http.HttpRequest;
import ass.manotoma.webserver01.http.HttpResponse;
import ass.manotoma.webserver01.http.exception.handler.HttpExceptionVisitor;
import java.io.OutputStream;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class UnauthorizedException extends HttpException {

    public UnauthorizedException() {
    }

    public UnauthorizedException(HttpRequest request) {
        super(request);
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    @Override
    public HttpResponse accept(HttpExceptionVisitor visitor, OutputStream os) {
        return visitor.handle(this, os);
    }
}
