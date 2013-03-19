package ass.manotoma;

public interface Future<T> {

    T get() throws Exception;
}
