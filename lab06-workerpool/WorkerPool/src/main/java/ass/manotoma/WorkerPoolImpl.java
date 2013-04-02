package ass.manotoma;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

public class WorkerPoolImpl<T> implements WorkerPool<T> {

    private LinkedBlockingQueue<BlockingResponse<T>> pool;

    public WorkerPoolImpl(int capacity) {
        pool = new LinkedBlockingQueue<BlockingResponse<T>>();
        for (int i = 0; i < capacity; i++) {
            new Thread(new BlockingRunnable<T>(pool)).start();
        }
    }

    public List<Future<T>> call(Collection<Callable<T>> callable) {
        List<Future<T>> futures = new ArrayList<Future<T>>(callable.size());
        for (Callable<T> c : callable) {
            BlockingResponse<T> br = new BlockingResponse<T>(c);
            pool.add(br);
            futures.add(br);
        }
        return futures;
    }
    
    //////////  Private Classes  //////////

    private class BlockingResponse<T> implements Future<T>, CallableAware<T> {

        private Callable<T> callable;
        private T val;
        private Exception ex;
        private CountDownLatch latch = new CountDownLatch(1);

        public BlockingResponse(Callable<T> callable) {
            this.callable = callable;
        }

        public T get() throws Exception {
            latch.await();
            if (ex != null) {
                throw ex;
            }
            return val;
        }

        private void execute() {
            try {
                val = callable.call();
            } catch (Exception ex) {
                this.ex = ex;
            } finally {
                latch.countDown();
            }
        }

        public Callable<T> getCallable() {
            return callable;
        }

        @Override
        public String toString() {
            return "BlockingResponse{" + "callable=" + callable + '}';
        }
        
    }

    private class BlockingRunnable<T> implements Runnable {

        private LinkedBlockingQueue<BlockingResponse<T>> pool;

        public BlockingRunnable(LinkedBlockingQueue<BlockingResponse<T>> pool) {
            this.pool = pool;
        }

        public void run() {
            BlockingResponse<T> response = null;
            while (true) {
                try {
                    response = pool.take();
                    response.execute();
                } catch (Exception ex) {
                    response.ex = ex;
                }
            }
        }
    }
}
