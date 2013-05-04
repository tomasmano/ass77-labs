package ass.manotoma.webserver01.server;

import ass.manotoma.webserver01.cache.CacheFactory;
import ass.manotoma.webserver01.cache.ContentHolder;
import java.io.File;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class Content {

    private String targetName;
    private File file;
    boolean cached;
    boolean exists;
    private ContentHolder contentHolder;
    private String contentType;

    public Content(File file) {
        this.file = file;
        this.targetName = file.getName();
        this.exists = file.exists();
        this.contentHolder = CacheFactory.getCache().load(file.getPath());
        this.cached = contentHolder == null ? false : true;
        findContentType();
    }

    //////////  Getters / Setters  //////////
    
    public String getTargetName(){
        return targetName;
    }
    
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isCached() {
        return cached;
    }

    public void setCached(boolean cached) {
        this.cached = cached;
    }

    public boolean isExists() {
        return file != null ? file.exists() : false;
    }

    public ContentHolder getContentHolder() {
        return contentHolder;
    }

    public void setContentHolder(ContentHolder contentHolder) {
        this.contentHolder = contentHolder;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    //////////  Helper methods  //////////
    
    private void findContentType() {
        String fileName = file.getAbsolutePath();
        if (fileName.endsWith(".html") || fileName.endsWith(".htm")) {
            contentType = "text/html; charset=UTF-8";
            return;
        }
        if (fileName.endsWith(".jpeg") || fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".gif")) {
            contentType = "image/gif";
            return;
        }
        if (fileName.endsWith(".js")) {
            contentType = "text/script";
            return;
        }
    }
}
