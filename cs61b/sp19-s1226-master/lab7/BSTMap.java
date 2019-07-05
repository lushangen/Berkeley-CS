import java.util.Iterator;
import java.util.Set;
public class BSTMap <K extends Comparable<K>, V> implements Map61B<K, V>  {
    private Node root;
    private int size;
    private class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    public BSTMap() {
    }
    @Override
    public void clear() {
        root = null;
        size = 0;
    }
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }
    private V getHelper(K key, Node n) {
        if (n == null || size == 0) {
            return null;
        }
        if (key.compareTo(n.key) < 0) {
            return getHelper(key, n.left);
        } else if (key.compareTo(n.key) > 0) {
            return getHelper(key, n.right);
        } else {
            return n.value;
        }
    }
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public void put(K key, V value) {
        size++;
        root = putHelper(key, value, root);
    }
    private Node putHelper(K key, V value, Node n) {
        if (n == null) {
            return new Node(key, value);
        }
        if (key.compareTo(n.key) < 0) {
            putHelper(key, value, n.left);
        } else if (key.compareTo(n.key) > 0) {
            putHelper(key, value, n.right);
        } else {
            n.value = value;
        }
        return n;
    }
    public void printInOrder(Node n) {
        if (n == null) {
            return;
        }
        if (n.left != null) {
            printInOrder(n.left);
        }
        System.out.print(n.key);
        if (n.right != null) {
            printInOrder(n.right);
        }
    }
    public V remove(K k) {
        return null;
    }
    public V remove(K k, V v) {
        return null;
    }
    public Set<K> keySet() {
        return null;
    }
    public Iterator<K> iterator() {
        return null;
    }
}
