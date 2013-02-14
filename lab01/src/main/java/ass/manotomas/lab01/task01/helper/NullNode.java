package ass.manotomas.lab01.task01.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tomas Mano <tomasmano@gmail.com>
 */
public class NullNode<E extends Comparable<E>> implements Node<E>{
    
    public static final Logger LOG = LoggerFactory.getLogger(NullNode.class);
    
    private E element;
    private Node<E> next;
    private Node<E> previous;

    public NullNode() {
        LOG.debug("Initializing null node...");
    }

    public boolean hasNext() {
        return false;
    }

    public boolean hasPrevious() {
        return false;
    }

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
        LOG.debug("Setting previous element on NULL_NODE INSTANCE: [{}]", previous.getElement());
        this.previous = previous;
    }

    public int compareTo(E o) {
        return element.compareTo(o);
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
        final NullNode<E> other = (NullNode<E>) obj;
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
        return "Null Node";
    }
    
}
