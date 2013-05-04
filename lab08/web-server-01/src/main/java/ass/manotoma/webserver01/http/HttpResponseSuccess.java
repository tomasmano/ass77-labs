package ass.manotoma.webserver01.http;

import ass.manotoma.webserver01.http.util.StatusCode;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.EnumMap;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponseSuccess extends HttpResponse {

    public static final Logger LOG = LoggerFactory.getLogger(HttpResponseSuccess.class);
    private File target;
    private boolean exists;
    public static final String status = "HTTP/1.1 200 OK";
    private Map<Header, String> headers = new EnumMap<Header, String>(Header.class);
    private byte[] body;

    public HttpResponseSuccess(File target) {
        this.target = target;
        this.exists = target.exists();
        createContentType();    
    }

    //////////  Getters  //////////
    @Override
    public StatusCode getStatusCode() {
        return StatusCode._200;
    }

    @Override
    public String getTargetName() {
        return target.getName();
    }

    @Override
    public String getStatusLine() {
        return status;
    }

    @Override
    public Map<Header, String> getHeaders() {
        return headers;
    }

    @Override
    public byte[] getBody() {
        if (body == null) {
            createMassageBody();
        }
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    @Override
    public void feedBodyToOutput(OutputStream os) {
        FileInputStream fis = null;
        try {

            fis = new FileInputStream(target);
            IOUtils.copy(fis, os);
        } catch (IOException e) {
            LOG.error("An error occured while creating massage body in {}, cannot find requested target file:"
                    + e.getMessage(), this.getClass().getCanonicalName());
            e.printStackTrace(System.out);
        }
    }

    @Override
    public boolean targetExists() {
        return exists;
    }

    //////////  Helper Methods  //////////


    private void createContentType() {
        String fileName = target.getAbsolutePath();
        if (fileName.endsWith(".html") || fileName.endsWith(".htm")) {
            headers.put(Header.CONTENT_TYPE, "text/html; charset=UTF-8");
            return;
    }
        if (fileName.endsWith(".jpeg") || fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".gif")) {
            headers.put(Header.CONTENT_TYPE, "image/gif");
            return;
        }
        if (fileName.endsWith(".js")) {
            headers.put(Header.CONTENT_TYPE, "text/script");
            return;
        }
    }

    private void createMassageBody() {
        FileInputStream fis = null;
        try {

            fis = new FileInputStream(target);
        } catch (FileNotFoundException e) {
            LOG.error("An error occured while creating massage body in {}, cannot find requested target file:"
                    + e.getMessage(), this.getClass().getCanonicalName());
            e.printStackTrace(System.out);
        }
        try {
            body = IOUtils.toByteArray(fis);
        } catch (IOException e) {
            LOG.error("An error occured while creating massage body in {}, byte reading and writing operations was not succesfull:"
                    + e.getMessage(), this.getClass().getCanonicalName());
            e.printStackTrace(System.out);
        }
    }
}
