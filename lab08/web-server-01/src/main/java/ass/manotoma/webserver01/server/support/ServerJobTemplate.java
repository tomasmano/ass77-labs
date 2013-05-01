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
public abstract class ServerJobTemplate<RQ extends Request, RSP extends Response> {

    public static final Logger LOG = LoggerFactory.getLogger(ServerJobTemplate.class);
    
    private RequestReader parser;
    private OutputStream os;

    public ServerJobTemplate(RequestReader parser, OutputStream os) {
        this.parser = parser;
        this.os = os;
    }
    
    //////////  Template method (general workflow structure)  //////////

    public RSP doTemplate() {
        RQ req = parse(parser);
        preProcess(req);
        RSP res = serve(req);
        postProcess(req, res);
        LOG.debug("Job finished: Request [{}] processing succesfully finished", req);
        return res;
    }
    
    //////////  Individual steps (placeholders)  //////////

    /**
     * Parse the given reader.
     *
     * @param source the given source
     * @return request
     */
    public abstract RQ parse(RequestReader source);

    /**
     * Do custom preprocessing.
     *
     * @param req request to be preprocessed
     */
    public abstract void preProcess(RQ req);

    /**
     * Serve the given request.
     *
     * @param req
     * @return
     */
    public abstract RSP serve(RQ req);

    /**
     * Do custom postprocessing.
     *
     * @param res response to be postprocessed
     */
    public abstract void postProcess(RQ req, RSP res);
    
    //////////  Getters / Setters  //////////

    public OutputStream getOutputStream() {
        return os;
    }
}
