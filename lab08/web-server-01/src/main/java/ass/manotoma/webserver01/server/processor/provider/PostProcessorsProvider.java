package ass.manotoma.webserver01.server.processor.provider;

import ass.manotoma.webserver01.server.processor.PostProcessor;
import ass.manotoma.webserver01.server.support.Request;
import ass.manotoma.webserver01.server.support.Response;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public interface PostProcessorsProvider<RQ extends Request, RSP extends Response> {

    RSP postProcess(RQ req, RSP res);

    void add(PostProcessor<RQ , RSP> processor);
}