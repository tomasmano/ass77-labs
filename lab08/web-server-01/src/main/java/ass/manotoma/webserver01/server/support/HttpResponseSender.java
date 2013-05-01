package ass.manotoma.webserver01.server.support;

import ass.manotoma.webserver01.http.HttpResponse;
import ass.manotoma.webserver01.io.HttpResponseOutputStream;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpResponseSender implements ResponseSender<HttpResponse> {

    public static final Logger LOG = LoggerFactory.getLogger(HttpResponseSender.class);
    
    private static ResponseSender<HttpResponse> INSTANCE = new HttpResponseSender();

    public static ResponseSender<HttpResponse> getInstance() {
        return INSTANCE;
    }
    
    public void send(HttpResponse res, OutputStream os) {
        LOG.debug("Sending response [{}].. ", res);
        HttpResponseOutputStream httpOutputStream = null;
        try {
            httpOutputStream = new HttpResponseOutputStream(new BufferedOutputStream(os));
            httpOutputStream.write(res);
            httpOutputStream.close();
        } catch (Exception ex) {
            LOG.error("An error occured while sending response: " + ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }
    
}
