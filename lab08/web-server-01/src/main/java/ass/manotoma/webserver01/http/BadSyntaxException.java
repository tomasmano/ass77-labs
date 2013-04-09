package ass.manotoma.webserver01.http;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class BadSyntaxException extends RuntimeException {

    public BadSyntaxException() {
    }

    public BadSyntaxException(String message) {
        super(message);
    }

    public BadSyntaxException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadSyntaxException(Throwable cause) {
        super(cause);
    }

    public BadSyntaxException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
