package algos.iq;

import edu.princeton.cs.algs4.Stack;

public class QueueUsingStacks<T> {

    private Stack<T> inbox = new Stack<>();
    private Stack<T> outbox = new Stack<>();

    public void enqueue(T element) {
        inbox.push(element);
    }

    public T dequeue() {
        if (outbox.isEmpty()) {
            while (!inbox.isEmpty())
                outbox.push(inbox.pop());
        }
        return outbox.pop();
    }

    public int size() {
        return inbox.size() + outbox.size();
    }

}
