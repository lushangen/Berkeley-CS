public class ArrayDeque<T> {

    private T[] items;
    private int size;
    private int first;
    private int last;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        first = 5;
        last = 4;
        size = 0;
    }
    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return (size == 0);
    }
    public void addFirst(T item) {
        if (size >= items.length) {
            resize(items.length * 2);
        }
        if (first == 0) {
            first = items.length - 1;
            items[first] = item;
        } else {
            first = first - 1;
            items[first] = item;
        }
        size++;
    }
    public void addLast(T item) {
        if (size >= items.length) {
            resize(items.length * 2);
        }
        if (last == (items.length - 1)) {
            last = 0;
            items[last] = item;
        } else {
            last = last + 1;
            items[last] = item;
        }
        size++;
    }

    public void printDeque() {
        if (first == last) {
            System.out.print(items[first]);
        } else if (first < last) {
            int index = first;
            while (index <= last) {
                System.out.print(items[index] + " ");
                index++;
            }
        } else {
            int f = first;
            while (f <= items.length - 1) {
                System.out.print(items[f] + " ");
                f++;
            }
            int l = 0;
            while (l <= last) {
                System.out.print(items[l] + " ");
                l++;
            }
        }
    }
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T result = items[first];
        items[first] = null;
        if (first == items.length - 1) {
            first = 0;
        } else {
            first++;
        }
        size--;
        if (((items.length / 4) > size) && (items.length > 16)) {
            resize(items.length / 2);
        }
        return result;
    }
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T result = items[last];
        items[last] = null;
        if (last == 0) {
            last = items.length - 1;
        } else {
            last--;
        }
        size--;
        if (((items.length / 4) > size) && (items.length > 16)) {
            resize(items.length / 2);
        }
        return result;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int t = (index + first + items.length) % items.length;
        return items[t];
    }
    public ArrayDeque(ArrayDeque other) {
        items = (T[]) new Object[8];
        first = 5;
        last = 4;
        size = 0;

        for (int i = 0; i < other.size(); i += 1) {
            addLast((T) other.get(i));
        }
    }
    private void resize(int capacity) {
        T[] re = (T[]) new Object[capacity];
        int counter = 0;
        for (int i = first; i <= items.length - 1; i++) {
            if (items[i] != null) {
                re[counter] = items[i];
                counter++;
            }
        }
        for (int i = 0; i < first; i++) {
            if (items[i] != null) {
                re[counter] = items[i];
                counter++;
            }
        }
        first = 0;
        last = size - 1;
        items = re;
    }
}
