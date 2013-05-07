package ass.manotoma.lab01.task01.helper;

/**
 * Node contract.
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public interface Node<E extends Comparable<E>> extends Comparable<E> {

    public boolean hasNext();

    public boolean hasPrevious();

    public E getElement();

    public void setElement(E element);

    public Node<E> getNext();

    public void setNext(Node<E> next);

    public Node<E> getPrevious();

    public void setPrevious(Node<E> previous);

    public int compareTo(E o);
}
