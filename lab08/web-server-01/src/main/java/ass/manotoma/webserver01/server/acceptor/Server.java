package ass.manotoma.webserver01.server.acceptor;

/**
 * Waits for and accepts incoming requests. It encapsulates the different
 * concurrency policies for simultaneous access etc.
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public interface Server {

    void init();

    void serve();

    void stop();
}
