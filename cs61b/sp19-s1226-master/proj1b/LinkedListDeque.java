public class LinkedListDeque<T> implements Deque<T> {
    private class Node {
        private T item;
        private Node next;
        private Node prev;

        Node(T i, Node n, Node p) {
            item = i;
            next = n;
            prev = p;
        }
    }
    private int size;
    private Node sentinel;

    @Override
    public int size() {
        return size;
    }
    @Override
    public boolean isEmpty() {
        return (size == 0);
    }
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }
    @Override
    public void addFirst(T item) {
        Node shifted = sentinel.next;
        sentinel.next = new Node(item, sentinel.next, sentinel);
        shifted.prev = sentinel.next;
        size++;
    }
    @Override
    public void addLast(T item) {
        Node shifted = sentinel.prev;
        sentinel.prev = new Node(item, sentinel, sentinel.prev);
        shifted.next = sentinel.prev;
        size++;
    }
    @Override
    public void printDeque() {
        Node iterator = sentinel.next;
        while (iterator != sentinel) {
            System.out.print(iterator.item + " ");
            iterator = iterator.next;
        }
    }
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T result = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size--;
        return result;
    }
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T result = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size--;
        return result;
    }
    @Override
    public T get(int index) {
        if (isEmpty()) {
            return null;
        }
        Node iterator = sentinel.next;
        int i = 0;
        while (i != index) {
            iterator = iterator.next;
            i++;
        }
        return iterator.item;
    }
    public T getRecursive(int index) {
        return getRecursiveHelper(index, this.sentinel.next);
    }
    private T getRecursiveHelper(int index, Node recursor) {
        if (index == 0) {
            return recursor.item;
        } else {
            return getRecursiveHelper(index - 1, recursor.next);
        }
    }
    public LinkedListDeque(LinkedListDeque other) {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;

        for (int i = 0; i < other.size(); i += 1) {
            addLast((T) other.get(i));
        }
    }
}
