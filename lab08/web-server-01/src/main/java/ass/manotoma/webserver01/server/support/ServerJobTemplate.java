package ass.manotoma.webserver01.server.support;

import ass.manotoma.webserver01.io.RequestReader;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Server job template. Example of Template Method pattern.
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public abstract class ServerJobTemplate {

    public static final Logger LOG = LoggerFactory.getLogger(ServerJobTemplate.class);
    private RequestReader parser;
    private OutputStream os;

    public ServerJobTemplate(RequestReader parser, OutputStream os) {
        this.parser = parser;
        this.os = os;
    }

    public Response doTemplate() {
        Request req = parse(parser);
        preProcess(req);
        Response res = serve(req);
        postProcess(res);
        LOG.debug("Job finished: Request [{}] processing succesfully finished", req);
        return res;
    }

    /**
     * Parse the given reader.
     *
     * @param source the given source
     * @return request
     */
    public abstract Request parse(RequestReader source);

    /**
     * Do custom preprocessing.
     *
     * @param req request to be preprocessed
     */
    public abstract void preProcess(Request req);

    /**
     * Serve the given request.
     *
     * @param req
     * @return
     */
    public abstract Response serve(Request req);

    /**
     * Do custom postprocessing.
     *
     * @param req response to be postprocessed
     */
    public abstract void postProcess(Response req);

    public OutputStream getOutputStream() {
        return os;
    }
}
