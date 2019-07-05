package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ArrayHeapMinPQTest {
    @Test
    public void testRemoveSmallest() {
        ArrayHeapMinPQ<Integer> tester = new ArrayHeapMinPQ<>();
        for (int i = 1; i <= 10; i++) {
            tester.add(i, i);
        }
        for (int i = 1; i <= 10; i++) {
            int x = tester.removeSmallest();
            assertEquals(x, i);
        }
    }
    @Test
    public void testRemoveSmallestTwo() {
        ArrayHeapMinPQ<Integer> tester = new ArrayHeapMinPQ<>();
        tester.add(1, 1);
        //tester.add(2, 2);
        int x = tester.removeSmallest();
        //int y = tester.removeSmallest();
        //assertEquals(x, 1);
        //assertEquals(y, 2);
    }
    @Test
    public void testGetSmallest() {
        ArrayHeapMinPQ<Integer> tester = new ArrayHeapMinPQ<>();
        for (int i = 1; i <= 10; i++) {
            tester.add(i, i);
        }
        for (int i = 1; i <= 10; i++) {
            int x = tester.getSmallest();
            int y = tester.removeSmallest();
            assertEquals(x, y);
            assertEquals(x, i);
            assertEquals(y, i);
        }
    }
    @Test
    public void testContains() {
        ArrayHeapMinPQ<Integer> tester = new ArrayHeapMinPQ<>();
        for (int i = 1; i <= 10; i++) {
            tester.add(i, i);
        }
        for (int i = 1; i <= 10; i++) {
            assertEquals(tester.contains(i), true);
        }
    }
    @Test
    public void size() {
        ArrayHeapMinPQ<Integer> tester = new ArrayHeapMinPQ<>();
        for (int i = 1; i <= 10; i++) {
            tester.add(i, i);
        }
        assertEquals(tester.size(), 10);
    }
    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ<Integer> tester = new ArrayHeapMinPQ<>();
        for (int i = 1; i <= 10; i++) {
            tester.add(i, i);
        }
        tester.changePriority(10, 1);
        int x = tester.getSmallest();
        assertEquals(x, 1);
    }
    @Test
    public void timeTestAdd() {
        ArrayHeapMinPQ<Integer> tester = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> naiveTester = new NaiveMinPQ<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            tester.add(i, i);
        }
        long end = System.currentTimeMillis();
        long start2 = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");
        for (int i = 0; i < 10000000; i++) {
            naiveTester.add(i, i);
        }
        long end2 = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end2 - start2) / 1000.0 +  " seconds.");
    }
    @Test
    public void timeTestRemoveSmallest() {
        ArrayHeapMinPQ<Integer> tester = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> naiveTester = new NaiveMinPQ<>();
        for (int i = 0; i < 1000000; i++) {
            tester.add(i, i);
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            int x = tester.getSmallest();
            int y = tester.removeSmallest();
            assertEquals(x, y);
            assertEquals(x, i);
            assertEquals(y, i);
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");
        for (int i = 0; i < 1000000; i++) {
            naiveTester.add(i, i);
        }
        long start2 = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            int x = naiveTester.getSmallest();
            int y = naiveTester.removeSmallest();
            assertEquals(x, y);
            assertEquals(x, i);
            assertEquals(y, i);
        }
        long end2 = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end2 - start2) / 1000.0 +  " seconds.");
    }
    @Test
    public void timeTestChangePriority() {
        ArrayHeapMinPQ<Integer> tester = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> tester2 = new NaiveMinPQ<>();
        for (int i = 0; i < 10000; i++) {
            tester.add(i, i);
            tester2.add(i, i);
        }
        long start = System.currentTimeMillis();
        for (int i = 10000 - 1; i >= 0; i--) {
            tester.changePriority(i, tester.getSmallest());
            tester2.changePriority(i, tester2.getSmallest());
            int x = tester.getSmallest();
            int y = tester2.getSmallest();
            assertEquals(x, y);
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");
    }
    @Test
    public void timeTestTwoChangePriority() {
        ArrayHeapMinPQ<Integer> tester = new ArrayHeapMinPQ<>();
        int[] items = new int[10000000];
        for (int i = 0; i < 10000000; i++) {
            tester.add(i, i);
            items[i] = i;
        }
        long start = System.currentTimeMillis();
        int priority = 0;
        for (int i = 10000000 - 1; i > 0; i--) {
            tester.changePriority(items[i], priority);
            priority++;
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");
    }
}
