package ass.manotoma.webserver01.server.processor.provider;

import ass.manotoma.webserver01.server.processor.PreProcessor;
import ass.manotoma.webserver01.server.support.Request;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public interface PreProcessorsProvider<RQ extends Request> {

    RQ preProcess(RQ req);

    void add(PreProcessor<RQ> processor);
}
