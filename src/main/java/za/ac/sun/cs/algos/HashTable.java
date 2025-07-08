package za.ac.sun.cs.algos;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class HashTable<K, V> implements Map<K, V> {
    // Constants
    public static final int DEFAULT_INITIAL_CAPACITY = 16;
    public static final float DEFAULT_MAXIMUM_LOAD_FACTOR = 0.75f;

    // Variables
    private HashTableEntry<K, V>[] table;
    private final float maxLoadFactor;
    private int scale;
    private int shift;
    private final Random random = new Random();
    private int threshold;
    private int size = 0;

    // HashTable Chained Constructors
    public HashTable() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_MAXIMUM_LOAD_FACTOR);
    }

    public HashTable(int initialCapacity) {
        this(initialCapacity, DEFAULT_MAXIMUM_LOAD_FACTOR);
    }

    public HashTable(int initialCapacity, float maxLoadFactor) {
        // Checks for Capacity > 0 and loadfactor good enough as Javadocs wants them
        // TODO?...unsure...surely correct?
        if (initialCapacity < 1)
            throw new IllegalArgumentException();
        if (maxLoadFactor <= 0 || Float.isNaN(maxLoadFactor))
            throw new IllegalArgumentException();

        this.maxLoadFactor = maxLoadFactor;
        this.table = newTable(initialCapacity);
        this.threshold = (int) maxLoadFactor * initialCapacity;
        // Init MAD factors
        this.scale = random.nextInt(table.length - 1) + 1;
        this.shift = random.nextInt(table.length);

    }

    public static class HashTableEntry<K, V> implements MapEntry<K, V> {
        private final K key;
        private V value;
        private HashTableEntry<K, V> next;

        private HashTableEntry(K key, V value, HashTableEntry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

    }

    // Private Helpers
    @SuppressWarnings("unchecked")
    private HashTableEntry<K, V>[] newTable(int length) {
        return (HashTableEntry<K, V>[]) new HashTableEntry[length];
    }

    private HashTableEntry<K, V> getEntry(K key) {
        int index = hash(key);
        HashTableEntry<K, V> currNode = table[index];

        // Traverse List
        while (currNode != null) {
            if (key == currNode.getKey() || (key != null && key.equals(currNode.getKey()))) {
                return currNode;
            }

            currNode = currNode.next;
        }

        // Not found
        return null;
    }

    // Converts a key into a valid table index using MAD
    private int hash(K key) {
        // Handle null at index 0?
        if (key == null) {
            return 0;
        }

        int mad = (key.hashCode() * scale + shift);
        return Math.abs(mad) % table.length;
    }

    private void resize(int capacity) {
        HashTableEntry<K, V>[] oldTable = table;
        table = newTable(capacity);
        scale = random.nextInt(capacity - 1) + 1;
        shift = random.nextInt(capacity);
        threshold = (int) (maxLoadFactor * capacity);
        size = 0;

        for (HashTableEntry<K, V> entry : oldTable) {
            while (entry != null) {
                put(entry.key, entry.value);
                entry = entry.next;
            }
        }
    }

    @Override
    public boolean containsKey(K key) {
        HashTableEntry<K, V> entry = getEntry(key);
        return entry != null;
    }

    @Override
    public boolean containsValue(V value) {
        for (int i = 0; i < table.length; i++) {
            HashTableEntry<K, V> currEntry = table[i];
            while (currEntry != null) {
                // Massive if, checks null as per spec also
                if ((value == null && currEntry.getValue() == null)
                        || (value != null && value.equals(currEntry.getValue()))) {
                    return true;
                }

                currEntry = currEntry.next;
            }
        }

        return false;
    }

    @Override
    public Iterable<MapEntry<K, V>> entries() {
        return () -> new HashTableIterator<MapEntry<K, V>>() {
            @Override
            public MapEntry<K, V> next() {
                return getNext();
            }
        };
    }

    @Override
    public V get(K key) {
        HashTableEntry<K, V> entry = getEntry(key);

        if (entry == null) {
            return null;
        } else {
            return entry.getValue();
        }

    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterable<K> keys() {
        return () -> new HashTableIterator<K>() {
            @Override
            public K next() {
                return getNext().getKey();
            }
        };
    }

    @Override
    public V put(K key, V value) {
        int index = hash(key);
        HashTableEntry<K, V> currEntry = getEntry(key);

        // Key exists, Traverse List
        if (currEntry != null) {
            V prevVal = currEntry.getValue();
            currEntry.value = value;
            return prevVal;
        }

        // Key does not exist, add
        table[index] = new HashTableEntry<K, V>(key, value, table[index]);
        size++;

        // Check threshold
        if (size > threshold) {
            resize(2 * table.length);
        }

        return null;
    }

    @Override
    public V remove(K key) {
        int index = hash(key);
        HashTableEntry<K, V> prevNode = null;
        HashTableEntry<K, V> currNode = table[index];

        // Cant use iterator as per spec
        while (currNode != null) {
            if (key == currNode.key || (key != null && key.equals(currNode.key))) {
                if (prevNode == null) {
                    table[index] = currNode.next;

                } else {
                    prevNode.next = currNode.next;
                }

                size--;
                V oldValue = currNode.value;
                // Resize as per spec if removed
                if (size < (int) (maxLoadFactor * table.length / 4) && table.length > 1) {
                    resize(Math.max(1, table.length / 2));
                }

                return oldValue;
            }

            prevNode = currNode;
            currNode = currNode.next;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterable<V> values() {
        return () -> new HashTableIterator<V>() {
            @Override
            public V next() {
                return getNext().getValue();
            }
        };
    }

    private abstract class HashTableIterator<E> implements Iterator<E> {
        private int index = 0;
        private HashTableEntry<K, V> next;

        private HashTableIterator() {
            next = findNext();
        }

        private HashTableEntry<K, V> findNext() {
            while (index < table.length) {
                if (table[index] != null) {
                    return table[index];
                }

                index++;
            }

            return null;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        // Package private?
        HashTableEntry<K, V> getNext() {
            if (next == null) {
                throw new NoSuchElementException();
            }

            HashTableEntry<K, V> currNode = next;
            if (next.next != null) {
                next = next.next; // lol

            } else {
                index++;
                next = findNext();
            }

            return currNode;
        }

        // Stay Abstract and Unimplemented as per spec
        @Override
        public abstract E next();

        // Unsupported as per spec
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
