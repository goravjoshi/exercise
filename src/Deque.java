import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private static final int INITIAL_SIZE = 16;

    private Object[] items;
    private int head, tail;

    public Deque() {
        // construct an empty deque
        items = new Object[INITIAL_SIZE];
    }

    public boolean isEmpty() {
        // is the deque empty?
        return head == tail;
    }

    public int size() {
        // return the number of items on the deque
        return (tail - head) & items.length - 1;
    }

    public void addFirst(Item item) {
        // add the item to the front
        checkForNotNull(item);
        head = (head - 1) & (items.length - 1);
        items[head] = item;
        if (tail + 1 == head) {
            resize();
        }

    }

    private void resize() {
        Object[] newItemsContainer = new Object[2 * items.length];
        if (tail > head) {
            System.arraycopy(items, head, newItemsContainer, 0, this.size());
        } else {
            System.arraycopy(items, head, newItemsContainer, 0,
                    items.length - head);
            System.arraycopy(items, 0, newItemsContainer,
                    (items.length - head) - 1, tail + 1);
        }
        head = 0;
        tail = this.size() - 1;
        items = newItemsContainer;
    }

    public void addLast(Item item) {
        // add the item to the end
        checkForNotNull(item);
        items[tail] = item;
        tail = (tail + 1) & (items.length - 1);
        if (tail == head) {
            resize();
        }
    }

    public Item removeFirst() {
        // remove and return the item from the front
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item r = (Item) items[head];
        items[head] = null;
        head = (head + 1) & items.length - 1;
        return r;
    }

    public Item removeLast() {
        // remove and return the item from the end
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        tail = (tail - 1) & (items.length - 1);
        Item r = (Item) items[tail];
        items[tail] = null;
        return r;
    }

    public Iterator<Item> iterator() {
        // return an iterator over items in order from front to end
        return new Iterator<Item>() {
            private int index = head;

            @Override
            public boolean hasNext() {              
                return (index + 1) != tail;
            }

            @Override
            public Item next() {
                if ((index + 1) != tail) {
                    throw new NoSuchElementException();
                }
                index = (index + 1) & items.length - 1;
                Item i = (Item) items[index];
                return i;
            }
        };
    }

    public static void main(String[] args) {
        // unit testing (optional)

        Deque<Integer> d = new Deque<>();
        d.addLast(3);
        d.addLast(6);
        d.addLast(1);
        d.addLast(9);
        d.addFirst(10);

        assert d.size() == 5;
        System.out.println("Printing: ");
        for (int integer : d) {
            System.out.println(integer);
        }

    }

    private void checkForNotNull(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
    }
}