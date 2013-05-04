package ass.manotoma.webserver01.server.processor;

import ass.manotoma.webserver01.server.model.Request;
import ass.manotoma.webserver01.server.model.Response;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public interface PostProcessor<RQ extends Request, RSP extends Response> {

    RSP postProcess(RQ req, RSP res);
}
