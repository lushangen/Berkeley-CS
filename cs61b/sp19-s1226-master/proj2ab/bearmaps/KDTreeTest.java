package bearmaps;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;



public class KDTreeTest {
    /**@source video walk-through for randomization */
    private static Random r = new Random(2);
    @Test
    public  void kdTreeExample() {
        Point p1 = new Point(2, 3); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);
        Point p8 = new Point(2, 7);

        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7, p8));
        Point p = kd.nearest(0, 7);
        System.out.println(p.getX());
        System.out.println(p.getY());
    }
    /*@Test
    public void testNearestDemoSlides() {
        KDTree kd = kdTreeExample();
        Point actual = kd.nearest(0, 7);
        Point expected = new Point(1, 5);
        assertEquals(expected, actual);
    }*/
    private Point randomPoint() {
        double x = r.nextDouble();
        double y = r.nextDouble();
        return new Point(x, y);
    }
    private List<Point> randomPoints(int N) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            points.add(randomPoint());
        }
        return points;
    }
    @Test
    public void test100kPoints() {
        List<Point> points = randomPoints(100000);
        NaivePointSet naive = new NaivePointSet(points);
        KDTree kd = new KDTree(points);

        List<Point> queries = randomPoints(10000);
        long start = System.currentTimeMillis();
        for (Point p: queries) {
            Point expected = naive.nearest(p.getX(), p.getY());
            Point actual = kd.nearest(p.getX(), p.getY());
            assertEquals(expected, actual);
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");
    }
    @Test
    public void timeKDTree() {
        List<Point> points = randomPoints(5000000);
        KDTree kd = new KDTree(points);

        List<Point> queries = randomPoints(200000);
        long start = System.currentTimeMillis();
        for (Point p: queries) {
            Point actual = kd.nearest(p.getX(), p.getY());
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");
    }
    @Test
    public void timeNaive() {
        List<Point> points = randomPoints(100000);
        NaivePointSet naive = new NaivePointSet(points);

        List<Point> queries = randomPoints(10000);
        long start = System.currentTimeMillis();
        for (Point p: queries) {
            Point actual = naive.nearest(p.getX(), p.getY());
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");
    }
    @Test
    public void timeBoth() {
        for (int i = 0; i < 10; i++) {
            List<Point> points = randomPoints(100000);
            KDTree kd = new KDTree(points);
            NaivePointSet naive = new NaivePointSet(points);
            List<Point> queries = randomPoints(10000);

            long start = System.currentTimeMillis();
            for (Point p : queries) {
                Point actual = kd.nearest(p.getX(), p.getY());
            }
            long end = System.currentTimeMillis();
            System.out.println("Total time elapsed: " + (end - start) / 1000.0 + " seconds.");

            long start2 = System.currentTimeMillis();
            for (Point p : queries) {
                Point actual = naive.nearest(p.getX(), p.getY());
            }
            long end2 = System.currentTimeMillis();
            System.out.println("Total time elapsed: " + (end2 - start2) / 1000.0 + " seconds.");

            System.out.println("Faster by: " + ((end2 - start2) / (end - start)) + " times.");
        }
    }
}
