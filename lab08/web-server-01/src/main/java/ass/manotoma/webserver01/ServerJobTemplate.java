package ass.manotoma.webserver01;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public interface ServerJobTemplate {
    Request parse(byte[] bytes);
    void preProcess(Request req);
    Response serve(Request req);
    void postProcess(Response req);
}
