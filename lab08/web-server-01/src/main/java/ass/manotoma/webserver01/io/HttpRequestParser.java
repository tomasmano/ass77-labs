package ass.manotoma.webserver01.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HttpRequestParser implements RequestParser {

    private BufferedReader br;
    private InputStream is;

    public HttpRequestParser() {
    }

    public HttpRequestParser(InputStream is) {
        this.is = is;
        br = new BufferedReader(new InputStreamReader(is));
    }

    /**
     * Reads request headers according to HTTP Specifications. The request line
     * and headers are separated by CR LF sequences (bytes with decimal value 13
     * and 10).
     *
     * @return HTTP Request header
     * @throws IOException
     */
    @Override
    public String read() throws IOException {
        return br.readLine();
    }
}
