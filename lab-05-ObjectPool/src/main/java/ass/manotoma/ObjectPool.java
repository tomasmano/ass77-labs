package ass.manotoma;

/**
 * Created with IntelliJ IDEA. User: jajusko Date: 3/12/13 Time: 12:09 PM
 */
public interface ObjectPool<T> {

    T poll() throws InterruptedException;

    void offer(T object);
}
