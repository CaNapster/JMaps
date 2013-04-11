import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: napster
 * Date: 4/7/13
 * Time: 7:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class StartButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        pathSearcher.searchSocks();


        RoadGraph nearestPoint = null;
        double minDistance = Double.POSITIVE_INFINITY;
        double dist;
        for (RoadGraph i: JMaps.getRoadGraphList()){
            dist = Math.sqrt((i.getPoint().x - JMaps.getStation().x)*(i.getPoint().x - JMaps.getStation().x)+(i.getPoint().y - JMaps.getStation().y)*(i.getPoint().y - JMaps.getStation().y));
            if (dist < minDistance){
                minDistance = (int)dist;
                nearestPoint = i;
                i.setImportant(true);
            }
        }


        RoadGraph.makeSimple();

        CustomPainting.fullGraphRepaint(GUI.get2DGraphicsBufferedImage(), JMaps.getRoadGraphList());

        for (Point i: JMaps.getHousesList()){
            CustomPainting.paintCircle(GUI.get2DGraphicsBufferedImage(), Color.RED, new Point(i.x,i.y), 10);
        }
        for (RoadGraph i: JMaps.getSocketList()){
            CustomPainting.paintCircle(GUI.get2DGraphicsBufferedImage(), Color.BLACK, i.getPoint(), 20);
        }


        ArrayList<RoadGraph> curves = new ArrayList<RoadGraph>();
        curves.clear();
        RoadGraph roadGraph = null;
        ArrayList<RoadGraph> temp = null;

        if (JMaps.getSocketList().size()!=1)
            for (RoadGraph x: JMaps.getSocketList()){

                if (curves.isEmpty()){

                    RoadGraph farestSocket = JMaps.getSocketList().get(0);
                    minDistance = 0;
                    for (RoadGraph i: JMaps.getSocketList()){
                        dist = Math.sqrt((i.getPoint().x - JMaps.getStation().x)*(i.getPoint().x - JMaps.getStation().x)+(i.getPoint().y - JMaps.getStation().y)*(i.getPoint().y - JMaps.getStation().y));
                        if (dist > minDistance){
                            minDistance = (int)dist;
                            farestSocket = i;
                        }
                    }

                    for (RoadGraph k: JMaps.getRoadGraphList())
                        if (k.getNumber() == farestSocket.getNumber()) {
                            temp = pathSearcher.getPath(nearestPoint, k);
                            curves.addAll(temp);
                            for (int i2=0; i2<temp.size()-1; i2++)
                                CustomPainting.paintLine(GUI.get2DGraphicsBufferedImage(),Color.RED, temp.get(i2).getPoint(), temp.get(i2+1).getPoint(), 10);
                            break;
                        }
                } else {

                    RoadGraph farestSocket = JMaps.getSocketList().get(0);
                    RoadGraph nearestInTree = curves.get(0);
                    double absoluteMIN = 0;

                    for (RoadGraph j: JMaps.getSocketList()){

                        minDistance = Double.POSITIVE_INFINITY;
                        for (RoadGraph i: curves){
                            dist = Math.sqrt((j.getPoint().x - i.getPoint().x)*(j.getPoint().x - i.getPoint().x)+(j.getPoint().y - i.getPoint().y)*(j.getPoint().y - i.getPoint().y));
                            if (dist < minDistance){
                                minDistance = (int)dist;
                            }
                        }

                        if (minDistance > absoluteMIN){
                            absoluteMIN = minDistance;
                            farestSocket = j;
                        }

                    }



                    minDistance = Double.POSITIVE_INFINITY;
                    for (RoadGraph i: curves){
                        dist = Math.sqrt((farestSocket.getPoint().x - i.getPoint().x)*(farestSocket.getPoint().x - i.getPoint().x)+(farestSocket.getPoint().y - i.getPoint().y)*(farestSocket.getPoint().y - i.getPoint().y));
                        if (dist < minDistance){
                            minDistance = (int)dist;
                            nearestInTree = i;
                        }
                    }
                    for (RoadGraph k: JMaps.getRoadGraphList())
                        if (k.getNumber() == farestSocket.getNumber()) {
                            temp = pathSearcher.getPath(nearestInTree, k);
                            curves.addAll(temp);
                            for (int i2=0; i2<temp.size()-1; i2++)
                                CustomPainting.paintLine(GUI.get2DGraphicsBufferedImage(),Color.RED, temp.get(i2).getPoint(), temp.get(i2+1).getPoint(), 10);
                            break;
                        }
                }
            }
        else {
            for (RoadGraph i: JMaps.getSocketList())
                for (RoadGraph i1: JMaps.getRoadGraphList()){
                    if (i.getNumber() == i1.getNumber()){
                        //for (RoadGraph j: pathSearcher.getPath(nearestPoint, i1)){
                        temp = pathSearcher.getPath(nearestPoint, i1);
                        for (int i2=0; i2<temp.size()-1; i2++)
                            CustomPainting.paintLine(GUI.get2DGraphicsBufferedImage(),Color.RED, temp.get(i2).getPoint(), temp.get(i2+1).getPoint(), 10);
                        //CustomPainting.paintCircle(GUI.get2DGraphicsBufferedImage(), Color.black, j.getPoint(), 40);

                    }
                }
        }

        GUI.refreshControls();
    }
}
