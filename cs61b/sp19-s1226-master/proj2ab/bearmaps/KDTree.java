package bearmaps;

import java.util.List;

public class KDTree implements PointSet {
    private static final boolean HORIZONTAL = false;
    private static final boolean VERTICAL = true;
    private Node root;

    private class Node {
        private Point p;
        private boolean orientation;
        private Node left; //down child
        private Node right; //up child
        Node(Point p, boolean orientation) {
            this.p = p;
            this.orientation = orientation;
        }
    }
    public KDTree(List<Point> points) {
        for (Point p: points) {
            root = add(p, root, HORIZONTAL);
        }
    }
    private Node add(Point p, Node n, boolean orientation) {
        if (n == null) {
            return new Node(p, orientation);
        }
        if (p.equals(n.p)) {
            return n;
        }
        int compare = comparePoints(p, n.p, orientation);
        if (compare < 0) {
            n.left = add(p, n.left, !orientation);
        } else if (compare >= 0) {
            n.right = add(p, n.right, !orientation);
        }
        return n;
    }
    private int comparePoints(Point p, Point q, boolean orientation) {
        if (orientation == HORIZONTAL) {
            return Double.compare(p.getX(), q.getX());
        }
        return Double.compare(p.getY(), q.getY());
    }
    @Override
    public Point nearest(double x, double y) {
        Point p = new Point(x, y);
        return nearestHelper(root, p, root).p;
    }
    private Node nearestHelper(Node n, Point goal, Node best) {
        if (n == null) {
            return best;
        }
        double nDistance = Point.distance(n.p, goal);
        double bestDistance = Point.distance(best.p, goal);
        if (nDistance < bestDistance) {
            best = n;
        }
        Node goodSide = null;
        Node badSide = null;
        int compare = comparePoints(goal, n.p, n.orientation);
        if (compare < 0) {
            goodSide = n.left;
            badSide = n.right;
        } else if (compare >= 0) {
            goodSide = n.right;
            badSide = n.left;
        }
        best = nearestHelper(goodSide, goal, best);
        if (n.orientation == HORIZONTAL) {
            if (Math.pow(goal.getX() - n.p.getX(), 2) < bestDistance) {
                best = nearestHelper(badSide, goal, best);
            }
        } else {
            if (Math.pow(goal.getY() - n.p.getY(), 2) < bestDistance) {
                best = nearestHelper(badSide, goal, best);
            }
        }
        return best;
    }
}
