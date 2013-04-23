package ass.manotoma.webserver01.io;

import ass.manotoma.webserver01.http.HttpResponse;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpResponseOutputStream extends OutputStream {

    private OutputStream os;

    public HttpResponseOutputStream(OutputStream os) {
        this.os = os;
    }

    @Override
    public void write(int b) throws IOException {
        os.write(b);
    }

    public void write(HttpResponse httpResponse) throws IOException {
        os.write(httpResponse.getFormatedStatusLine().getBytes());
        os.write(httpResponse.getFormatedHeader().getBytes());
        if (httpResponse.isCached()) {
//            os.write(httpResponse.getBody());
            IOUtils.write(httpResponse.getBody(), os);
        } else {
            httpResponse.feedBodyToOutput(os);
        }
        os.close();
    }
}