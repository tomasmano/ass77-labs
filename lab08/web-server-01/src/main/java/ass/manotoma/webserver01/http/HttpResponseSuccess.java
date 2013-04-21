package ass.manotoma.webserver01.http;

import ass.manotoma.webserver01.http.util.StatusCode;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
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
    }

    //////////  Getters  //////////
    @Override
    public StatusCode getStatusCode() {
        return StatusCode._200;
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

    @Override
    public void buildResponse() throws Exception {
        createHeaders();
//        createMassageBody();
    }

    //////////  Helper Methods  //////////
    private void createHeaders() {
        createContentType();
    }

    private void createContentType() {
        String fileName = target.getAbsolutePath();
        if (fileName.endsWith(".html") || fileName.endsWith(".htm")) {
            headers.put(Header.CONTENT_TYPE, "text/html; charset=UTF-8");
            return;
        }
        if (fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif")) {
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
        //        IOUtils.toByteArray(fis);
        byte[] bucket = new byte[32 * 1024];
        BufferedInputStream bis = null;
        ByteArrayOutputStream result = null;
        try {
            bis = new BufferedInputStream(fis);
            result = new ByteArrayOutputStream(bucket.length);
            int bytesRead = 0;
            while (bytesRead != -1) {
                //aInput.read() returns -1, 0, or more :
                bytesRead = bis.read(bucket);
                if (bytesRead > 0) {
                    result.write(bucket, 0, bytesRead);
                }
            }
            bis.close();
        } catch (IOException e) {
            LOG.error("An error occured while creating massage body in {}, byte reading and writing operations was not succesfull:"
                    + e.getMessage(), this.getClass().getCanonicalName());
            e.printStackTrace(System.out);
        }
        bucket = null; // GC
        body = result.toByteArray();
    }
}
