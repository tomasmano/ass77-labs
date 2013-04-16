package ass.manotoma.webserver01.io;

import ass.manotoma.webserver01.http.HttpResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpResponseOutputStream extends OutputStream {

    OutputStream os;

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
        os.write(httpResponse.getBody());
        os.close();
    }
}