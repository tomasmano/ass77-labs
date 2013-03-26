
import ass.manotoma.CallableAware;
import ass.manotoma.Future;
import ass.manotoma.SumCallable;
import ass.manotoma.WorkerPool;
import ass.manotoma.WorkerPoolImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
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
public class WorkerPoolTest {

    private Collection<Callable<Integer>> sums = new ArrayList<Callable<Integer>>();
    private static Random random = new Random();

    public WorkerPoolTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        for (int i = 0; i < 100; i++) {
            sums.add(new SumCallable(anyInt(), anyInt()));
        }
    }

    @After
    public void tearDown() {
        sums.clear();
    }

    @Test(expected=IllegalArgumentException.class)
    public void test_expected_error() throws Exception{
        //given
        // to test exception handling: arg less than zero is invalid
        sums.add(new SumCallable(-1000, anyInt()));
        
        // create pool
        WorkerPool<Integer> pool = new WorkerPoolImpl<Integer>(10);
        
        // invoke pool
        List<Future<Integer>> futures = pool.call(sums);
        
        //when
        // retrieve values
        for (Future<Integer> f : futures) {
            Integer res = f.get();
        }
        
        //then
        // ... exception.....
    }
    
    @Test
    public void test_valid_result() throws Exception{
        //given
        // create pool
        WorkerPool<Integer> pool = new WorkerPoolImpl<Integer>(10);
        
        // invoke pool
        List<Future<Integer>> futures = pool.call(sums);
        
        //when
        // retrieve values
        for (Future<Integer> f : futures) {
            CallableAware<Integer> aware = (CallableAware<Integer>) f;
            SumCallable sum = (SumCallable) aware.getCallable();
            Integer res = f.get();
            //then
            assertEquals("Not equal", res.intValue(), sum.getX() + sum.getY());
        }
        
    }
    
    @Test
    public void test_blocking() throws Exception{
        //given
        sums = new ArrayList<Callable<Integer>>();
        sums.add(new SumCallable(2, 3));
        
        // create pool
        WorkerPool<Integer> pool = new WorkerPoolImpl<Integer>(1);
        
        // invoke pool
        List<Future<Integer>> futures = pool.call(sums);
        
        //when
        // retrieve values
        for (Future<Integer> f : futures) {
            CallableAware<Integer> aware = (CallableAware<Integer>) f;
            SumCallable sum = (SumCallable) aware.getCallable();
            Integer res = f.get();
        }
        
        //then
    }

    //////////  Helper methods  //////////
    public static int anyInt() {
        return random.nextInt(1000);
    }
}
