package za.ac.sun.cs.algos;

import java.util.Comparator;

public class PriorityQueue<E> implements Queue<E> {
    private final ArrayHeap<E> heap;

    public PriorityQueue() {
        // Natural Ordring as per spec
        heap = new ArrayHeap<E>();
    }

    public PriorityQueue(Comparator<? super E> comparator) {
        // Custom Ordring as per spec
        heap = new ArrayHeap<E>(comparator);
    }

    @Override
    public E dequeue() {
        return heap.removeMinimum();
    }

    @Override
    public void enqueue(E element) {
        heap.addElement(element);
    }

    @Override
    public E peek() {
        return heap.findMinimum();
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public String toString() {
        return heap.toString();
    }

}
