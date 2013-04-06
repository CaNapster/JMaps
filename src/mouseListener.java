import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

/**
* Created with IntelliJ IDEA.
* User: napster
* Date: 4/3/13
* Time: 3:14 AM
* To change this template use File | Settings | File Templates.
*/
public class mouseListener implements MouseListener {
    private static Image img = new ImageIcon("Flag2.png").getImage();
    private static int i=0;
    private static RoadGraph vertex1;
    @Override
    public void mouseClicked(MouseEvent e){
        RoadGraph.setDrawSize(20);
        if (e.isAltDown()){
            JMaps.addRoadGraphList(new RoadGraph(i, e.getPoint()));
            i++;
        }
        CustomPainting.fullGraphRepaint(GUI.get2DGraphicsBufferedImage(), JMaps.getRoadGraphList());
    }
    @Override
    public void mouseReleased(MouseEvent e)
    {
        if (e.isShiftDown()){
            for(RoadGraph i: JMaps.getRoadGraphList()){
                if (i.getPoint().x >= e.getPoint().x-RoadGraph.getDrawSize() && i.getPoint().x <= e.getPoint().x+RoadGraph.getDrawSize())
                    if (i.getPoint().y >= e.getPoint().y-RoadGraph.getDrawSize() && i.getPoint().y <= e.getPoint().y+RoadGraph.getDrawSize()){
                        if (i.getList().lastIndexOf(vertex1.getNumber()) == -1)
                            if (i != vertex1 && vertex1!=null){
                                vertex1.addToList(i.getNumber());
                                i.addToList(vertex1.getNumber());
                                CustomPainting.fullGraphRepaint(GUI.get2DGraphicsBufferedImage(), JMaps.getRoadGraphList());
                                vertex1 = null;
                            }
                    }
            }
        }
    }
    @Override
    public void mouseExited(MouseEvent e)
    {

    }
    @Override
    public void mousePressed(MouseEvent e)
    {
        if (e.isShiftDown()){
            for(RoadGraph i: JMaps.getRoadGraphList()){
                if (i.getPoint().x >= e.getPoint().x-RoadGraph.getDrawSize() && i.getPoint().x <= e.getPoint().x+RoadGraph.getDrawSize())
                    if (i.getPoint().y >= e.getPoint().y-RoadGraph.getDrawSize() && i.getPoint().y <= e.getPoint().y+RoadGraph.getDrawSize())
                        vertex1 = i;
            }
        }
    }
    @Override
    public void mouseEntered(MouseEvent e)
    {

    }
}