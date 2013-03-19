package ass.manotoma;

import ass.manotoma.lab01.task01.helper.MyLinkedList;
import java.util.Collection;
import java.util.Iterator;

/**
 * ReversibleList implementation using custom inner generic MyLinkedList.
 * 
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class ReversibleLinkedList<E extends Comparable<E>> implements ReversibleList<E> {
    
    private MyLinkedList<E> inner = new MyLinkedList<E>();

    public int size() {
        return inner.size();
    }

    public boolean isEmpty() {
        return inner.isEmpty();
    }

    public boolean contains(E e) {
        return inner.contains(e);
    }

    public Iterator<E> iterator() {
        return inner.iterator();
    }

    public void add(E e) {
        inner.add(e);
    }

    public boolean remove(E e) {
        return inner.remove(e);
    }

    public void addAll(Collection<? extends E> c) {
        inner.addAll(c);
    }

    public void clear() {
        inner.clear();
    }

    public void sort() {
        inner.sort();
    }

    public Iterator<E> descendingIterator() {
       return inner.descendingIterator();
    }
    
}
