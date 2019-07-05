public class ArrayDequeTest {
    public static void main (String[]args) {
        testAdd();
    }
    public static void testAdd() {
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<Integer>();
        arrayDeque.addFirst(0);
        arrayDeque.addFirst(0);
        arrayDeque.addFirst(0);
        arrayDeque.addFirst(0);
        arrayDeque.addFirst(0);
        arrayDeque.addFirst(0);
        arrayDeque.addFirst(0);
        arrayDeque.addFirst(0);
        arrayDeque.addFirst(0);
        arrayDeque.addFirst(0);
        arrayDeque.removeFirst();
        arrayDeque.removeFirst();
        arrayDeque.removeFirst();
        arrayDeque.removeFirst();
        arrayDeque.removeFirst();
        arrayDeque.removeFirst();
        arrayDeque.removeFirst();
        arrayDeque.removeFirst();
        arrayDeque.removeFirst();
        int x = arrayDeque.removeFirst();
        Object y = arrayDeque.removeFirst();
        arrayDeque.removeFirst();
        arrayDeque.removeFirst();
        arrayDeque.addFirst(0);
        arrayDeque.addFirst(0);
        arrayDeque.addFirst(0);
        arrayDeque.addFirst(0);
        arrayDeque.addFirst(0);
        arrayDeque.addFirst(0);
        arrayDeque.addFirst(0);
        arrayDeque.addFirst(0);
    }
}
