package byow.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;


/**
 * Created by hug.
 */
public class KeyboardInputDevice implements InputSource {

    public KeyboardInputDevice() {
        StdDraw.setCanvasSize(80 * 16, 35 * 16);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Monaco", Font.BOLD, 60);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.7, "CS61B: The Game");
        Font font2 = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font2);
        StdDraw.text(0.5, 0.5, "New Game (N)");
        StdDraw.text(0.5, 0.4, "Load Game (L)");
        StdDraw.text(0.5, 0.3, "Replay Game (R)");
        StdDraw.text(0.5, 0.2, "Quit (Q)");
        //StdDraw.enableDoubleBuffering();
    }

    public char getNextKey() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                return c;
            }
        }
    }

    public boolean possibleNextInput() {
        return true;
    }
}
