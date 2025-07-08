package za.ac.sun.cs.algos;

/**
 * An entry maintaining a key--value pair.
 *
 * @param <K> the type of key stored in this entry
 * @param <V> the type of value stored in this entry
 */
public interface MapEntry<K, V> {

  /**
   * Returns the key of this entry.
   *
   * @return the key of this entry
   */
  K getKey();

  /**
   * Returns the value of this entry.
   *
   * @return the value of this entry
   */
  V getValue();
}
