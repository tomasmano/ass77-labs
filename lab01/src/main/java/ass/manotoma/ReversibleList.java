package ass.manotoma;

import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public interface ReversibleList<E extends Comparable<E>> extends Iterable<E> {

    /**
     * Returns the number of elements in this collection. If this collection
     * contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     *
     * @return the number of elements in this collection
     */
    int size();

    /**
     * Returns <tt>true</tt> if this collection contains no elements.
     *
     * @return <tt>true</tt> if this collection contains no elements
     */
    boolean isEmpty();

    /**
     * Returns <tt>true</tt> if this collection contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this collection
     * contains at least one element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     *
     * @param e element whose presence in this collection is to be tested
     * @return <tt>true</tt> if this collection contains the specified element
     */
    boolean contains(E e);

    /**
     * Returns an iterator over the elements in this collection.
     *
     * @return an <tt>Iterator</tt> over the elements in this collection
     */
    @Override
    Iterator<E> iterator();

    /**
     * Adds specified element to the collection.
     *
     * @param e element whose presence in this collection is to be ensured
     */
    void add(E e);

    /**
     * Removes a single instance of the specified element from this collection,
     * if it is present (optional operation). More formally, removes an element
     * <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>, if this
     * collection contains one or more such elements. Returns <tt>true</tt> if
     * this collection contained the specified element (or equivalently, if this
     * collection changed as a result of the call).
     *
     * @param e element to be removed from this collection, if present
     * @return <tt>true</tt> if an element was removed as a result of this call
     */
    boolean remove(E e);

    /**
     * Adds all of the elements in the specified collection to this collection.
     * The behavior of this operation is undefined if the specified collection
     * is modified while the operation is in progress. (This implies that the
     * behavior of this call is undefined if the specified collection is this
     * collection, and this collection is nonempty.)
     *
     * @param c collection containing elements to be added to this collection
     * @see #add(Comparable)
     */
    void addAll(Collection<? extends E> c);

    /**
     * Removes all of the elements from this collection. The collection will be
     * empty after this method returns.
     */
    void clear();

    /**
     * Sorts all of the elements from this collection, using compare method. For
     * this Bubble sort should be used. After calling this method the elements
     * should be in ascending order.
     */
    void sort();

    /**
     * Reverse the order of the elements in the collection
     */
    Iterator<E> descendingIterator();
}