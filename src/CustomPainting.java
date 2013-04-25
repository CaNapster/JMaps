import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: napster
 * Date: 4/3/13
 * Time: 3:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustomPainting {
    private static Image imgStation = new ImageIcon("Station.png").getImage();
    private  static Image imgSplitter = new ImageIcon("Splitter.png").getImage();
    public static void drawSplitter(Graphics2D g2, Point p){
        g2.drawImage(imgSplitter, p.x-imgSplitter.getWidth(null)/2, p.y-imgSplitter.getHeight(null)/2, null);
    }
    public static void drawStation(Graphics2D g2, Point p){
        g2.drawImage(imgStation, p.x-imgStation.getWidth(null)/2, p.y-imgStation.getHeight(null)/2, null);
    }
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
    public static void paintVertex(Graphics2D g, Color color, Point center, int d, int name, boolean isImportant){
        Ellipse2D.Double circle = new Ellipse2D.Double(center.x-d/2, center.y-d/2, d, d);
        if (isImportant)
            g.setColor(Color.GREEN);
        else
            g.setColor(color);
        g.fill(circle);
        g.setColor(Color.black);
        g.drawString(String.valueOf(name), center.x, center.y);
    }
    public static void paintVertexDebug(Graphics2D g, Color color, Point center, int d, int name, ArrayList<Integer> list, boolean isImportant){
        Ellipse2D.Double circle = new Ellipse2D.Double(center.x-d/2, center.y-d/2, d, d);
        if (isImportant)
            g.setColor(Color.GREEN);
        else
            g.setColor(color);
        g.fill(circle);
        g.setColor(Color.black);
        g.drawString(String.valueOf(name), center.x, center.y);
        StringBuilder str = new StringBuilder("");
        for (int i: list){
            str.append(",");
            str.append(i);
        }
        if (str.length() > 0)
        g.drawString(str.toString(), center.x + String.valueOf(name).length()*5, center.y+30);
    }
    public static void paintLine(Graphics2D g, Color color, Point begin, Point end, int stroke){
        g.setColor(color);
        g.setStroke(new BasicStroke(stroke));
        g.drawLine(begin.x, begin.y, end.x, end.y);
    }
    public static void fullGraphRepaint(Graphics2D g2, ArrayList<RoadGraph> list){
        g2.setComposite(AlphaComposite.DstOut);
        g2.setColor(new Color(0,0,0,1f));
        g2.fillRect(0,0,GUI.getBufferedImageSize().width, GUI.getBufferedImageSize().height);
        g2.setComposite(AlphaComposite.SrcOver);

        for (RoadGraph i: list){
            for (Integer j: i.getList()){
                for (RoadGraph k: list){
                    if (k.getNumber() == j){
                        //paintLine(g2, Color.black, i.getPoint(), k.getPoint(), 1);
                        break;
                    }
                }
            }
        }

        for (RoadGraph i: list){
            paintVertexDebug(g2, Color.red, i.getPoint(), RoadGraph.getDrawSize(), i.getNumber(), i.getList(), i.isImportant());
        }
        scrollStateChanged.stateChange();
        GUI.repaintLabel();
        GUI.repaintScrollPane2();
    }
}