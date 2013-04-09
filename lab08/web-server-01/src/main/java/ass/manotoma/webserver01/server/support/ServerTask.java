package ass.manotoma.webserver01.server.support;

/**
 * Command pattern example.
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public interface ServerTask extends Runnable {

    /**
     * Executes the given job.
     */
    void process();
}
