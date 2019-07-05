package byow.Core;

import java.util.HashSet;


public class Room {
    private int bottomY;
    private int rightX;
    private int x;
    private int y;
    private int xSize;
    private int ySize;
    private Point centerPt;
    private int id;
    private HashSet<Integer> xRange;
    private HashSet<Integer> yRange;


    public Room(int x, int y, int xSize, int ySize, Point p, int id) {
        this.x = x;
        this.y = y;
        this.xSize = xSize;
        this.ySize = ySize;
        centerPt = p;
        this.id = id;
        xRange = new HashSet<>();
        yRange = new HashSet<>();
        for (int i = x + 1; i < x + xSize - 1; i++) {
            xRange.add(i);
        }
        for (int i = y + 1; i < y + ySize - 1; i++) {
            yRange.add(i);
        }
        bottomY = y + ySize - 1;
        rightX = x + xSize - 1;
    }

    public Room(int id) {
        this.id = id;
    }

    public HashSet<Integer> xRange() {
        return xRange;
    }

    public HashSet<Integer> yRange() {
        return yRange;
    }

    public int xSize() {
        return xSize;
    }

    public int ySize() {
        return ySize;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int id() {
        return id;
    }

    public int bottomY() {
        return bottomY;
    }

    public int rightX() {
        return rightX;
    }

    public Point point() {
        return centerPt;
    }
}
