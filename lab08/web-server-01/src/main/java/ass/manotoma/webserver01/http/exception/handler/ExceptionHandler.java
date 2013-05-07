package ass.manotoma.webserver01.http.exception.handler;

import ass.manotoma.webserver01.server.model.Response;
import java.io.OutputStream;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public interface ExceptionHandler<RSP extends Response, EX extends RuntimeException> {

    RSP handle(EX ex, OutputStream os);
    
}
