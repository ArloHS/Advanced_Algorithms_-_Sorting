package za.ac.sun.cs.algos;

import java.lang.reflect.Array;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<E> implements List<E> {

    // Sentinel & Size
    private final Node<E> sentinel;
    private int size;
    // Modification counter. transient?
    private int modCount = 0;

    // Constructors: 1) Parameterless & 2) Data Array
    public LinkedList() {
        // Init Sentinels!
        sentinel = new Node<E>(null, null, null);
        sentinel.previous = sentinel;
        sentinel.next = sentinel;
        size = 0;

    }

    public LinkedList(E[] listElem) {
        // Init Sentinels!
        sentinel = new Node<E>(null, null, null);
        sentinel.previous = sentinel;
        sentinel.next = sentinel;

        // Populate array of elements into list.
        for (int i = 0; i < listElem.length; i++) {
            addToRear(listElem[i]);
        }
    }

    // Nested static Node class.
    private static class Node<E> {
        // 'Most limited visability modifiers' as per spec.
        private Node<E> previous;
        private E data;
        private Node<E> next;

        // Constructor for Node to init values
        private Node(Node<E> previous, E data, Node<E> next) {
            this.previous = previous;
            this.data = data;
            this.next = next;
        }

    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> currNode = sentinel.next;
            private int finalModCount = modCount;
            // As stated in the API docs, we must check once next() has run before we can
            // run remove().
            private boolean flagRemove = false;

            @Override
            public boolean hasNext() {
                return currNode != sentinel;
            }

            @Override
            public E next() {
                // No next element exception
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                // Modification Exception for race conditions
                if (finalModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                // May proceed to next node
                E currNodedata = currNode.data;
                currNode = currNode.next;

                // Update and allow flagRemove
                flagRemove = true;

                return currNodedata;
            }

            @Override
            public void remove() {
                // Modification Exception for race conditions
                if (finalModCount != modCount) {
                    throw new ConcurrentModificationException();
                }

                // As per API Docs
                if (!flagRemove) {
                    throw new IllegalStateException();
                }

                // Now remove the last node in the list
                Node<E> targetNodetoRemove = currNode.previous;
                LinkedList.this.remove(targetNodetoRemove);

                // Update expectedModCount
                finalModCount = modCount;
                // Set flag to false again
                flagRemove = false;
            }
        };
    }

    // Private helper methods
    private Node<E> find(E element) {
        Node<E> currNode = sentinel.next;
        while (sentinel != currNode) {
            if ((currNode.data == null && element == null) ||
                    (currNode.data != null && currNode.data.equals(element))) {
                return currNode;
            }

            currNode = currNode.next;
        }

        return null;
    }

    private void addAfter(E element, Node<E> target) {
        Node<E> newNode = new Node<E>(target, element, target.next);
        // Update pointers to new Nodes
        newNode.next.previous = newNode;
        target.next = newNode;

        // Increment counters
        modCount++;
        size++;
    }

    private E remove(Node<E> target) {
        if (target == null || target.equals(sentinel)) {
            throw new NoSuchElementException();
        }

        // Update pointers to new Nodes
        target.next.previous = target.previous;
        target.previous.next = target.next;
        // Increment modCount and decrement size
        modCount++;
        size--;

        return target.data;
    }

    // Rest of functions to implement
    @Override
    public void addAfter(E element, E target) {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        // Node is at head/first element of list
        Node<E> targetNode = find(target);
        if (targetNode == null) {
            throw new NoSuchElementException();
        }

        addAfter(element, targetNode);
    }

    @Override
    public void addToFront(E element) {
        addAfter(element, sentinel);
    }

    @Override
    public void addToRear(E element) {
        addAfter(element, sentinel.previous);
    }

    @Override
    public boolean contains(E element) {
        if (find(element) == null) {
            return false;
        }

        return true;
    }

    @Override
    public E first() {
        if (size == 0) {
            throw new EmptyCollectionException();
        }

        return sentinel.next.data;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E last() {
        if (size == 0) {
            throw new EmptyCollectionException();
        }

        return sentinel.previous.data;
    }

    @Override
    public E remove(E element) {
        if (size == 0) {
            throw new EmptyCollectionException();
        }

        Node<E> targetNode = find(element);
        if (targetNode == null) {
            throw new NoSuchElementException();
        }

        return remove(targetNode);
    }

    @Override
    public E removeFirst() {
        if (size == 0) {
            throw new EmptyCollectionException();
        }

        return remove(sentinel.next);
    }

    @Override
    public E removeLast() {
        if (size == 0) {
            throw new EmptyCollectionException();
        }

        return remove(sentinel.previous);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E[] toArray() {
        if (size == 0) {
            throw new EmptyCollectionException();
        }

        // Find element class
        Class<?> elementType = null;
        Iterator<E> iterator = iterator();

        // Keep checking elements until we find a non-null one
        while (iterator.hasNext()) {
            E element = iterator.next();
            if (element != null) {
                // Found a non-null element, save its type
                elementType = element.getClass();
                break;
            }
        }

        if (elementType == null) {
            throw new IllegalStateException();
        }

        // IS THIS ALLOWED TODO?
        @SuppressWarnings("unchecked")
        E[] result = (E[]) Array.newInstance(elementType, size);

        // Go through all elements again, put in array
        // Reset the iterator
        iterator = iterator();
        int i = 0;

        while (iterator.hasNext()) {
            result[i] = iterator.next();
            i++;
        }

        return result;
    }

    @Override
    public String toString() {
        Iterator<E> iterator = iterator();

        if (size <= 0) {
            return "( -empty- )";
        }

        String otp = "(";
        while (iterator.hasNext()) {
            E data = iterator.next();
            // Handles null safely better than toString()?
            otp += String.valueOf(data);
            if (iterator.hasNext()) {
                otp += ", ";
            }

        }

        return otp + ")";
    }

}
