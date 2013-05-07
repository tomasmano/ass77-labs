package ass.manotoma.webserver01.http.exception.handler;

import ass.manotoma.webserver01.Bootstrap;
import ass.manotoma.webserver01.http.HttpMsgsFactory;
import ass.manotoma.webserver01.http.HttpResponse;
import ass.manotoma.webserver01.http.exception.BadSyntaxException;
import ass.manotoma.webserver01.http.exception.NotFoundException;
import ass.manotoma.webserver01.http.exception.UnauthorizedException;
import ass.manotoma.webserver01.http.util.StatusCode;
import ass.manotoma.webserver01.server.support.sender.HttpResponseSender;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpExceptionVisitor {

    public static final Logger LOG = LoggerFactory.getLogger(HttpExceptionVisitor.class);

    /**
     * Handles code 400.
     */
    public HttpResponse handle(BadSyntaxException ex, OutputStream os) {
        LOG.debug("Request had bad syntax, creating 400 response...");
        HttpResponse res = HttpMsgsFactory.createErrorResponse(StatusCode._400, ex.getRequest().getRequestTargetName());
        HttpResponseSender.getInstance().send(res, os);
        return res;
    }

    /**
     * Handles code 401.
     */
    public HttpResponse handle(UnauthorizedException ex, OutputStream os) {
        LOG.debug("Requested targed is secured and client authentication was not successfull, creating 401 response...");
        HttpResponse res = HttpMsgsFactory.createErrorResponse(StatusCode._401, ex.getRequest().getRequestTargetName())
                .addHeader(HttpResponse.Header.WWW_AUTHENTICATE, Bootstrap.properties.getProperty("security_realm"));
        HttpResponseSender.getInstance().send(res, os);
        return res;
    }

    /**
     * Handles code 404.
     */
    public HttpResponse handle(NotFoundException ex, OutputStream os) {
        LOG.debug("Cannot find requested targed, creating 404 response...");
        HttpResponse res = HttpMsgsFactory.createErrorResponse(StatusCode._404, ex.getRequest().getRequestTargetName());
        HttpResponseSender.getInstance().send(res, os);
        return res;
    }

}
