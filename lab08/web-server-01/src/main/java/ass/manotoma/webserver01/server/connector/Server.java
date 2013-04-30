package ass.manotoma.webserver01.server.connector;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public interface Server {

    void init();

    void serve();

    void stop();
}
