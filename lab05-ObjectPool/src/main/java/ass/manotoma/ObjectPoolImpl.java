package ass.manotoma;

import java.util.LinkedList;
import java.util.Queue;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class ObjectPoolImpl<T> implements ObjectPool<T> {
    
//    public static final Logger LOG = LoggerFactory.getLogger(ObjectPoolImpl.class);

    private Queue<T> queue = new LinkedList<T>();

    public T poll() throws InterruptedException {
//        LOG.debug("Attempting to poll elem...");
        synchronized (this) {
            while (queue.isEmpty()) {
//                LOG.debug("Pool is empty, waiting...");
                wait();
            }
//            LOG.debug("Polling elem: [{}]", queue.peek());
            return queue.poll();
        }
    }

    public void offer(T object) {
//        LOG.debug("Offering elem: [{}]", object);
        synchronized (this) {
            queue.add(object);
            notify();
        }
    }

    @Override
    public String toString() {
        return "ObjectPoolImpl{" + "queue=" + queue + '}';
    }
    
}
