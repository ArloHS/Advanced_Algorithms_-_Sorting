package za.ac.sun.cs.algos;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A skeletal array-based implementation of a binary tree.
 *
 * <p>
 * This class does not permit <code>null</code> elements: Overriding classes
 * <strong>must</strong> inhibit this behaviour if necessary.
 *
 * @param <E> the type of element stored in this binary tree
 */
public abstract class ArrayBinaryTree<E> implements BinaryTree<E> {

  /** The default initial capacity. */
  public static final int DEFAULT_INITIAL_CAPACITY = 16;

  /** The number of nodes in this binary tree. */
  protected int size;

  /** The array in which the elements are stored. */
  protected E[] tree;

  /**
   * Constructs a new empty array-based binary tree with the default initial
   * capacity.
   */
  protected ArrayBinaryTree() {
    size = 0;
    tree = newArray(DEFAULT_INITIAL_CAPACITY);
  }

  /**
   * Ensures the capacity of tree by doubling the size of the tree array if
   * necessary.
   */
  protected void ensureCapacity() {
    // Case tree array is null
    // if (tree == null) {
    // throw new IllegalStateException("Tree array is null in ensureCapacity");
    // }

    // Case tree is full
    if (tree.length == size) {
      // Update tree to new array
      tree = newArray(2 * tree.length);
    }
  }

  /**
   * Recursively traverses the subtree rooted at the specified node, and adds
   * these nodes to the
   * specified list in-order.
   *
   * @param node the root of the subtree to traverse in-order
   * @param list the list to which to add the traversed nodes
   */
  protected void listInOrder(int node, List<E> list) {
    // Size of array exceeded
    if (size <= node) {
      return;
    }
    // LEFT -> ROOT -> RIGHT
    // Left subtree call
    listInOrder(2 * node + 1, list);

    // Add root
    list.addToRear(tree[node]);

    // Right subtree call
    listInOrder(2 * node + 2, list);
  }

  /**
   * Recursively traverses the subtree rooted at the specified node, and adds
   * these nodes to the
   * specified list postorder.
   *
   * @param node the root of the subtree to traverse postorder
   * @param list the list to which to add the traversed nodes
   */
  protected void listPostOrder(int node, List<E> list) {
    // Size of array exceeded
    if (size <= node) {
      return;
    }
    // LEFT -> RIGHT -> ROOT
    // Left subtree call
    listPostOrder(2 * node + 1, list);

    // Right subtree call
    listPostOrder(2 * node + 2, list);

    // Add root
    list.addToRear(tree[node]);
  }

  /**
   * Recursively traverses the subtree rooted the specified node, and adds these
   * nodes to the
   * specified list preorder.
   *
   * @param node
   * @param list
   */
  protected void listPreOrder(int node, List<E> list) {
    // Size of array exceeded
    if (size <= node) {
      return;
    }
    // ROOT -> LEFT -> RIGHT
    // Add root
    list.addToRear(tree[node]);

    // Left subtree call
    listPreOrder(2 * node + 1, list);

    // Right subtree call
    listPreOrder(2 * node + 2, list);
  }

  /**
   * Returns a new array of tree nodes of the specified length and cast to the
   * appropriate type.
   *
   * @param length the length of the new array
   * @return a new array of tree nodes, cast to the appropriate abstract type
   */

  protected E[] newArray(int length) {
    @SuppressWarnings("unchecked")
    E[] newTreeArray = (E[]) new Object[length];
    if (tree != null && size > 0) {
      // Copy only up to the minimum of current size and new length to avoid
      // IndexOutOfBounds
      int copyLength = Math.min(tree.length, length);
      System.arraycopy(tree, 0, newTreeArray, 0, copyLength);
    }

    return newTreeArray;
  }

  @Override
  public boolean contains(E element) {
    if (element == null) {
      throw new NullPointerException();
    }

    // Tree array is null
    if (tree == null) {
      throw new IllegalStateException();
    }

    for (int i = 0; i < size; i++) {
      if (tree[i].equals(element)) {
        return true;
      }
    }

    return false;
  }

  @Override
  public E find(E element) {
    // TODO IS THIS EVEN CORRECT?!
    if (element == null) {
      throw new NullPointerException();
    }

    // Tree array is null
    if (tree == null) {
      throw new IllegalStateException();
    }

    E temp = null;
    boolean find = false;

    for (int i = 0; i < size; i++) {
      if (tree[i].equals(element)) {
        return tree[i];
      }
    }
    // TODO WHAT SHOULD I RETURN IF NOT FOUND

    return null;
  }

  @Override
  public E getRoot() {
    if (size == 0) {
      throw new EmptyCollectionException();
    }

    // Tree array is null
    if (tree == null) {
      throw new IllegalStateException();
    }

    // TODO IS THIS CORRECT?
    return tree[0];
  }

  @Override
  public boolean isEmpty() {
    return (size == 0);
  }

  @Override
  public Iterator<E> iteratorInOrder() {
    // Tree array is null
    if (tree == null) {
      throw new IllegalStateException();
    }

    List<E> newListInOrder = new LinkedList<E>();
    if (size > 0) {
      listInOrder(0, newListInOrder);
    }

    return newListInOrder.iterator();
  }

  @Override
  public Iterator<E> iteratorPostOrder() {
    // Tree array is null
    if (tree == null) {
      throw new IllegalStateException();
    }

    List<E> newListPostOrder = new LinkedList<E>();
    if (size > 0) {
      listPostOrder(0, newListPostOrder);
    }

    return newListPostOrder.iterator();
  }

  @Override
  public Iterator<E> iteratorPreOrder() {
    // Tree array is null
    if (tree == null) {
      throw new IllegalStateException();
    }

    List<E> newListPreOrder = new LinkedList<E>();
    if (size > 0) {
      listPreOrder(0, newListPreOrder);
    }

    return newListPreOrder.iterator();
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public String toString() {
    // Case empty
    if (size == 0) {
      return "[]";
    }

    // Tree array is null
    if (tree == null) {
      throw new IllegalStateException();
    }

    String otp = "[";
    for (int i = 0; i < size; i++) {
      otp += tree[i];
      // Add comma if not last element
      if (size - 1 > i) {
        otp += ", ";
      }
    }

    return otp + "]";
  }
}
