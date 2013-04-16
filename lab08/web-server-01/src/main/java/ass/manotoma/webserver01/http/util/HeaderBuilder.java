package ass.manotoma.webserver01.http.util;

import ass.manotoma.webserver01.http.HttpResponse;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class HeaderBuilder {
    
    private StringBuilder sb = new StringBuilder();
    private final static String SEPARATOR = ": ";
    private final static String CRLF = "\r\n"; // TERMINATOR
    
    public HeaderBuilder add(HttpResponse.Header header, String val){
        sb.append(header.getFormated()).append(SEPARATOR).append(val).append(CRLF);
        return this;
    }
    
    public String build(){
        return sb.toString() + CRLF;
    }
    
}
