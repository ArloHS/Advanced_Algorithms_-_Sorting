package za.ac.sun.cs.algos;

import java.util.Iterator;

/**
 * A binary tree.
 *
 * @param <E> the type of element stored in this binary tree
 */
public interface BinaryTree<E> {

  /**
   * Determines whether this binary tree contains the specified element.
   *
   * @param element the element whose presence in this binary tree is to be tested
   * @return <code>true</code> if the specified element is in this binary tree; otherwise <code>
   *     false</code>
   * @throws NullPointerException if the specified element is <code>null</code> and this binary tree
   *     does not permit <code>null</code> elements (optional)
   */
  boolean contains(E element);

  /**
   * Returns the specified element if it exists in this binary tree.
   *
   * @param element the element sought in this binary tree
   * @return the specified element if it exists in this binary tree
   * @throws NullPointerException if the specified element is <code>null</code> and this binary tree
   *     does not permit <code>null</code> elements (optional)
   */
  E find(E element);

  /**
   * Returns the root element of this binary tree.
   *
   * @return the root element of this binary tree
   * @throws EmptyCollectionException if this binary treee contains no elements
   */
  E getRoot();

  /**
   * Determines whether this binary tree contains no elements.
   *
   * @return <code>true</code> if this binary tree contains no elements; otherwise, <code>false
   *     </code>
   */
  boolean isEmpty();

  /**
   * Returns an in-order iterator over the elements in this binary tree.
   *
   * @return an in-order iterator over the elements in this binary tree
   */
  Iterator<E> iteratorInOrder();

  /**
   * Returns a level-order iterator over the elements in this binary tree.
   *
   * @return a level-order iterator over the elements in this binary tree
   */
  Iterator<E> iteratorLevelOrder();

  /**
   * Returns a postorder iterator over the elements in this binary tree.
   *
   * @return a postorder iterator over the elements in this binary tree
   */
  Iterator<E> iteratorPostOrder();

  /**
   * Returns a preorder iterator over the elements in this binary tree.
   *
   * @return a preorder iterator over the elements in this binary tree
   */
  Iterator<E> iteratorPreOrder();

  /**
   * Returns the number of elements in this binary tree.
   *
   * @return the number of elements in this binary tree
   */
  int size();

  /**
   * Returns a string representation of this binary tree.
   *
   * @return a string representation of this binary tree
   */
  String toString();
}
