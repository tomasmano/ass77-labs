package ass.manotoma;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class ObjectPoolTest {
    
    private ObjectPool<String> pool;
    private ExecutorService es;

    public ObjectPoolTest() { }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        pool = new ObjectPoolImpl<String>();
        es = Executors.newFixedThreadPool(5);
    }

    @After
    public void tearDown() {
        pool = null;
        es.shutdown();
        es = null;
    }

    @Test
    public void test_offer_and_poll() throws Exception{
        //when
        pool.offer("caj");
        
        //then
        assertEquals("caj", pool.poll());
    }

    @Test
    public void test_poll_and_offer_and_poll() throws Exception{
        //given
        Worker<String> blocked = new Worker<String>(pool);
        Thread tBlocked = new Thread(blocked);
        
        //when
        tBlocked.start();
        Thread.sleep(2000);
        //then
        assertEquals("Thread is not waiting", Thread.State.WAITING, tBlocked.getState());

        //when
        pool.offer("soda");
        Thread.sleep(2000);
        //then
        assertEquals("Thread is not waiting", Thread.State.WAITING, tBlocked.getState());
        
        //when
        pool.offer("caj");
        Thread.sleep(2000);
        //then
        assertEquals("Thread is not waiting", Thread.State.WAITING, tBlocked.getState());
    }
}
