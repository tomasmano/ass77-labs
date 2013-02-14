package ass.manotomas.lab01.task01.helper;

import java.util.Iterator;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class MyLinkedListDescendingIterator<E extends Comparable<E>> implements Iterator<E> {

    private MyLinkedList<E> inner;
    private Node<E> current;

    public MyLinkedListDescendingIterator(MyLinkedList<E> inner, Node<E> current) {
        this.inner = inner;
        this.current = current;
    }

    public boolean hasNext() {
        return current.hasPrevious();
    }

    public E next() {
        if (current.hasPrevious()) {
            E oldCurrent = current.getElement();
            current = current.getPrevious();
            return oldCurrent;
        }
        return null;
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
