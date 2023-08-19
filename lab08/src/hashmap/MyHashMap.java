package hashmap;

import java.util.Collection;
import java.util.Iterator;
import java.util.*;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Runjeet Narula
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int IC = 16;
    private double LF = 0.75;
    private int size = 0;

    /** Constructors */
    public MyHashMap() {
        buckets = createTable(IC);
        size = 0;
    }


    public MyHashMap(int initialCapacity) {
        this.IC = initialCapacity;
        buckets = createTable(initialCapacity);
        size = 0;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.IC = initialCapacity;
        this.LF = loadFactor;
        buckets = createTable(initialCapacity);
        size = 0;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] table = new Collection[tableSize];
        for (int i = 0; i < tableSize; i++) {
            table[i] = createBucket();
        }
        return table;
    }

    // TODO: Implement the methods of the Map61B Interface below
    public void clear() {
        size = 0;
        buckets = createTable(IC);
    }

    public boolean containsKey(K key) {
        return NodeGetter(key) != null;
    }

    public V get(K key) {
        Node node = NodeGetter(key);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    private Node NodeGetter(K key) {
        int bucketIndex = getIndexForBuckets(key);
        return NodeGetter(key, bucketIndex);
    }

    private Node NodeGetter(K key, int bucketIndex) {
        for (Node node : buckets[bucketIndex]) {
            if (node.key.equals(key)) {
                return node;
            }
        }
        return null;
    }

    public int size() {
        return size;
    }

    public Iterator<K> iterator() {
        return new H_M_I();
    }

    private class H_M_I implements Iterator<K> {
        private final Iterator<Node> nodeIterator = new H_M_N_I();

        public boolean hasNext() {
            return nodeIterator.hasNext();
        }

        public K next() {
            return nodeIterator.next().key;
        }
    }

    private class H_M_N_I implements Iterator<Node> {
        private final Iterator<Collection<Node>> bucketsIterator = Arrays.stream(buckets).iterator();
        private Iterator<Node> currentIterator;
        private int nodesLeft = size;

        public boolean hasNext() {
            return nodesLeft > 0;
        }

        public Node next() {
            if (currentIterator == null || !currentIterator.hasNext()) {
                Collection<Node> c = bucketsIterator.next();
                while (c.size() == 0) {
                    c = bucketsIterator.next();
                }
                currentIterator = c.iterator();
            }
            nodesLeft -= 1;
            return currentIterator.next();
        }
    }
    public void put(K key, V value) {
        int bucketIndex = getIndexForBuckets(key);
        Node n = NodeGetter(key, bucketIndex);
        if (n != null) {
            n.value = value;
            return;
        }
        n = createNode(key, value);
        buckets[bucketIndex].add(n);
        size += 1;
        if (hasReachedMaxLoad()) {
            resize(buckets.length * 2);
        }
    }

    private boolean hasReachedMaxLoad() {
        return ((double) size / (double) buckets.length) > LF;
    }

    private void resize(int capacity) {
        Collection<Node>[] c = createTable(capacity);
        Iterator<Node> i = new H_M_N_I();
        while (i.hasNext()) {
            Node n = i.next();
            int bIndex = getIndexForBuckets(n.key, c);
            c[bIndex].add(n);
        }
        buckets = c;
    }

    public Set<K> keySet() {
        HashSet<K> set = new HashSet<>();
        for (K key : this) {
            set.add(key);
        }
        return set;
    }

    public V remove(K key) {
        int buckIndex = getIndexForBuckets(key);
        Node tempNode = NodeGetter(key, buckIndex);
        if (tempNode == null) {
            return null;
        }
        size -= 1;
        buckets[buckIndex].remove(tempNode);
        return tempNode.value;
    }

    private int getIndexForBuckets(K key) {
        return getIndexForBuckets(key, buckets);
    }

    private int getIndexForBuckets(K key, Collection<Node>[] table) {
        int KHC = key.hashCode();
        return Math.floorMod(KHC, table.length);
    }
}
