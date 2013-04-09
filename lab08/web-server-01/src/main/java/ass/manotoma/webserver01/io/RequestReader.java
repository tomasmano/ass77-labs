package ass.manotoma.webserver01.io;

import java.io.IOException;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public interface RequestReader {

    /**
     * Reads request headers according to HTTP Specifications. The request line
     * and headers are separated by CR LF sequences (bytes with decimal value 13
     * and 10).
     *
     * @return HTTP Request header
     * @throws IOException
     */
    public String read() throws IOException;
}
