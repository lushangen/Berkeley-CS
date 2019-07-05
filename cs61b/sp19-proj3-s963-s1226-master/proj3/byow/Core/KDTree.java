package byow.Core;

import java.util.Comparator;
import java.util.List;

public class KDTree {
    private List<Point> points;
    private Tree root;

    public KDTree(List<Point> points) {
        this.points = points;
        createTree();
    }

    private void createTree() {
        root = new Tree(points.get(0), 0);
        for (int i = 1; i < points.size(); i++) {
            addNode(root, points.get(i));
        }
    }

    private void addNode(Tree t, Point p) {
        if (t == null) {
            return;
        }
        if (t.point.equals(p)) {
            return;
        }
        Point currentPoint = t.point;
        if (t.comparator.compare(p, currentPoint) < 0) {
            if (t.left == null) {
                t.left = new Tree(p, t.depth + 1);
                return;
            }
            addNode(t.left, p);
        } else {
            if (t.right == null) {
                t.right = new Tree(p, t.depth + 1);
                return;
            }
            addNode(t.right, p);
        }
        /*
        if (t.comparator.compare(p, currentPoint) < 0) {
            if (Double.compare(p.getX(), currentPoint.getX()) < 0) {
                if (t.left == null) {
                    t.left = new Tree(p, t.depth+1);
                    return;
                }
                addNode(t.left, p);
            }
            else {
                if (t.right == null) {
                    t.right = new Tree(p, t.depth+1);
                    return;
                }
                addNode(t.right, p);
            }
        } else {
            if (Double.compare(p.getY(), currentPoint.getY()) < 0) {
                if (t.left == null) {
                    t.left = new Tree(p, t.depth+1);
                    return;
                }
                addNode(t.left, p);
            }
            else {
                if (t.right == null) {
                    t.right = new Tree(p, t.depth+1);
                    return;
                }
                addNode(t.right, p);
            }
        }
        */
    }

    public Point nearest(double x, double y) {
        return getNearest(root, new Point(x, y), root).point;
    }

    //@Source: Kd-Tree Lecture slide psuedocode
    private Tree getNearest(Tree t, Point goal, Tree best) {
        if (t == null) {
            return best;
        }
        if (Point.distance(goal, t.point) < Point.distance(goal, best.point)) {
            best = t;
        }
        Tree goodSide;
        Tree badSide;
        if (t.comparator.compare(goal, t.point) < 0) {
            goodSide = t.left;
            badSide = t.right;
        } else {
            goodSide = t.right;
            badSide = t.left;
        }
        best = getNearest(goodSide, goal, best);
        Point nextBest;
        if (t.x) {
            nextBest = new Point(t.point.getX(), goal.getY());
        } else {
            nextBest = new Point(goal.getX(), t.point.getY());
        }
        if (Point.distance(nextBest, goal) < Point.distance(best.point, goal)) {
            best = getNearest(badSide, goal, best);
        }
        return best;
    }

    private class Tree {
        private Tree left;
        private Tree right;
        private Point point;
        private int depth;
        private boolean x;
        private Comparator<Point> comparator;

        //@Source: intellJ hints for comparator.comparing to shorten code without using lambda
        private Tree(Point p, int level) {
            depth = level;
            point = p;
            left = null;
            right = null;
            if (level % 2 == 0) {
                comparator = Comparator.comparing(Point::getX);
                x = true;
            } else {
                comparator = Comparator.comparing(Point::getY);
                x = false;
            }
        }
    }
}
