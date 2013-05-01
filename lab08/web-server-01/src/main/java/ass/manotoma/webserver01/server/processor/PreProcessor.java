package ass.manotoma.webserver01.server.processor;

import ass.manotoma.webserver01.server.support.Request;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public interface PreProcessor<RQ extends Request> {

    RQ preProcess(RQ req);
}
