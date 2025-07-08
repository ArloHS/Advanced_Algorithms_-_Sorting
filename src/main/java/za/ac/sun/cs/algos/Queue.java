package za.ac.sun.cs.algos;

/**
 * A queue, with the queue discipline to be specified by implementing classes.
 *
 * @param <E> the type of element stored in this queue
 */
public interface Queue<E> {

  /**
   * Removes and returns the element at the front this queue.
   *
   * @return the element at the front of this queue
   * @throws EmptyCollectionException if this queue contains no elements
   */
  E dequeue();

  /**
   * Adds an element to the rear of this queue.
   *
   * @param element the element to add
   * @throws NullPointerException if the element is <code>null</code>
   */
  void enqueue(E element);

  /**
   * Returns, but does not remove, the element at the front of this queue.
   *
   * @return the element at the front of this queue
   * @throws EmptyCollectionException if this queue contains no elements
   */
  E peek();

  /**
   * Determines whether this queue contains no elements.
   *
   * @return <code>true</code> if this queue contains no elements; otherwise, <code>false</code>
   */
  boolean isEmpty();

  /**
   * Returns the number of elements in this queue.
   *
   * @return the number of elements in this queue
   */
  int size();

  /**
   * Returns a string representation of this queue.
   *
   * @return a string representation of this queue
   */
  String toString();
}
