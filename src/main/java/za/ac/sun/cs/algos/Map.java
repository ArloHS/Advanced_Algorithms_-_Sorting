package za.ac.sun.cs.algos;

/**
 * A mapping from keys to values.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public interface Map<K, V> {

  /**
   * Determines whether this map contains the specified key.
   *
   * @param key the key whose presence in this map is to be tested
   * @return <code>true</code> if this map contains a mapping for the specified key; otherwise,
   *     <code>false</code>
   * @throws NullPointerException if the specified key is <code>null</code> and this map does not
   *     permit <code>null</code> keys (optional)
   */
  boolean containsKey(K key);

  /**
   * Determines whether this map fontains a key to the specified value.
   *
   * @param value the value whose presence in this map is to be tested
   * @return <code>true</code> if this map contains one or more keys to the specified value;
   *     otherwise, <code>false</code>
   * @throws NullPointerException if the specified value is <code>null</code> and this map does not
   *     permit <code>null</code> values (optional)
   */
  boolean containsValue(V value);

  /**
   * Returns an iterable collection of the key--value pairs stored in this map.
   *
   * @return an iterable collection of the key--value pairs stored in this map
   */
  Iterable<MapEntry<K, V>> entries();

  /**
   * Returns the value to which the specified key is mapped, or <code>null</code> if this map
   * contains no mapping for the key.
   *
   * <p>If this map permits <code>null</code> values, the a return value of <code>null</code> does
   * not necessarily indicate that the map contains no mapping for the key; it's also possible that
   * the map explicitly maps the key to <code>null</code>. The {@link #containsKey(V)} method may be
   * used to distinguish these two cases.
   *
   * @param key the key for which to retrieve the value
   * @return the value associated with the specified key, or <code>null</code> if there was no
   *     mapping for the key
   * @throws NullPointerException if the specified key is <code>null</code> and this map does no
   *     permit <code>null</code> keys (optional)
   */
  V get(K key);

  /**
   * Determines whether this map contains no mappings.
   *
   * @return <code>true</code> if this map has no mappings; otherwise, <code>false</code>
   */
  boolean isEmpty();

  /**
   * Returns an iterable collection of the keys maintained by this map.
   *
   * @return an iterable collection of the keys maintained by this map
   */
  Iterable<K> keys();

  /**
   * Inserts a new map from the specified key to the specified value, and returns the old value if
   * the key is already maintained by this map.
   *
   * @param key the key with which the specified value is to be associated
   * @param value the value to be associated with the specified key
   * @return the previous value associated with the key, or <code>null</code> if there was no
   *     mapping for the key; a <code>null</code> return value can also indicate that the map
   *     previously associated <code>null</code> with the key if the implementation permits <code>
   *     null</code> values.
   * @throws NullPointerException if the specified key or value is null and this map does not permit
   *     <code>null</code> keys or values (optional)
   */
  V put(K key, V value);

  /**
   * Removes the mapping associated with the specified key, and returns its value.
   *
   * @param key the key for which to remove the mapping
   * @return the value previously associated with the key, or <code>null</code> if there was no
   *     mapping for the key; a <code>null</code> return value can also indicate that the map
   *     previously associated <code>null</code> with the key if the implementation permits <code>
   *     null</code> values
   * @throws NullPointerException if the specified key is <code>null</code> and this map does not
   *     permit <code>null</code> keys (optional)
   */
  V remove(K key);

  /**
   * Returns the number of entries in this map.
   *
   * @return the number of entries in this map
   */
  int size();

  /**
   * Returns an iterable collection of the values stored in this map.
   *
   * @return an iterable collection of the values stored in this map
   */
  Iterable<V> values();
}
