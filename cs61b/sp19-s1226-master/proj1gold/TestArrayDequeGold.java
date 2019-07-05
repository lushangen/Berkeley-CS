import static org.junit.Assert.*;
import org.junit.Test;
public class TestArrayDequeGold {
    @Test
    public void testADeque() {
        StudentArrayDeque<Integer> student = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> sln = new ArrayDequeSolution<>();
        String message = "";

        for (int i = 0; i <= 200; i++){
            int x = StdRandom.uniform(0, 9);
            int y = StdRandom.uniform(0,2);
            if (x%2 == 0) {
                student.addFirst(x);
                sln.addFirst(x);
                message = message + "addFirst(" + x + ")\n";
                assertEquals(message, sln.get(0), student.get(0));
            } else {
                student.addLast(x);
                sln.addLast(x);
                message = message + "addLast(" + x + ")\n";
                assertEquals(message, sln.get(sln.size() - 1), student.get(student.size() - 1));
            }
            if (sln.size() > 0 && y == 1) {
                int random = StdRandom.uniform(0, 2);
                if (random == 1) {
                    message = message + "removeFirst()\n";
                    assertEquals(message, sln.removeFirst(), student.removeFirst());
                } else {
                    message = message + "removeLast()\n";
                    assertEquals(message, sln.removeLast(), student.removeLast());
                }
            }
        }
    }
}
