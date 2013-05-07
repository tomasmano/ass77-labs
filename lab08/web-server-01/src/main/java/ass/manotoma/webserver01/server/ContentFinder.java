package ass.manotoma.webserver01.server;

import java.io.File;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class ContentFinder {
    
    private final static String SERVER_ROOT = ".";

    /**
     * Finds file for the given filename.
     * 
     * @param filename name of file starting with '/'
     * @return file for the given filename
     */
    public static File find(String filename) {
        return new File(SERVER_ROOT + filename);
    }
}
