public interface Deque<T> {
    default  boolean isEmpty() {
        return (size() == 0);
    }
    int size();
    void addFirst(T item);
    void addLast(T item);
    void printDeque();
    T removeFirst();
    T removeLast();
    T get(int index);
}
