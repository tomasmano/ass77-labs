package ass.manotoma.webserver01.server;

import java.io.File;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class ContentFinder {

    /**
     * Finds file for the given filename.
     * 
     * @param filename name of file starting with '/'
     * @return file for the given filename
     */
    public static File find(String filename) {
        return new File("."+filename);
    }
}
