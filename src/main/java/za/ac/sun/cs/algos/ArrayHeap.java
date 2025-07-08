package za.ac.sun.cs.algos;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An array-based implementation of {@link Heap}. The elements are ordered
 * either by their natural
 * order, in which case they must be comparable, or by a
 * {@link java.util.Comparator} provided at
 * heap construction time.
 *
 * @param <E> the type of element stored in this heap
 */
public final class ArrayHeap<E> extends ArrayBinaryTree<E> implements Heap<E> {

  /**
   * The (optional) comparator if the the natural ordering of the elements is not
   * to be used.
   */
  // uncomment:
  private final Comparator<? super E> comparator;

  /**
   * Constructs a new array-based heap that is ordered by the natural order of the
   * elements, which
   * is to say, elements <strong>must</strong> implement
   * {@link java.util.Comparable}.
   */
  public ArrayHeap() {
    super();
    this.comparator = null;

  }

  /**
   * Constructs a new array-based that is ordered using the specified comparator.
   *
   * @param comparator the comparator that is to be used in ordering this heap
   * @throws NullPointerException if the comparator is <code>null</code>
   */
  public ArrayHeap(Comparator<? super E> comparator) {
    super();
    if (comparator == null) {
      throw new NullPointerException();
    }

    this.comparator = comparator;
  }

  @Override
  public Iterator<E> iteratorLevelOrder() {
    // Return iterator over tree from 0 to size-1
    return new Iterator<E>() {
      private int current = 0;

      @Override
      public boolean hasNext() {
        if (size == 0) {
          return false;
        }

        return current < size;
      }

      @Override
      public E next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }

        E element = tree[current];
        current++;
        return element;
      }
    };
  }

  /**
   * @throws ClassCastException   if the specified element cannot be compared with
   *                              the elements
   *                              current in this heap according to the heap's
   *                              ordering
   * @throws NullPointerException if the specified element is <code>null</code>
   */
  @Override
  public void addElement(E element) {
    if (element == null) {
      throw new NullPointerException();
    }

    ensureCapacity();
    tree[size] = element;
    size++;
    try {
      swim(element);

    } catch (ClassCastException e) {
      throw new ClassCastException();
    }
  }

  /**
   * @throws NullPointerException if the specified element is <code>null</code>
   */
  @Override
  public boolean contains(E element) {
    if (element == null) {
      throw new NullPointerException();
    }

    return super.contains(element);
  }

  /**
   * @throws NullPointerException if the specified element is <code>null</code>
   */
  @Override
  public E find(E element) {
    if (element == null) {
      throw new NullPointerException();
    }

    return super.find(element);
  }

  /**
   * @throws EmptyCollectionException if this heap is empty
   */
  @Override
  public E findMinimum() {
    if (size == 0) {
      throw new EmptyCollectionException();
    }

    // Min is always at root?
    return tree[0];
  }

  @Override
  public E removeMinimum() {
    // TODO UNSURE!!
    if (size == 0) {
      throw new EmptyCollectionException();
    }

    // Store the minimum to return
    E min = tree[0];
    // Replace root with last element
    tree[0] = tree[size - 1];
    size--;
    tree[size] = null;

    // Sink the new root if heap isnt empty
    if (size > 0) {
      sink(tree[0]);
    }

    return min;
  }

  private void sink(E element) {
    // TODO: what does element do?...confusion

    // Left Child 2i + 1
    // Right Child 2i + 2

    // Always sink the root?
    int index = 0;
    while (true) {
      int left = 2 * index + 1;
      int right = 2 * index + 2;
      int smallest = index;
      if (left < size && compare(tree[left], tree[smallest]) < 0) {
        smallest = left;
      }

      if (right < size && compare(tree[right], tree[smallest]) < 0) {
        smallest = right;
      }

      if (smallest == index) {
        break;
      }

      swapElem(index, smallest);
      index = smallest;
    }
  }

  private void swim(E element) {
    // What does elemnent do TODO?!

    // If heap is empty or only root
    if (size <= 1) {
      return;
    }

    // Start at the last element of tree
    int index = size - 1;
    while (index > 0) {
      int parent = (index - 1) / 2;
      if (compare(tree[index], tree[parent]) < 0) {
        swapElem(index, parent);
        index = parent;

      } else {
        break;
      }
    }
  }

  @SuppressWarnings("unchecked")
  private int compare(E a, E b) {
    if (comparator != null) {
      return comparator.compare(a, b);
    }

    if (a instanceof Comparable && b instanceof Comparable) {
      // A disgusting cast to use compareTo
      return ((Comparable<E>) a).compareTo(b);
    }

    throw new ClassCastException("Elements are not comparable");
  }

  private void swapElem(int indexA, int indexB) {
    E temp = tree[indexB];
    tree[indexB] = tree[indexA];
    tree[indexA] = temp;
  }

  @Override
  public String toString() {
    if (size == 0) {
      return "[ -empty- ]";
    }

    String otp = "[";
    for (int i = 0; i < size; i++) {
      otp += tree[i];
      if (i < size - 1) {
        otp += ", ";
      }
    }

    return otp += "]";
  }
}
