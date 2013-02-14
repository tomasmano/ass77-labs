package ass.manotomas.lab01.task01.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class RealNode<E extends Comparable<E>> implements Node<E>{
    
    public static final Logger LOG = LoggerFactory.getLogger(RealNode.class);
    
    private E element;
    private Node<E> next;
    private Node<E> previous;

    public RealNode() {
    }

    public RealNode(E element) {
        this.element = element;
    }

    public RealNode(E element, Node<E> previous, Node<E> next) {
        LOG.debug("Creating node with elem [{}], prev: {}, next: {}", new Object[]{element, previous.getElement(), next.getElement()});
        this.element = element;
        this.previous = previous;
        this.next = next;
        this.previous.setNext(this);
        this.next.setPrevious(this);
    }
    
    //////////  Helper Methods  //////////
    
    public void insert(Node<E> inserted){
        
    }
    
    public boolean hasNext(){
        return next != null;
    }

    public boolean hasPrevious() {
        return previous != null;
    }
    
    //////////  Getters / Setters  //////////

    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

    public Node<E> getPrevious() {
        return previous;
    }

    public void setPrevious(Node<E> previous) {
        this.previous = previous;
    }

    public int compareTo(E o) {
        return o != null ? element.compareTo(o) : 1;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + (this.element != null ? this.element.hashCode() : 0);
        hash = 43 * hash + (this.next != null ? this.next.hashCode() : 0);
        hash = 43 * hash + (this.previous != null ? this.previous.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RealNode<E> other = (RealNode<E>) obj;
        if (this.element != other.element && (this.element == null || !this.element.equals(other.element))) {
            return false;
        }
        if (this.next != other.next && (this.next == null || !this.next.equals(other.next))) {
            return false;
        }
        if (this.previous != other.previous && (this.previous == null || !this.previous.equals(other.previous))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Node{" + "element=" + element + ", next=" + (next instanceof NullNode ? "NULL_NODE" : next.getElement()) + ", previous=" + (previous instanceof NullNode ? "NULL_NODE" : previous.getElement()) + '}';
    }
    
}
