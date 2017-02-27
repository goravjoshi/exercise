import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int INITIAL_SIZE = 16;

    private Object[] elements;
    private int size = 0;

    public RandomizedQueue() {
        elements = new Object[INITIAL_SIZE];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        // return the number of items on the queue
        return size;
    }

    public void enqueue(Item item) {
        // add the item
        if (item == null)
            throw new NullPointerException();
        if (size + 1 == elements.length) {
            resize();
        }
        elements[size++] = item;
    }

    public Item dequeue() {
        if (size == 0)
            throw new NoSuchElementException();
        int random = StdRandom.uniform(0, size);
        Item toReturn = (Item) elements[random];
        elements[random] = elements[--size];
        elements[size] = null;
        if (elements.length / 4 > size) {
            shrink();
        }
        return toReturn;
    }

    public Item sample() {
        if (size == 0)
            throw new NoSuchElementException();
        int random = StdRandom.uniform(0, size);
        Item toReturn = (Item) elements[random];
        return toReturn;
    }

    public Iterator<Item> iterator() {
        // return an independent iterator over items in random order
        Object[] copy = Arrays.copyOf(elements, size);
        return new Iterator<Item>() {
            private int localSize = RandomizedQueue.this.size;

            @Override
            public boolean hasNext() {
                return localSize > 0;
            }

            @Override
            public Item next() {
                if (localSize == 0) throw new NoSuchElementException();
                int random = StdRandom.uniform(0, localSize);
                Item toReturn = (Item) copy[random];
                copy[random] = copy[--localSize];
                copy[localSize] = null;
                return toReturn;
            }

        };
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",");
        for (Item i : this) {
            sj.add(i.toString());
        }
        return "[" + sj.toString() + "]";

    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        q.enqueue(4);
        q.enqueue(1);
        q.enqueue(9);
        q.enqueue(0);
        q.enqueue(3);
        q.enqueue(7);

        StringJoiner sj = new StringJoiner(",");
        for (int integer : q) {
            sj.add(String.valueOf(integer));
        }
        System.out.println("[" + sj.toString() + "]");

        System.out.println("D: " + q.dequeue());
        System.out.println("D: " + q.dequeue());

        sj = new StringJoiner(",");
        for (int integer : q) {
            sj.add(String.valueOf(integer));
        }
        System.out.println("[" + sj.toString() + "]");

    }

    private void resize() {
        int newCapacity = elements.length << 1;
        Object[] newElements = new Object[newCapacity];
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }

    private void shrink() {
        int newCapacity = elements.length / 2;
        Object[] newElements = new Object[newCapacity];
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }

}