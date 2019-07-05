package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class MapGenV5 {
    private static final double TILE_RATIO = 0.5;
    private TETile[][] world;
    private int width;
    private int height;
    private long seed;
    private Random random;
    private HashMap<Point, Room> pointToRoom;
    private HashSet<Point> roomPoints;
    private HashSet<Point> roomCenterPts;
    private ArrayDeque<Room> rooms;
    private Point avatarPt;
    private boolean lockedDoor;
    private int floorCount;
    private int average;
    private int numRooms;

    public MapGenV5(int width, int height, Integer seed) {
        this.width = width;
        this.height = height;
        this.seed = seed;
        random = new Random(seed);
        world = new TETile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        floorCount = 0;
        roomPoints = new HashSet<>();
        average = (width + height) / 2;
        numRooms = 0;
        roomCenterPts = new HashSet<>();
        rooms = new ArrayDeque<>();
        pointToRoom = new HashMap<>();
        lockedDoor = false;
        createRooms();
    }

    public static void main(String[] args) {
        int x = (int) 1232433L;

        MapGenV5 map = new MapGenV5(80, 30, x);
        //map.generateHaws();
        //map.switcheroo();
        TERenderer renderer = new TERenderer();
        renderer.initialize(80, 30);
        renderer.renderFrame(map.world);
    }

    private void setLockedDoor(Room room) {
        int randX = 1 + random.nextInt(room.xSize() - 1) + room.x();
        int randY = 1 + random.nextInt(room.ySize() - 1) + room.y();
        if (world[randX][randY].equals(Tileset.WALL)) {
            if (world[randX + 1][randY].equals(Tileset.FLOOR)
                    && !world[randX - 1][randY].equals(Tileset.FLOOR)
                    && !world[randX][randY + 1].equals(Tileset.FLOOR)
                    && !world[randX][randY - 1].equals(Tileset.FLOOR)) {
                world[randX][randY] = Tileset.LOCKED_DOOR;
            } else if (!world[randX + 1][randY].equals(Tileset.FLOOR)
                    && world[randX - 1][randY].equals(Tileset.FLOOR)
                    && !world[randX][randY + 1].equals(Tileset.FLOOR)
                    && !world[randX][randY - 1].equals(Tileset.FLOOR)) {
                world[randX][randY] = Tileset.LOCKED_DOOR;
            } else if (!world[randX + 1][randY].equals(Tileset.FLOOR)
                    && !world[randX - 1][randY].equals(Tileset.FLOOR)
                    && world[randX][randY + 1].equals(Tileset.FLOOR)
                    && !world[randX][randY - 1].equals(Tileset.FLOOR)) {
                world[randX][randY] = Tileset.LOCKED_DOOR;
            } else if (!world[randX + 1][randY].equals(Tileset.FLOOR)
                    && !world[randX - 1][randY].equals(Tileset.FLOOR)
                    && !world[randX][randY + 1].equals(Tileset.FLOOR)
                    && world[randX][randY - 1].equals(Tileset.FLOOR)) {
                world[randX][randY] = Tileset.LOCKED_DOOR;
            } else {
                setLockedDoor(room);
            }
        } else {
            setLockedDoor(room);
        }
    }

    public Point getAvatarPoint() {
        return this.avatarPt;
    }

    public TETile[][] getWorld() {
        return this.world;
    }

    private void createRooms() {
        while (floorCount < TILE_RATIO * width * height) {
            rooms.add(generateRoom());
            numRooms++;
        }

        Room[] roomsArr = new Room[numRooms];
        for (int i = 0; i < numRooms; i++) {
            roomsArr[i] = rooms.remove();
        }
        QuickSort.sort(roomsArr, 0, numRooms - 1);
        Collections.addAll(rooms, roomsArr);
        /*
        for (int i = 0; i < numRooms; i++) {
            rooms.add(roomsArr[i]);
        }*/
        int origSize = rooms.size();
        while (rooms.size() > 1) {
            Room current = rooms.remove();
            List<Point> tempPts = new ArrayList<>();
            for (Point r : roomCenterPts) {
                if (!r.equals(current.point())) {
                    tempPts.add(r);
                }
            }
            KDTree kd = new KDTree(tempPts);
            double x = current.point().getX();
            double y = current.point().getY();
            Room closest = pointToRoom.get(kd.nearest(x, y));

            hallMaker(current, closest);
            //link 2 rooms together
            //roomsLeft.union(current.id(), closest.id());

            roomCenterPts.remove(current.point());
            if (rooms.size() == origSize / 2) {
                avatarPt = current.point();
                world[(int) avatarPt.getX()][(int) avatarPt.getY()] = Tileset.AVATAR;
                setLockedDoor(current);
            }
            //Find nearest room
            //Connect function
            //Remove room from queue & List
            //Union rooms in WQU
        }
    }

    private Room generateRoom() {
        int[] randomVals = randomXYVals();
        while (!checkEdges(randomVals)) {
            randomVals = randomXYVals();
        }
        for (int i = randomVals[0]; i < (randomVals[0] + randomVals[2]); i++) {
            for (int j = randomVals[1]; j < (randomVals[1] + randomVals[3]); j++) {
                if (i == randomVals[0] || j == randomVals[1] || i == randomVals[0]
                        + randomVals[2] - 1 || j == randomVals[1] + randomVals[3] - 1) {
                    //System.out.println(i + ", " + j);
                    world[i][j] = Tileset.WALL;
                } else {
                    world[i][j] = Tileset.FLOOR;
                }
                Point p = new Point(i, j);
                roomPoints.add(p);
            }
        }
        int[] centers = centerPoint(randomVals[0], randomVals[1], randomVals[2], randomVals[3]);
        Point newPt = new Point(centers[0], centers[1]);
        roomCenterPts.add(newPt);

        //int length = 4 + random.nextInt((average/5) - 4);
        //generateHaw(randomVals[0] + randomVals[2] - 1, length, randomVals[1] + randomVals[3] - 1);

        floorCount += randomVals[2] * randomVals[3];
        int x = randomVals[0];
        int y = randomVals[1];
        int xSize = randomVals[2];
        int ySize = randomVals[3];
        Room thisRoom = new Room(x, y, xSize, ySize, newPt, numRooms);
        pointToRoom.put(newPt, thisRoom);
        return thisRoom;
    }


    private void hallMaker(Room r1, Room r2) {
        List<Integer> matchingCoords = new ArrayList<>();
        for (Integer i : r1.xRange()) {
            if (r2.xRange().contains(i)) {
                matchingCoords.add(i);
            }
        }

        if (matchingCoords.size() > 0) {
            int rand = random.nextInt(matchingCoords.size());
            int yFloor = matchingCoords.get(rand);
            int yDiff = (int) (r1.point().getY() - r2.point().getY());
            if (yDiff > 0) {
                for (int y = r1.y(); y >= r2.bottomY(); y--) {
                    if (!world[yFloor - 1][y].equals(Tileset.FLOOR)) {
                        world[yFloor - 1][y] = Tileset.WALL;
                    }
                    world[yFloor][y] = Tileset.FLOOR;
                    if (!world[yFloor + 1][y].equals(Tileset.FLOOR)) {
                        world[yFloor + 1][y] = Tileset.WALL;
                    }
                }
                //build up along x = yFloor
            } else {
                for (int y = r1.bottomY(); y <= r2.y(); y++) {
                    if (!world[yFloor - 1][y].equals(Tileset.FLOOR)) {
                        world[yFloor - 1][y] = Tileset.WALL;
                    }
                    world[yFloor][y] = Tileset.FLOOR;
                    if (!world[yFloor + 1][y].equals(Tileset.FLOOR)) {
                        world[yFloor + 1][y] = Tileset.WALL;
                    }
                }
                //build down ''
            }
            return;
            //yTilemaker
        }
        //break;
        for (Integer i : r1.yRange()) {
            if (r2.yRange().contains(i)) {
                matchingCoords.add(i);
            }
        }
        if (matchingCoords.size() > 0) {
            int rand = random.nextInt(matchingCoords.size());
            int xFloor = matchingCoords.get(rand);
            int xDiff = (int) (r1.point().getX() - r2.point().getX());
            if (xDiff > 0) {
                for (int x = r1.x(); x >= r2.rightX(); x--) {
                    if (!world[x][xFloor - 1].equals(Tileset.FLOOR)) {
                        world[x][xFloor - 1] = Tileset.WALL;
                    }
                    world[x][xFloor] = Tileset.FLOOR;
                    if (!world[x][xFloor + 1].equals(Tileset.FLOOR)) {
                        world[x][xFloor + 1] = Tileset.WALL;
                    }
                }
            } else {
                for (int x = r1.rightX(); x <= r2.x(); x++) {
                    if (!world[x][xFloor - 1].equals(Tileset.FLOOR)) {
                        world[x][xFloor - 1] = Tileset.WALL;
                    }
                    world[x][xFloor] = Tileset.FLOOR;
                    if (!world[x][xFloor + 1].equals(Tileset.FLOOR)) {
                        world[x][xFloor + 1] = Tileset.WALL;
                    }
                }
            }
            return;
        }
        int centerX = (int) r1.point().getX();
        int centerY = (int) r2.point().getY();
        drawOneByOne(centerX, centerY);
        Point center = new Point(centerX, centerY);
        Room oneByOne = new Room(centerX - 1, centerY - 1, 3, 3, center, 100);
        hallMaker(r1, oneByOne);
        hallMaker(r2, oneByOne);
    }

    private void drawOneByOne(int centerX, int centerY) {
        if (!world[centerX - 1][centerY - 1].equals(Tileset.FLOOR)) {
            world[centerX - 1][centerY - 1] = Tileset.WALL;
        }
        if (!world[centerX - 1][centerY].equals(Tileset.FLOOR)) {
            world[centerX - 1][centerY] = Tileset.WALL;
        }
        if (!world[centerX][centerY - 1].equals(Tileset.FLOOR)) {
            world[centerX][centerY - 1] = Tileset.WALL;
        }
        if (!world[centerX - 1][centerY + 1].equals(Tileset.FLOOR)) {
            world[centerX - 1][centerY + 1] = Tileset.WALL;
        }
        if (!world[centerX + 1][centerY - 1].equals(Tileset.FLOOR)) {
            world[centerX + 1][centerY - 1] = Tileset.WALL;
        }
        if (!world[centerX][centerY + 1].equals(Tileset.FLOOR)) {
            world[centerX][centerY + 1] = Tileset.WALL;
        }
        if (!world[centerX + 1][centerY].equals(Tileset.FLOOR)) {
            world[centerX + 1][centerY] = Tileset.WALL;
        }
        if (!world[centerX + 1][centerY + 1].equals(Tileset.FLOOR)) {
            world[centerX + 1][centerY + 1] = Tileset.WALL;
        }
        world[centerX][centerY] = Tileset.FLOOR;
    }


    private int[] centerPoint(int x, int y, int xSize, int ySize) {
        int xCenter = x + (xSize / 2);
        int yCenter = y + (ySize / 2);
        return new int[]{xCenter, yCenter};
    }


    private int[] randomXYVals() {
        //returns random x, y, xSize, ySize values respectively
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xSize = 4 + random.nextInt((average / 5) - 4);
        int ySize = 4 + random.nextInt((average / 5) - 4);
        if (x + xSize >= width || y + ySize >= height) {
            return randomXYVals();
        }
        return new int[]{x, y, xSize, ySize};
    }

    private boolean checkEdges(int[] randomVals) {
        List<Point> points = new ArrayList<>();
        for (int i = randomVals[0]; i < (randomVals[0] + randomVals[2]); i++) {
            for (int j = randomVals[1]; j < (randomVals[1] + randomVals[3]); j++) {
                points.add(new Point(i, j));
            }
        }
        for (Point p : points) {
            if (roomPoints.contains(p)) {
                return false;
            }
        }
        return true;
    }


    private void switcheroo() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == 0 || j == 0 || i == width - 1 || j == height - 1) {
                    world[i][j] = Tileset.WALL;
                }
                if (world[i][j].equals(Tileset.FLOOR)) {
                    world[i][j] = Tileset.NOTHING;
                } else if (world[i][j].equals(Tileset.NOTHING)) {
                    world[i][j] = Tileset.FLOOR;
                }
            }
        }
    }
}

