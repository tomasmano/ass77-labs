package ass.manotoma.webserver01.http.util;

import ass.manotoma.webserver01.http.HttpResponse;
import ass.manotoma.webserver01.io.HttpResponseOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpResponsePrinter {

    public static final org.slf4j.Logger LOG = LoggerFactory.getLogger(HttpResponsePrinter.class);

    public static String getAsString(HttpResponse res) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        HttpResponseOutputStream stream = new HttpResponseOutputStream(bytes);
        try {
            stream.write(res);
            stream.close();
        } catch (IOException ex) {
            LOG.error("an error occured while printing: {}", ex);
        }
        return bytes.toString();
    }
    
    public static void print(HttpResponse res) {
        System.out.println("=========================");
        System.out.println("Printing response:");
        System.out.println("=========================");
        System.out.println(getAsString(res));
        System.out.println("=========================");
    }
}
