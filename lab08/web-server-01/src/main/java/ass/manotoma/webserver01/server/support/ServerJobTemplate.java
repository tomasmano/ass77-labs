package ass.manotoma.webserver01.server.support;

import ass.manotoma.webserver01.http.Request;
import ass.manotoma.webserver01.http.Response;
import ass.manotoma.webserver01.io.RequestParser;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public interface ServerJobTemplate {
    Request parse(RequestParser source);
    void preProcess(Request req);
    Response serve(Request req);
    void postProcess(Response req);
}
