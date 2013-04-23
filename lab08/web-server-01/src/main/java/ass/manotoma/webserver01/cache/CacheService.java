package ass.manotoma.webserver01.cache;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public interface CacheService {

    /**
     * Load data holder for the given key.
     *
     * @param filename
     * @return dataholder of null if mapping no exists, or was deleted or
     * expired
     */
    DataHolder load(String filename);

    /**
     * Store the given data holder for the given key.
     * 
     * @param filename
     * @param data 
     */
    void store(String filename, DataHolder data);

    /**
     * Remove data holder with the given key.
     * 
     * @param filename 
     */
    void remove(String filename);
}
