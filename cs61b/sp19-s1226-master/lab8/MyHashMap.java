import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;


public class MyHashMap<K, V> implements Map61B<K, V> {
    private static final int defaultSize = 16;
    private double load_factor() {
        return items / buckets.size();
    }
    private double lf;
    private ArrayList<Entry<K, V>> buckets;
    private int items = 0;
    private class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;
        Entry (K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
        public K getKey() {
            return key;
        }
        public V getvalue() {
            return value;
        }
        public V setValue(V x) {
            V old = value;
            value = x;
            return old;
        }
    }
    public MyHashMap() {
        this (defaultSize, .75);
    }
    public MyHashMap(int initialSize) {
        this (initialSize, .75);
    }
    public MyHashMap(int initialSize, double loadFactor) {
        buckets = new ArrayList<Entry<K,V>>(initialSize);
        for (int i = 0; i < initialSize; i++) {
            buckets.add(null);
        }
        lf = loadFactor;
    }
    private int hash (K key) {
        return Math.floorMod((key.hashCode() & 0x7fffffff), buckets.size());
    }

    @Override
    public void clear() {
        buckets = new ArrayList<Entry<K,V>>(defaultSize);
        for (int i = 0; i < defaultSize; i++) {
            buckets.add(null);
        }
        this.items = 0;
    }

    @Override
    public int size() {
        return items;
    }

    @Override
    public boolean containsKey(K key) {
        Entry e = find(key, buckets.get(hash(key)));
        return e != null;
    }

    @Override
    public V get(K key) {
        Entry e = find(key, buckets.get(hash(key)));
        return (V) e.getvalue();
    }
    private Entry<K, V> find (K key, Entry<K, V> buckets) {
        for (Entry<K, V> i = buckets; i != null; i = i.next) {
            if ((key == null && i.key == null) || key.equals(i.key)) {
                return i;
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        int has = hash(key);
        Entry e = find(key, buckets.get(has));
        if (e == null) {
            /*if (items > buckets.size() * load_factor()) {
                resize(buckets);
            }*/
            buckets.set(has, new Entry<K,V> (key, value, buckets.get(has)));
            items++;
        } else {
            e.setValue(value);
        }
    }
    /*private void resize(ArrayList<Entry<K,V>> bckt) {
        ArrayList<Entry<K, V>> nAList = new ArrayList<>(buckets.size() * 2);
        for (int i = 0; i < bckt.size(); i++) {
            Entry<K, V> e = bckt.get(i);
            int x = hash(e.getKey());
            nAList.set(x, new Entry<K,V> (e.getKey(), e.getvalue(), bckt.get(x)));
        }
    }*/

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (int i = 0; i < buckets.size(); i++) {
            Entry<K, V> e = buckets.get(i);
            K x = e.getKey();
            keySet.add(x);
        }
        return keySet;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }
}
