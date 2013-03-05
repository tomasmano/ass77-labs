package ass.manotoma.lab01.task01.helper;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class NullNodeMin<E extends Comparable<E>> extends NullNode<E> {

    @Override
    public int compareTo(E o) {
        return -1;
    }
    
}
