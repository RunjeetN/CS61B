import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    // class variables
    private BSTNode root;
    private int size;

    private class BSTNode {
        private K key;
        private V value;
        private BSTNode left;
        private BSTNode right;

        private BSTNode(K key, V value, BSTNode left, BSTNode right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    public BSTMap() {
        root = new BSTNode(null, null, null, null);
        size = 0;
    }

    @Override
    public void put(K key, V value) {
        // base cases: if root key is -1, or you're at the root of an already established tree
        if (root.key == null) {
            root = new BSTNode(key, value, null, null);
            size++;
        }
        putHelper(root, key, value);
    }

    private BSTNode putHelper(BSTNode root, K key, V value) {
        // base case
        if (root == null) {
            size++;
            return new BSTNode(key, value, null, null);
        }
        // recursive checks
        if (key.compareTo(root.key) < 0) {
            root.left = putHelper(root.left, key, value); // if key is less, go to the left
        } else if (key.compareTo(root.key) > 0) {
            root.right = putHelper(root.right, key, value); // if key is greater than, go to the right
        } else {
            root.value = value; // keys are equal, replace node's value
        }
        return root;
    }

    @Override
    public V get(K key) {
        return getHelper(root, key);
    }

    private V getHelper(BSTNode root, K key) {
        if (root.key == null) {
            return null;
        }
        if (root.key.equals(key)) {
            return root.value;
        }
        if (key.compareTo(root.key) < 0) {
            return getHelper(root.left, key);
        } else if (key.compareTo(root.key) > 0) {
            return getHelper(root.right, key);
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return containsHelper(root, key);
    }

    private boolean containsHelper(BSTNode root, K key) {
        if (root.key == null) {
            return false;
        }
        if (root.key.equals(key)) {
            return true;
        }
        if (key.compareTo(root.key) < 0) {
            return containsHelper(root.left, key);
        } else if (key.compareTo(root.key) > 0) {
            return containsHelper(root.right, key);
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        root = new BSTNode(null, null, null, null);
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
