import java.awt.*;
import java.awt.geom.GeneralPath;

/**
 * Created with IntelliJ IDEA.
 * User: napster
 * Date: 4/3/13
 * Time: 3:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustomPainting {
    public static void paintCustomRect(Graphics2D g, Color color,Point p[]){
        GeneralPath shape = new GeneralPath();
        g.setColor(color);
        shape.moveTo(p[0].x, p[0].y);
        for (int i=0; i<p.length; i++)
            shape.lineTo(p[i].x, p[i].y);
        shape.lineTo(p[0].x, p[0].y);
        g.fill(shape);
        g.draw(shape);
    }
}