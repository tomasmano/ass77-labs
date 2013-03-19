package ass.manotoma;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

public interface WorkerPool<T> {
    List<Future<T>> call(Collection<Callable<T>> callable);
}
