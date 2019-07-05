package byow.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Clock {
    public static void main(String[] args) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        System.out.println(formatter.format(date));
        StdDraw.setCanvasSize();
        StdDraw.text(.5, .5, formatter.format(date));
    }
}
