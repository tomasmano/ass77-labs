package ass.manotoma;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class Worker<T> implements Runnable {

//    public static final Logger LOG = LoggerFactory.getLogger(Worker.class);
    private ObjectPool<T> pool;

    public Worker(ObjectPool<T> pool) {
        this.pool = pool;
//        LOG.debug("Instantiating {} [{}]", this.getClass().getSimpleName(), this);
    }

    public void run() {
        try {
            while (true) {
                System.out.println(">>>>>>>>>>> called");
                System.out.println("pool: "+pool);
                pool.poll();
            }
        } catch (InterruptedException ex) {
//            LOG.error("An error occured during invoking poll(): {}", ex);
        }
    }
}
