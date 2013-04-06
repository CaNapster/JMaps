import com.sun.javafx.binding.StringFormatter;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: napster
 * Date: 4/3/13
 * Time: 3:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustomPainting {
    public static void paintCustomRect(Graphics2D g, Color color, Point p[]){
        GeneralPath shape = new GeneralPath();
        g.setColor(color);
        shape.moveTo(p[0].x, p[0].y);
        for (int i=0; i<p.length; i++)
            shape.lineTo(p[i].x, p[i].y);
        shape.lineTo(p[0].x, p[0].y);
        g.fill(shape);
        g.draw(shape);
    }
    public static void paintCircle(Graphics2D g, Color color, Point center, int d){
        Ellipse2D.Double circle = new Ellipse2D.Double(center.x-d/2, center.y-d/2, d, d);
        g.setColor(color);
        g.fill(circle);
    }
    public static void paintVertex(Graphics2D g, Color color, Point center, int d, int name){
        Ellipse2D.Double circle = new Ellipse2D.Double(center.x-d/2, center.y-d/2, d, d);
        g.setColor(color);
        g.fill(circle);
        g.setColor(Color.black);
        g.drawString(String.valueOf(name), center.x, center.y);
    }
    public static void paintVertexDebug(Graphics2D g, Color color, Point center, int d, int name, ArrayList<Integer> list){
        Ellipse2D.Double circle = new Ellipse2D.Double(center.x-d/2, center.y-d/2, d, d);
        g.setColor(color);
        g.fill(circle);
        g.setColor(Color.black);
        g.drawString(String.valueOf(name), center.x, center.y);
        StringBuilder str = new StringBuilder("");
        for (int i: list){
            str.append(", ");
            str.append(i);
        }
        if (str.length() > 0)
        g.drawString(str.toString(), center.x + 10, center.y);
    }

    public static void paintLine(Graphics2D g, Color color, Point begin, Point end){
        g.setColor(color);
        g.drawLine(begin.x, begin.y, end.x, end.y);
    }
}