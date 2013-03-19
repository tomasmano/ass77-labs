package ass.manotoma;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class WorkerOffer<T> implements Runnable {

    public static final Logger LOG = LoggerFactory.getLogger(WorkerOffer.class);
    private ObjectPool<T> pool;
    private T element;

    public WorkerOffer(ObjectPool<T> pool, T elem) {
        this.pool = pool;
        this.element = elem;
        LOG.debug("Instantiating {} [{}]", this.getClass().getSimpleName(), this);
    }

    public void run() {
        pool.offer(element);
    }
}
