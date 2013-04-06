import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
            CustomPainting.paintVertex(GUI.get2DGraphicsBufferedImage(), Color.red, e.getPoint(), RoadGraph.getDrawSize(), i);
            i++;
            scrollStateChanged.stateChange();
            GUI.repaintLabel();
            GUI.repaintScrollPane2();
        }
    }
    @Override
    public void mouseReleased(MouseEvent e)
    {
        if (e.isShiftDown()){
            for(RoadGraph i: JMaps.getRoadGraphList()){
                if (i.getPoint().x >= e.getPoint().x-RoadGraph.getDrawSize() && i.getPoint().x <= e.getPoint().x+RoadGraph.getDrawSize())
                    if (i.getPoint().y >= e.getPoint().y-RoadGraph.getDrawSize() && i.getPoint().y <= e.getPoint().y+RoadGraph.getDrawSize())
                        if (i != vertex1 && vertex1!=null){
                            vertex1.addToList(i.getNumber());
                            i.addToList(vertex1.getNumber());
                            CustomPainting.paintLine(GUI.get2DGraphicsBufferedImage(), Color.black, vertex1.getPoint(), i.getPoint());
                            vertex1 = null;
                            scrollStateChanged.stateChange();
                            GUI.repaintLabel();
                            GUI.repaintScrollPane2();
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

/*
  public void mouseClicked(MouseEvent e){
        if (e.isAltDown()){
            //GUI.get2DGraphicsBufferedImage().drawImage(img, e.getX()-3, e.getY()-img.getHeight(null), null);
        }
    }
//            p.add(e.getPoint());
//            if (j == 3){
//                j=0;
//                bp.add(new BuildingsPointer(p));
//                p.clear();
//                System.out.print("added");
//            } else j++;
//        }
//        if (e.isControlDown()) {
//            for (int i=0; i<bp.size(); i++)
//                //for (int j=0; j<bp.get(i).getArrPoint().length; j++)
//                    //System.out.println(bp.get(i).getArrPoint());
//                CustomPainting.paintCustomRect(GUI.get2DGraphicsBufferedImage(), Color.red, bp.get(i).getArrPoint());
//            scrollStateChanged.stateChange();
//            GUI.repaintLabel();
//            GUI.repaintScrollPane2();
//    }
//        System.out.println(String.format("test^  %d,%d", e.getX(),e.getY()));
//    }


*/