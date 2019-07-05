package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Engine {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    TERenderer ter = new TERenderer();

    public static void main(String[] args) {
        Engine e = new Engine();
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        //TETile[][] world = e.interactWithInputString("n7193300625454684331saaawasdaawdwsd");
        TETile[][] world = e.interactWithInputString("n7193300625454684331saaawasdaawd:q");
        world = e.interactWithInputString("lwsd");
        ter.renderFrame(world);
    }

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */

    public void interactWithKeyboard() {
        long seed = 0; boolean newGame = false; int saveNum = 1;
        KeyboardInputDevice keyboardInput = new KeyboardInputDevice();
        StringBuffer seedBuff = new StringBuffer(); StringBuffer instructions = new StringBuffer();
        TETile[][] worldFrame = null; Point avatarPt = null;
        while (keyboardInput.possibleNextInput()) {
            char ch = keyboardInput.getNextKey();
            if (ch == 'n' || ch == 'N') {
                instructions.append(ch);
                drawPreLoadScreen();
                ch = keyboardInput.getNextKey();
                if (ch >= '1' && ch <= '3') {
                    saveNum = Character.getNumericValue(ch);
                    newGame = true;
                    break;
                } else if (ch == 'b' || ch == 'B') {
                    interactWithKeyboard();
                    return;
                }
            } else if (ch == 'q' || ch == 'Q') {
                System.exit(0);
            } else if (ch == 'l' || ch == 'L') {
                drawLoadScreen();
                ch = keyboardInput.getNextKey();
                if (ch >= '1' && ch <= '3') {
                    StringBuffer inst = readFile("world_save" + ch + ".txt");
                    ArrayList lst = runWorld(inst.toString());
                    saveNum = Character.getNumericValue(ch);
                    worldFrame = (TETile[][]) lst.get(0);
                    avatarPt = (Point) lst.get(1);
                    instructions.append(inst);
                    break;
                } else if (ch == 'b' || ch == 'B') {
                    interactWithKeyboard();
                    return;
                }
            } else if (ch == 'r' || ch == 'R') {
                drawReplayScreen();
                ch = keyboardInput.getNextKey();
                if (ch >= '1' && ch <= '3') {
                    StringBuffer inst = readFile("world_save" + ch + ".txt");
                    replayWorld(inst.toString());
                } else if (ch == 'b' || ch == 'B') {
                    interactWithKeyboard();
                    return;
                }
            }
        }
        if (newGame) {
            StdDraw.clear(Color.BLACK);
            while (keyboardInput.possibleNextInput()) {
                drawSeedScreen();
                StdDraw.text(0.5, 0.4, seedBuff.toString());
                char ch = keyboardInput.getNextKey();
                instructions.append(ch);
                if (ch == 's' || ch == 'S') {
                    break;
                } else if (ch != '\u0000' && (ch >= '1' && ch <= '9')) {
                    seedBuff.append(ch);
                }
                StdDraw.clear(Color.BLACK);
            }
            seed = Long.parseLong(seedBuff.toString());
            MapGenV5 map = new MapGenV5(WIDTH, HEIGHT, (int) seed);
            worldFrame = map.getWorld();
            avatarPt = map.getAvatarPoint();
        }
        ter.initialize(WIDTH, HEIGHT + 5);
        double oldmX = StdDraw.mouseX();
        double oldmY = StdDraw.mouseY();
        ter.renderWithText(worldFrame, oldmX, oldmY);
        char prevChar = ' ';
        while (keyboardInput.possibleNextInput()) {
            char ch = keyOrMouse(worldFrame, oldmX, oldmY);
            prevChar = endGame(ch, prevChar, worldFrame, avatarPt,
                    instructions, saveNum);
            oldmX = StdDraw.mouseX();
            oldmY = StdDraw.mouseY();
        }
    }

    private Character endGame(Character ch, Character prevChar, TETile[][] worldFrame,
                              Point avatarPt, StringBuffer instructions, int saveNum) {
        basicMove(ch, worldFrame, avatarPt);
        if (ch == 'q' && prevChar == ':') {
            String instList = instructions.toString();
            try {
                FileWriter file = new FileWriter("world_save" + saveNum + ".txt");
                file.write(instList);
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
        if (ch != ':' && ch != 'q' && ch != '\u0000' && ch != ' ') {
            instructions.append(ch);
        }
        double oldmX = StdDraw.mouseX();
        double oldmY = StdDraw.mouseY();
        ter.renderWithText(worldFrame, oldmX, oldmY);
        return ch;
    }


    private void basicMove(Character ch, TETile[][] worldFrame, Point avatarPt) {
        if (ch == 'w') {
            move(Direction.DIR.UP, worldFrame, avatarPt);
        } else if (ch == 'a') {
            move(Direction.DIR.LEFT, worldFrame, avatarPt);
        } else if (ch == 's') {
            move(Direction.DIR.DOWN, worldFrame, avatarPt);
        } else if (ch == 'd') {
            move(Direction.DIR.RIGHT, worldFrame, avatarPt);
        }
    }


    private StringBuffer readFile(String fileName) {
        StringBuffer inst = new StringBuffer();
        try {
            FileReader reader = new FileReader(fileName);
            BufferedReader read = new BufferedReader(reader);
            String temp = "";
            while ((temp = read.readLine()) != null) {
                inst.append(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inst;
    }


    private void drawLoadScreen() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Monaco", Font.BOLD, 60);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.7, "Load Screen (which save slot to load?)");
        Font font2 = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font2);
        StdDraw.text(0.5, 0.5, "Save 1 (1)");
        StdDraw.text(0.5, 0.4, "Save 2 (2)");
        StdDraw.text(0.5, 0.3, "Save 3 (3)");
        StdDraw.text(0.5, 0.2, "Back (b)");
    }

    private void drawReplayScreen() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Monaco", Font.BOLD, 60);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.7, "Replay Screen (chose a replay)");
        Font font2 = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font2);
        StdDraw.text(0.5, 0.5, "Save 1 (1)");
        StdDraw.text(0.5, 0.4, "Save 2 (2)");
        StdDraw.text(0.5, 0.3, "Save 3 (3)");
        StdDraw.text(0.5, 0.2, "Back (b)");
    }

    private void drawSeedScreen() {
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Monaco", Font.BOLD, 60);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.7, "Enter a Seed:");
        Font font2 = new Font("Monaco", Font.ITALIC, 30);
        StdDraw.setFont(font2);
    }

    private void drawPreLoadScreen() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Monaco", Font.BOLD, 60);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.7, "Pick a Slot to Save To:");
        Font font2 = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font2);
        StdDraw.text(0.5, 0.5, "Save 1 (1)");
        StdDraw.text(0.5, 0.4, "Save 2 (2)");
        StdDraw.text(0.5, 0.3, "Save 3 (3)");
        StdDraw.text(0.5, 0.2, "Back (b)");
    }

    private Character keyOrMouse(TETile[][] worldFrame, double oldMx, double oldmY) {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                return c;
            }
            double mX = StdDraw.mouseX();
            double mY = StdDraw.mouseY();
            if (mX != oldMx || mY != oldmY) {
                return '\u0000';
            }
        }

    }

    public void replayWorld(String input) {
        long seed = 0;
        StringInputDevice inputDev = new StringInputDevice(input);
        boolean start = false;
        StringBuffer seedBuff = new StringBuffer();

        while (inputDev.possibleNextInput()) {
            char ch = inputDev.getNextKey();
            if (ch == 'n' || ch == 'N') {
                start = true;
            } else if (ch == 's' || ch == 'S') {
                break;
            } else if (start) {
                seedBuff.append(ch);
            }
        }

        if (seedBuff.length() > 0) {
            seed = Long.parseLong(seedBuff.toString());
        }
        MapGenV5 mapGen = new MapGenV5(WIDTH, HEIGHT, (int) seed);
        TETile[][] worldFrame = mapGen.getWorld();
        Point avatarPt = mapGen.getAvatarPoint();

        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(worldFrame);
        while (inputDev.possibleNextInput()) {
            char ch = inputDev.getNextKey();
            basicMove(ch, worldFrame, avatarPt);
            StdDraw.pause(50);
            ter.renderFrame(worldFrame);
        }
        StdDraw.pause(50);
    }

    public ArrayList runWorld(String input) {
        long seed = 0;
        StringInputDevice inputDev = new StringInputDevice(input);
        boolean start = false;
        StringBuffer seedBuff = new StringBuffer();

        while (inputDev.possibleNextInput()) {
            char ch = inputDev.getNextKey();
            if (ch == 'n' || ch == 'N') {
                start = true;
            } else if (ch == 's' || ch == 'S') {
                break;
            } else if (start) {
                seedBuff.append(ch);
            }
        }

        if (seedBuff.length() > 0) {
            seed = Long.parseLong(seedBuff.toString());
        }
        MapGenV5 mapGen = new MapGenV5(WIDTH, HEIGHT, (int) seed);
        TETile[][] finalWorldFrame = mapGen.getWorld();
        Point avatarPt = mapGen.getAvatarPoint();

        while (inputDev.possibleNextInput()) {
            char ch = inputDev.getNextKey();
            basicMove(ch, finalWorldFrame, avatarPt);
        }
        ArrayList list = new ArrayList();
        list.add(finalWorldFrame);
        list.add(avatarPt);
        return list;
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, both of these calls:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        long seed = 0;
        input = input.toLowerCase();
        StringInputDevice inputDev = new StringInputDevice(input);
        boolean start = false;
        boolean loadGame = false;
        StringBuffer seedBuff = new StringBuffer();
        StringBuffer instructions = new StringBuffer();
        TETile[][] finalWorldFrame = null;
        Point avatarPt = null;
        while (inputDev.possibleNextInput()) {
            char ch = inputDev.getNextKey();
            if (ch != 'l' && ch != 'L') {
                instructions.append(ch);
            }
            if (ch == 'n' || ch == 'N') {
                start = true;
            } else if (ch == 's' || ch == 'S') {
                break;
            } else if (ch == 'l' || ch == 'L') {
                try {
                    FileReader reader = new FileReader("world_save4.txt");
                    BufferedReader read = new BufferedReader(reader);
                    StringBuffer inst = new StringBuffer();
                    String temp = "";
                    while ((temp = read.readLine()) != null) {
                        inst.append(temp);
                    }
                    ArrayList lst = runWorld(inst.toString());
                    finalWorldFrame = (TETile[][]) lst.get(0);
                    avatarPt = (Point) lst.get(1);
                    instructions.append(inst);
                    loadGame = true;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            } else if (start) {
                seedBuff.append(ch);
            }
        }
        if (!loadGame) {
            if (seedBuff.length() > 0) {
                seed = Long.parseLong(seedBuff.toString());
            }
            MapGenV5 mapGen = new MapGenV5(WIDTH, HEIGHT, (int) seed);
            finalWorldFrame = mapGen.getWorld();
            avatarPt = mapGen.getAvatarPoint();
        }
        char prevChar = ' ';
        while (inputDev.possibleNextInput()) {
            char ch = inputDev.getNextKey();
            if (ch == 'w') {
                avatarPt = move(Direction.DIR.UP, finalWorldFrame, avatarPt);
            } else if (ch == 'a') {
                avatarPt = move(Direction.DIR.LEFT, finalWorldFrame, avatarPt);
            } else if (ch == 's') {
                avatarPt = move(Direction.DIR.DOWN, finalWorldFrame, avatarPt);
            } else if (ch == 'd') {
                avatarPt = move(Direction.DIR.RIGHT, finalWorldFrame, avatarPt);
            } else if (ch == 'q' && prevChar == ':') {
                String instList = instructions.toString();
                try {
                    FileWriter file = new FileWriter("world_save4.txt");
                    file.write(instList);
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return finalWorldFrame;
            }
            if (ch != ':' && ch != 'q') {
                instructions.append(ch);
            }
            prevChar = ch;
        }
        return finalWorldFrame;
    }

    private Point move(Direction.DIR direction, TETile[][] world, Point avatarPt) {
        int x = (int) avatarPt.getX();
        int y = (int) avatarPt.getY();
        if (direction == Direction.DIR.RIGHT) {
            if (world[x + 1][y].equals(Tileset.FLOOR)) {
                world[x][y] = Tileset.FLOOR;
                world[x + 1][y] = Tileset.AVATAR;
                avatarPt.setXY(x + 1, y);
                return new Point(x + 1, y);
            }
        } else if (direction == Direction.DIR.UP) {
            if (world[x][y + 1].equals(Tileset.FLOOR)) {
                world[x][y] = Tileset.FLOOR;
                world[x][y + 1] = Tileset.AVATAR;
                avatarPt.setXY(x, y + 1);
                return new Point(x, y + 1);
            }
        } else if (direction == Direction.DIR.DOWN) {
            if (world[x][y - 1].equals(Tileset.FLOOR)) {
                world[x][y] = Tileset.FLOOR;
                world[x][y - 1] = Tileset.AVATAR;
                avatarPt.setXY(x, y - 1);
                return new Point(x, y - 1);
            }
        } else if (direction == Direction.DIR.LEFT) {
            if (world[x - 1][y].equals(Tileset.FLOOR)) {
                world[x][y] = Tileset.FLOOR;
                world[x - 1][y] = Tileset.AVATAR;
                avatarPt.setXY(x - 1, y);
                return new Point(x - 1, y);
            }
        }
        return avatarPt;
    }
}
