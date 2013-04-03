import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: napster
 * Date: 4/3/13
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildingsPointer {
    private Point[] point = {new Point(),new Point(),new Point(),new Point()};

    public static void setBuildindPointer(ArrayList<Point> p){
        Collections.sort(p, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if (o1.x >= o2.x)
                    return 1;
                else return -1;
            }
        });
    for (int i=0; i<p.size(); i++)
        System.out.println(String.format("%d,%d", p.get(i).x, p.get(i).y));
    }
}
