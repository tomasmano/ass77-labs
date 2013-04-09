package ass.manotoma.webserver01.server.support;

import ass.manotoma.webserver01.http.Request;
import ass.manotoma.webserver01.http.Response;
import ass.manotoma.webserver01.io.RequestReader;

/**
 * Server job template. Example of Template Method pattern.
 * 
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public interface ServerJobTemplate {

    /**
     * Parse the given reader.
     * 
     * @param source the given source
     * @return request
     */
    Request parse(RequestReader source);

    /**
     * Do custom preprocessing.
     * 
     * @param req request to be preprocessed
     */
    void preProcess(Request req);

    /**
     * Serve the given request.
     * 
     * @param req
     * @return 
     */
    Response serve(Request req);

    /**
     * Do custom postprocessing.
     * 
     * @param req response to be postprocessed 
     */
    void postProcess(Response req);
}
