package ass.manotoma.webserver01.security;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public interface SecurityInterceptor {

    /**
     * If the given interceptor applies for the given url
     * 
     * @param url
     * @return true if the given url is intercepted by this intercpetor
     */
    boolean isApplied(String url);
}
