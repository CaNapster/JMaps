import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class mouseListener implements MouseListener{
    private static int i=0;
    private static RoadGraph vertex1;
    @Override
    public  void mouseClicked(MouseEvent e){
        RoadGraph.setDrawSize(20);
        if (GUI.getStateOfHousesCheckBox()){
            if (e.isControlDown()){
                JMaps.addRoadGraphList(new RoadGraph(i, e.getPoint(), true));
            }
            else
                JMaps.addRoadGraphList(new RoadGraph(i, e.getPoint()));

            CustomPainting.paintVertex(GUI.get2DGraphicsBufferedImage(),
                    Color.red, e.getPoint(),
                    RoadGraph.getDrawSize(),
                    JMaps.getRoadGraphList().get(JMaps.getRoadGraphList().size()-1).getNumber(),
                    JMaps.getRoadGraphList().get(JMaps.getRoadGraphList().size()-1).isImportant());

            i++;
        }
        if (e.isAltDown()){
            for (RoadGraph i: JMaps.getRoadGraphList()){
                CustomPainting.paintVertexDebug(GUI.get2DGraphicsBufferedImage(), Color.red, i.getPoint(),
                        RoadGraph.getDrawSize(), i.getNumber(), i.getList(), i.isImportant());
            }
        }
        if (e.isControlDown() && !GUI.getStateOfHousesCheckBox()){
            for (RoadGraph i: JMaps.getRoadGraphList()){
                StringBuilder str = new StringBuilder("");
                str.append(i.getList());
                str.delete(str.lastIndexOf("]"), str.lastIndexOf("]")+1);
                str.delete(0,1);
                System.out.println(
                        "JMaps.addRoadGraphList(new RoadGraph(" + i.getNumber() +
                        ", new Point(" + i.getPoint().x + "," + i.getPoint().y +")," +
                        " new ArrayList<Integer>(Arrays.asList("
                        + str.toString() + "))," + i.isImportant() + "));");
            }
        }
            GUI.repaintScrollPane1();
        GUI.repaintScrollPane2();

        scrollStateChanged.stateChange();
        GUI.repaintLabel();
    }





//    public void mouseClicked(MouseEvent e){
//        if (GUI.getStateOfHousesCheckBox()){
//            CustomPainting.paintCircle(GUI.get2DGraphicsBufferedImage(), Color.orange, e.getPoint(), 10);
//            JMaps.getHousesList().add(e.getPoint());
//            GUI.refreshControls();
//        }
    @Override
    public void mouseReleased(MouseEvent e){

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
    public void mouseExited(MouseEvent e){

    }
    @Override
    public void mousePressed(MouseEvent e){
        if (e.isShiftDown()){
            for(RoadGraph i: JMaps.getRoadGraphList()){
                if (i.getPoint().x >= e.getPoint().x-RoadGraph.getDrawSize() && i.getPoint().x <= e.getPoint().x+RoadGraph.getDrawSize())
                    if (i.getPoint().y >= e.getPoint().y-RoadGraph.getDrawSize() && i.getPoint().y <= e.getPoint().y+RoadGraph.getDrawSize())
                        vertex1 = i;
            }
        }

    }
    @Override
    public void mouseEntered(MouseEvent e){

    }
}