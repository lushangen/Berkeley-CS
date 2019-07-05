package bearmaps.proj2ab;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<Node> heap;
    private HashMap<T, Integer> items;
    public ArrayHeapMinPQ() {
        this.heap = new ArrayList<>();
        heap.add(null);
        items = new HashMap<>();
    }
    private int swim(int index) {
        if ((index != 1 && heap.get(index).compareTo(heap.get(parent(index))) < 0)) {
            items.replace(heap.get(index).getItem(), parent(index));
            items.replace(heap.get(parent(index)).getItem(), index);
            Collections.swap(heap, index, parent(index));
            return swim(parent(index));
        } else {
            return index;
        }
    }
    private int sink(int index) {
        int leftChild = leftChild(index);
        int rightChild = rightChild(index);
        int small;
        if (leftChild <= heap.size() - 1 && heap.get(leftChild).compareTo(heap.get(index)) < 0) {
            small = leftChild;
        } else {
            small = index;
        }
        if (rightChild <= heap.size() - 1 && heap.get(rightChild).compareTo(heap.get(small)) < 0) {
            small = rightChild;
        }
        if (small != index) {
            items.replace(heap.get(index).getItem(), small);
            items.replace(heap.get(small).getItem(), index);
            Collections.swap(heap, index, small);
            return sink(small);
        } else {
            return index;
        }
    }
    private int parent(int index) {
        return index / 2;
    }
    private int leftChild(int index) {
        return index * 2;
    }
    private int rightChild(int index) {
        return index * 2 + 1;
    }
    @Override
    public void add(T item, double priority) {
        if (items.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        heap.add(new Node(item, priority));
        int newIndex = swim(heap.size() - 1);
        items.put(item, newIndex);
    }

    @Override
    public boolean contains(T item) {
        return items.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (size() <= 0) {
            throw new NoSuchElementException();
        }
        return heap.get(1).item;
    }

    @Override
    public T removeSmallest() {
        if (size() <= 0) {
            throw new NoSuchElementException();
        }
        T rv = heap.get(1).item;
        heap.set(1, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        int newIndex = sink(1);
        items.remove(rv, newIndex);
        return rv;
    }

    @Override
    public int size() {
        return heap.size() - 1;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!items.containsKey(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        int index = items.get(item);
        heap.get(index).setPriority(priority);
        swim(index);
        sink(index);
    }
    private class Node implements Comparable<Node> {
        private T item;
        private double priority;
        Node(T e, double p) {
            this.item = e;
            this.priority = p;
        }
        T getItem() {
            return item;
        }
        double getPriority() {
            return priority;
        }
        void setPriority(double priority) {
            this.priority = priority;
        }
        @Override
        public int compareTo(Node other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }
        @Override
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((Node) o).getItem().equals(getItem());
            }
        }
        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }
}
