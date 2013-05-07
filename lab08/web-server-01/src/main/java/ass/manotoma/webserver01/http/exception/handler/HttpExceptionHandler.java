package ass.manotoma.webserver01.http.exception.handler;

import ass.manotoma.webserver01.http.HttpResponse;
import ass.manotoma.webserver01.http.exception.HttpException;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpExceptionHandler implements ExceptionHandler<HttpResponse, HttpException> {

    public static final Logger LOG = LoggerFactory.getLogger(HttpExceptionHandler.class);
    
    private static HttpExceptionHandler INSTANCE = new HttpExceptionHandler();
    
    private HttpExceptionVisitor visitor = new HttpExceptionVisitor();

    @Override
    public HttpResponse handle(HttpException ex, OutputStream os) {
        return ex.accept(visitor, os);
    }

    public static HttpExceptionHandler getInstance() {
        return INSTANCE;
    }
    
}
