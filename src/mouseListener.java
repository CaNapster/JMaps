import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

/**
* Created with IntelliJ IDEA.
* User: napster
* Date: 4/3/13
* Time: 3:14 AM
* To change this template use File | Settings | File Templates.
*/
public class mouseListener implements MouseListener {
    static ArrayList<Point> p = new ArrayList<Point>();
    static int j=0;
    @Override
    public void mouseClicked(MouseEvent e){
        if (e.isAltDown()){
            p.add(e.getPoint());
            if (j == 3){
                j=0;
                System.out.print("done");
                BuildingsPointer.setBuildindPointer(p);
            }
            j++;
//        CustomPainting.paintCustomRect(GUI.get2DGraphicsBufferedImage(), Color.red, p);
//        scrollStateChanged.stateChange();
//        GUI.repaintLabel();
//        GUI.repaintScrollPane2();
    }
        System.out.println(String.format("test^  %d,%d", e.getX(),e.getY()));
    }
    @Override
    public void mouseReleased(MouseEvent e)
    {

    }
    @Override
    public void mouseExited(MouseEvent e)
    {

    }
    @Override
    public void mousePressed(MouseEvent e)
    {

    }
    @Override
    public void mouseEntered(MouseEvent e)
    {

    }
}
