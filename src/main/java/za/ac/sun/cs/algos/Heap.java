package za.ac.sun.cs.algos;

/**
 * A minimum-ordered heap.
 *
 * <p>Note that heaps do not permit <code>null</code> elements.
 *
 * @param <E> the type of element stored in this heap
 */
public interface Heap<E> extends BinaryTree<E> {

  /**
   * Adds the specified element to this heap.
   *
   * @param element the element to add to this heap
   * @throws ClassCastException if the specified element cannot be compared with elements currently
   *     in this heap according to the heap's ordering
   * @throws NullPointerException if the specified element is <code>null</code>
   */
  void addElement(E element);

  /**
   * Returns, but does not remove, the minimum element in this heap.
   *
   * @return the minimum element in this heap
   * @throws EmptyCollectionException if this heap contains no elements
   */
  E findMinimum();

  /**
   * Removes and returns the minimum element in this heap.
   *
   * @return the minimum element in this heap
   * @throws EmptyCollectionException if this heap contains no elements
   */
  E removeMinimum();
}
