package ass.manotoma.webserver01.http.exception;

import ass.manotoma.webserver01.http.HttpRequest;
import ass.manotoma.webserver01.http.HttpResponse;
import ass.manotoma.webserver01.http.exception.handler.HttpExceptionVisitor;
import java.io.OutputStream;

public class NotFoundException extends HttpException {

    @Override
    public HttpResponse accept(HttpExceptionVisitor visitor, OutputStream os) {
        return visitor.handle(this, os);
    }

    public NotFoundException(HttpRequest request) {
        super(request);
    }
    
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
