package ass.manotoma;

import java.util.concurrent.Callable;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public interface CallableAware<T> {

    Callable<T> getCallable();
}
