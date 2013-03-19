package ass.manotoma;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class App {

    private static Random random = new Random();

    public static void main(String[] args) throws Exception{

        // init collections
        Collection<Callable<Integer>> sums = new ArrayList<Callable<Integer>>();
        for (int i = 0; i < 100; i++) {
            sums.add(new SumCallable(anyInt(), anyInt()));
        }
        // to test exception handling: arg less than zero is invalid
                sums.add(new SumCallable(-1000, anyInt()));
        
        // create pool
        WorkerPool<Integer> pool = new WorkerPoolImpl<Integer>(10);
        
        // invoke pool
        List<Future<Integer>> futures = pool.call(sums);
        System.out.format("Futures count [%s].%n", futures.size());
        
        // retrieve values
        for (Future<Integer> f : futures) {
            Integer res = f.get();
            System.out.format("Get from future [%s] returned [%s]%n", f, res);
        }

    }

    public static int anyInt() {
        return random.nextInt(1000);
    }

}
