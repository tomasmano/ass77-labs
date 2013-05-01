package ass.manotoma.webserver01.server.support;

import java.io.OutputStream;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public interface ResponseSender<RSP extends Response> {
    void send(RSP res, OutputStream os);
}
