package ass.manotoma.lab01.task01.helper;

import java.util.Iterator;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class MyLinkedListIterator<E extends Comparable<E>> implements Iterator<E> {

    private MyLinkedList<E> inner;
    private Node<E> current;

    public MyLinkedListIterator(MyLinkedList<E> inner, Node<E> current) {
        this.inner = inner;
        this.current = current;
    }

    public boolean hasNext() {
        return current.hasNext();
    }

    public E next() {
        if (current.hasNext()) {
            E oldCurrent = current.getElement();
            current = current.getNext();
            return oldCurrent;
        }
        return null;
    }

    public void remove() {
        Node<E> oldCurrent = current.getPrevious();
        if (oldCurrent.getPrevious() instanceof NullNode) {
            // list contains only one element, so clear it completely
            inner.clear();
            return;
        }
        current.setPrevious(oldCurrent.getPrevious());
        oldCurrent.getPrevious().setNext(current);
    }
    
    /**
     * Returns the current node instead of its element (as it is defined in
     * interface conctract).
     *
     * @return the current node instead of its element
     */
    protected Node<E> nextNode() {
        if (current.hasNext()) {
            Node<E> oldCurrent = current;
            current = current.getNext();
            return oldCurrent;
        }
        return new NullNodeMax<E>();
    }
}
