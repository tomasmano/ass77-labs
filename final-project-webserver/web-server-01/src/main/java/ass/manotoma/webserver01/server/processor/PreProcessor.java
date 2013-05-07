package ass.manotoma.webserver01.server.processor;

import ass.manotoma.webserver01.server.model.Request;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public interface PreProcessor<RQ extends Request> {

    RQ preProcess(RQ req);
}
