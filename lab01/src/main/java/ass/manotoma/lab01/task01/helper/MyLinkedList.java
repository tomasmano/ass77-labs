package ass.manotoma.lab01.task01.helper;

import ass.manotoma.ReversibleList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class MyLinkedList<E extends Comparable<E>> implements ReversibleList<E> {
    
//    public static final Logger LOG = LoggerFactory.getLogger(MyLinkedList.class);
    
    private final Node<E> NULL_NODE_MIN = new NullNodeMin<E>();
    private final Node<E> NULL_NODE_MAX = new NullNodeMax<E>();
    private Node<E> first = NULL_NODE_MIN;
    private final Node<E> last = NULL_NODE_MAX;
    private int size = 0;
    
    //////////  Interface  //////////

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return first instanceof NullNode ? true : false;
    }

    public boolean contains(E e) {
        Iterator<E> it = iterator();
        return contains(e, it);
    }
    
    /**
     * Helper methods for recursive contains
     */
    private  boolean contains(E e, Iterator<E> it) {
        if (it.hasNext()) {
            E compared = it.next();
            if(compared.equals(e)){
                return true;
            } else {
                // recursion ....
                return contains(e, it);
            }
        }
        return false;
    }

    public Iterator<E> iterator() {
        return new MyLinkedListIterator<E>(this, first);
    }

    public void add(E e) {
//        LOG.debug("-------------------------------");
//        LOG.debug("Inserting element [{}]", e);
        size++;
        // EMPTY list flow
        if (isEmpty() == true) {
            first = new RealNode<E>(e, NULL_NODE_MIN, last);
//            LOG.debug("Added First Node with elem [{}]: {}", e, first);
            return;
        }
        // NON-EMPTY list flow
        // #1 reserve the new node and put into inserted element
        Node<E> saved = new RealNode<E>(e, getCurrent(), last);
//        LOG.debug("Added Node with elem [{}]: {}", e, saved);
    }

    public boolean remove(E e) {
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            E current = it.next();
            
            // list contains the element, remove it!
            if (current.equals(e)) {
                it.remove();
                size--;
                return true;
            }
        }
        return false;
    }

    public void addAll(Collection<? extends E> c) {
        for (E e : c) {
            add(e);
        }
    }

    public void clear() {
        first = NULL_NODE_MIN;
        size = 0;
    }

    public void sort() {
//        System.out.println("Sorting inner elems: " +  MyLinkedList.toList(this));
//        sortSwaping();
//        System.out.println("Elems after sort: " +  MyLinkedList.toList(this));
        List<E> array = MyLinkedList.toList(this);
        System.out.println("Sorting inner elems on ["+this+"]: " + array);
        for (int i = 0; i < array.size() - 1; i++) {
            for (int j = 0; j < array.size() - i - 1; j++) {
                if ((array.get(j).compareTo(array.get(j + 1)) == 1)) {
                    E tmp = array.get(j);
                    array.set(j, array.get(j+1));
                    array.set(j+1, tmp);
                }
            }
        }
        this.clear();
        this.addAll(array);
        System.out.println("Elems after sort on ["+this+"]: " + MyLinkedList.toList(this));
    }
    
    private void sortSwaping() {
        MyLinkedListIterator<E> it = new MyLinkedListIterator<E>(this, first);
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                try {
                    if ((node(j).compareTo(node(j+1).getElement()) == 1)) {
                        swap(node(j), node(j + 1));
                    }
                } catch (NullPointerException e) {
//                    LOG.error("j = {}[class: {}]",node(j), node(j).getClass());
//                    LOG.error("j+1 = {}[class: {}]",node(j+1), node(j+1).getClass());
                    throw new NullPointerException();
                }
            }
        }
    }

    /**
     * Swap the given nodes.
     */
    private void swap(Node<E> node1, Node<E> node2){
        Node<E> early = node1.getPrevious();
        Node<E> later = node2.getNext();
        node2.setNext(node1);
        node2.setPrevious(early);
        node1.setPrevious(node2);
        node1.setNext(later);
        early.setNext(node2);
        later.setPrevious(node1);
    }
    
    /**
     * Returns node with the given index.
     * 
     * @param index
     * @return 
     */
    public Node<E> node(int index){
        assert index <= this.size;
        MyLinkedListIterator<E> it = new MyLinkedListIterator<E>(this, first);
        int count = 0;
        while(count != index){
            it.nextNode();
            count++;
        }
        return it.nextNode();
    }

    public Iterator<E> descendingIterator() {
        return new MyLinkedListDescendingIterator<E>(this, (isEmpty() ? last : last.getPrevious()));
    }

    /**
     * Helper methods for testing. Converts the given list to java.util.List
     */
    public static <E extends Comparable<E>> List<E> toList(MyLinkedList<E> list) {
        List arrayList = new ArrayList();
        Iterator<E> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        return arrayList;
    }
    
    //////////  Helper private methods //////////
    
    private Node<E> getCurrent(){
        return last.getPrevious();
    }

    protected Node<E> getFirst() {
        return first;
    }

    protected void setFirst(Node<E> first) {
        this.first = first;
    }

    protected Node<E> getLast() {
        return last;
    }
    
}
