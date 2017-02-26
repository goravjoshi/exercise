import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    
    private Node<Item> first;
    private Node<Item> last;  
    private int size = 0;
    
    public Deque() {
        // construct an empty deque
    }

    public boolean isEmpty() {
        // is the deque empty?
        return false;
    }

    public int size() {
        // return the number of items on the deque
        return size;
    }

    public void addFirst(Item item) {
        // add the item to the front
        checkForNotNull(item);
        Node<Item> aNode = new Node<>(item);        
        if(first == null) {
            last = first = aNode;
        } else {
           aNode.next = first;
           first = aNode;
        }
        size++;
    }    

    public void addLast(Item item) {
        // add the item to the end
        checkForNotNull(item);
        Node<Item> aNode = new Node<>(item);
        if(last == null) {
            assert first == null;
            last = first = aNode;
        } else {
            last.next = aNode;
            last = aNode;
        }
        size++;
    }

    public Item removeFirst() {
        // remove and return the item from the front
        if(size == 0) {
            throw new NoSuchElementException();
        }
        Node<Item> result = first;
        first = first.next;
        result.next = null;
        return result.data;
    }

    public Item removeLast() {
        // remove and return the item from the end
        if(size == 0) {
            throw new NoSuchElementException();
        }
        Node<Item> result = last;
        
        first = first.next;
        result.next = null;
        return result.data;
    }

    public Iterator<Item> iterator() {
        // return an iterator over items in order from front to end
        return null;
    }

    public static void main(String[] args) {
        // unit testing (optional)
    }
    
    private void checkForNotNull(Item item) {
        if(item == null) {
            throw new NullPointerException();
        }
    }
    
    static class Node<Item> {
        final Item data;
        Node<Item> next;
        
        public Node(Item data) {
            super();
            this.data = data;
        }
    }    
}