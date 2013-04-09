import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: napster
 * Date: 4/7/13
 * Time: 7:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class StartButtonListener implements ActionListener {
    private static ArrayList<RoadGraph> removeList = new ArrayList<RoadGraph>();
    public void actionPerformed(ActionEvent e) {
        removeList.clear();
        int distMult = 2;
        int maxSock = 8;
        double dist,dist2;
        RoadGraph nearest, nearestSocket;
        int minDistance;

        JMaps.getSocketList().clear();
        JMaps.getRoadGraphList().clear();
        RoadGraphLoader.loadGraph();
        //CustomPainting.fullGraphRepaint(GUI.get2DGraphicsBufferedImage(), JMaps.getRoadGraphList());
        for (Point i: JMaps.getHousesList()){
            CustomPainting.paintCircle(GUI.get2DGraphicsBufferedImage(), Color.CYAN, new Point(i.x,i.y), 10);
        }

        for (Point arr: JMaps.getHousesList()){
            minDistance=Integer.MAX_VALUE;
            nearest = null;

            for (RoadGraph i: JMaps.getRoadGraphList()){
                if (!i.isSocket()){
                    nearest = i;
                    break;
                }
            }

            if (nearest == null){
                System.out.println("No any Sockets");
            }

            for (RoadGraph i: JMaps.getRoadGraphList()){
                if (!i.isSocket()){
                    dist = Math.sqrt((i.getPoint().x - arr.x)*(i.getPoint().x - arr.x)+(i.getPoint().y - arr.y)*(i.getPoint().y - arr.y));
                    if (dist < minDistance){
                        minDistance = (int)dist;
                        nearest = i;
                    }
                }
            }

            nearestSocket = null;

            if (JMaps.getSocketList().isEmpty()){
                nearest.setSocketState(true);
                JMaps.getSocketList().add(new RoadGraph(nearest.getNumber(), nearest.getPoint(), nearest.getList()));
                JMaps.getSocketList().get(JMaps.getSocketList().size()-1).setSocketState(true);
                nearestSocket = JMaps.getSocketList().get(JMaps.getSocketList().size()-1);
            } else {

                minDistance = Integer.MAX_VALUE;
                for (RoadGraph i: JMaps.getSocketList()){
                    if (i.getSocketConnections() < maxSock){
                        dist = Math.sqrt((i.getPoint().x - arr.x)*(i.getPoint().x - arr.x)+(i.getPoint().y - arr.y)*(i.getPoint().y - arr.y));
                        if ((dist < minDistance) && (i.getSocketConnections() < maxSock)){
                            minDistance = (int)dist;
                            nearestSocket = i;
                        }
                    }
                }

                if (nearestSocket == null){
                    nearest.setSocketState(true);
                    JMaps.getSocketList().add(new RoadGraph(nearest.getNumber(), nearest.getPoint(), nearest.getList()));
                    JMaps.getSocketList().get(JMaps.getSocketList().size()-1).setSocketState(true);
                    nearestSocket = JMaps.getSocketList().get(JMaps.getSocketList().size()-1);
                }

                for (RoadGraph i: JMaps.getSocketList()){
                    dist = Math.sqrt((i.getPoint().x - arr.x)*(i.getPoint().x - arr.x)+(i.getPoint().y - arr.y)*(i.getPoint().y - arr.y));
                    if ((dist < minDistance) && (i.getSocketConnections() < maxSock)){
                        minDistance = (int)dist;
                        nearestSocket = i;
                    }
                }
            }

            dist = Math.sqrt(
                    (nearest.getPoint().x - arr.x)*((nearest.getPoint().x - arr.x))
                    +
                    (nearest.getPoint().y - arr.y)*((nearest.getPoint().y - arr.y))
            );
            dist2 = Math.sqrt(
                    (nearestSocket.getPoint().x - arr.x)*((nearestSocket.getPoint().x - arr.x))
                            +
                    (nearestSocket.getPoint().y - arr.y)*((nearestSocket.getPoint().y - arr.y))
            );

            if (dist2 <= distMult*dist){
                nearestSocket.incSocketConnetions();
                CustomPainting.paintLine(GUI.get2DGraphicsBufferedImage(),Color.black, new Point(arr.x, arr.y), nearestSocket.getPoint());
            }
            else {
                nearest.setSocketState(true);
                JMaps.getSocketList().add(new RoadGraph(nearest.getNumber(), nearest.getPoint(), nearest.getList()));
                JMaps.getSocketList().get(JMaps.getSocketList().size()-1).incSocketConnetions();
                JMaps.getSocketList().get(JMaps.getSocketList().size()-1).setSocketState(true);

                CustomPainting.paintLine(GUI.get2DGraphicsBufferedImage(),Color.black, new Point(arr.x, arr.y), nearest.getPoint());
            }
        }
        for (RoadGraph i: JMaps.getSocketList()){
            CustomPainting.paintCircle(GUI.get2DGraphicsBufferedImage(), Color.black, i.getPoint(), 20);
        }

            for(RoadGraph i: JMaps.getRoadGraphList()){
                if (!i.isImportant() && !i.isSocket()){
                    int curve1, curve2;
                    curve1 = i.getList().get(0);
                    curve2 = i.getList().get(1);

                    for (RoadGraph k: JMaps.getRoadGraphList()){
                        if (k.getNumber() == curve1){
                            k.getList().remove(k.getList().indexOf(i.getNumber()));
                            k.getList().addAll(i.getList());
                            k.getList().remove(k.getList().indexOf(k.getNumber()));
                        }
                    }
                    for (RoadGraph k: JMaps.getRoadGraphList()){
                        if (k.getNumber() == curve2){
                            k.getList().remove(k.getList().indexOf(i.getNumber()));
                            k.getList().addAll(i.getList());
                            k.getList().remove(k.getList().indexOf(k.getNumber()));
                        }
                    }
                    removeList.add(i);
                    }
                }

        JMaps.getRoadGraphList().removeAll(removeList);

        CustomPainting.fullGraphRepaint(GUI.get2DGraphicsBufferedImage(), JMaps.getRoadGraphList());

        GUI.refreshControls();
    }
}
