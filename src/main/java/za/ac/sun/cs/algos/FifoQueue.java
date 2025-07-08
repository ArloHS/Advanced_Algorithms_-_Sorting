package za.ac.sun.cs.algos;

public class FifoQueue<E> implements Queue<E> {
    private final LinkedList<E> list;

    public FifoQueue() {
        list = new LinkedList<E>();
    }

    @Override
    public E dequeue() {
        if (list.isEmpty()) {
            throw new EmptyCollectionException();
        }

        return list.removeFirst();
    }

    @Override
    public void enqueue(E element) {
        if (element == null) {
            throw new NullPointerException();
        }

        list.addToRear(element);
    }

    @Override
    public E peek() {
        if (list.isEmpty()) {
            throw new EmptyCollectionException();
        }

        return list.first();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public String toString() {
        return list.toString();
    }

}
