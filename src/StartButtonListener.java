import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class StartButtonListener implements ActionListener {
    ArrayList<RoadGraph> curves = new ArrayList<RoadGraph>();

    ArrayList<Pair> pairs = new ArrayList<Pair>();
    Set<RoadGraph> curves2 = new HashSet<RoadGraph>();
    public RoadGraph base = null;

    public void actionPerformed(ActionEvent e) {
        //CustomPainting.fullGraphRepaint(GUI.get2DGraphicsBufferedImage(), JMaps.getRoadGraphList());

        JMaps.clearRoadGraphList();
        RoadGraphLoader.loadGraph();
        JMaps.clearSocketList();
        curves.clear();
        curves2.clear();
        pairs.clear();

        if (GUI.getStateOfSlowCheckBox()){
            if (!JMaps.getHousesList().isEmpty()) {
                pathSearcher.searchSocks();

                RoadGraph base = null;
                double minDistance = Double.POSITIVE_INFINITY;
                double dist;
                for (RoadGraph i : JMaps.getRoadGraphList()) {
                    dist = Math.sqrt((i.getPoint().x - JMaps.getStation().x) * (i.getPoint().x - JMaps.getStation().x) + (i.getPoint().y - JMaps.getStation().y) * (i.getPoint().y - JMaps.getStation().y));
                    if (dist < minDistance) {
                        minDistance = (int) dist;
                        base = i;
                        i.setImportant(true);
                    }
                }

                //new graph
                for (RoadGraph rgAdd : JMaps.getSocketList())
                    curves.add(new RoadGraph(rgAdd.getNumber(), rgAdd.getPoint()));
                curves.add(new RoadGraph(base.getNumber(), base.getPoint()));
                for (RoadGraph curveIter : curves)
                    curveIter.clearList();

                //RoadGraph.makeSimple();

                //new graph for MST
                for (RoadGraph curvesIter1 : curves) {
                    curvesIter1.clearList();
                    for (RoadGraph curvesIter2 : curves) {
                        if (curvesIter2.getNumber() != curvesIter1.getNumber())
                            curvesIter1.addToList(curvesIter2.getNumber());
                    }
                }

                //fill edges for MST-structure
                TreeSet<EdgeForKruskal> edges = new TreeSet<EdgeForKruskal>();
                edges.clear();
                for (RoadGraph curveIter : curves) {
                    for (int listIter : curveIter.getList()) {
                        for (RoadGraph rgSearch : curves) {
                            if (rgSearch.getNumber() == listIter) {
                                double tempDist = pathSearcher.getCost(curveIter, rgSearch);
                                edges.add(new EdgeForKruskal(curveIter.getNumber(), rgSearch.getNumber(), tempDist));
                            }
                        }
                    }
                }
                //MST
                KruskalEdges vv = new KruskalEdges();


                for (EdgeForKruskal edge : edges) {
                    vv.insertEdge(edge);
                }

                double count = 0.0, whole = vv.getEdges().size();
                //go for it
                boolean flag;
                ArrayList<Pair> finalTree = new ArrayList<Pair>();
                for (EdgeForKruskal kruskalEdges : vv.getEdges()) {
                    count += 1;
                    for (RoadGraph rg1 : JMaps.getRoadGraphList()) {
                        if (kruskalEdges.getVertexA() == rg1.getNumber()) {
                            for (RoadGraph rg2 : JMaps.getRoadGraphList())
                                if (kruskalEdges.getVertexB() == rg2.getNumber()) {
                                    ArrayList<RoadGraph> tempArray = new ArrayList<RoadGraph>();
                                    tempArray.addAll(pathSearcher.getPath(rg1, rg2));
                                    for (int i = 0; i < tempArray.size() - 1; i++) {
                                        flag = false;
                                        for (Pair treeSearch : finalTree)
                                            if (treeSearch.getFirst().getNumber() == tempArray.get(i).getNumber() && treeSearch.getSecond().getNumber() == tempArray.get(i + 1).getNumber())
                                                flag = true;
                                        if (!flag)
                                            finalTree.add(new Pair(tempArray.get(i), tempArray.get(i + 1)));
                                    }
                                }
                        }
                    }
                    GUI.setProgressBarValue((int)Math.round((count/whole)*100));
                    GUI.updateProgressBar();
                }


                //output
                for (Pair pairIter : finalTree) {
                    CustomPainting.paintLine(GUI.get2DGraphicsBufferedImage(), Color.blue, pairIter.getFirst().getPoint(), pairIter.getSecond().getPoint(), 6);
                }
                for (RoadGraph i : JMaps.getSocketList()) {
                    for (Point j : i.getHousesInEachSock())
                        CustomPainting.paintLine(GUI.get2DGraphicsBufferedImage(), Color.BLACK, i.getPoint(), j, 3);
                    CustomPainting.drawSplitter(GUI.get2DGraphicsBufferedImage(), i.getPoint());
                }
                CustomPainting.paintLine(GUI.get2DGraphicsBufferedImage(), Color.black, base.getPoint(), JMaps.getStation(), 3);
                for (Point i : JMaps.getHousesList()) {
                    CustomPainting.paintCircle(GUI.get2DGraphicsBufferedImage(), Color.RED, new Point(i.x, i.y), 10);
                }
                CustomPainting.drawStation(GUI.get2DGraphicsBufferedImage(), JMaps.getStation());

                GUI.setsplitterLabel(String.valueOf(JMaps.getSocketList().size()));
                double wholeDist = 0.0, secondDist = 0.0;
                for (Pair pointIter : finalTree) {
                    wholeDist += Math.round(0.738 * Math.sqrt((pointIter.getFirst().getPoint().x - pointIter.getSecond().getPoint().x) * (pointIter.getFirst().getPoint().x - pointIter.getSecond().getPoint().x) + (pointIter.getFirst().getPoint().y - pointIter.getSecond().getPoint().y) * (pointIter.getFirst().getPoint().y - pointIter.getSecond().getPoint().y)));
                }
                GUI.setmainCableLabel(String.valueOf(wholeDist) + "m");

                for (RoadGraph rgIter : JMaps.getSocketList()) {
                    for (Point pointIter : rgIter.getHousesInEachSock()){
                        secondDist += Math.round(0.738 * Math.sqrt((rgIter.getPoint().x - pointIter.x) * (rgIter.getPoint().x - pointIter.x) + (rgIter.getPoint().y - pointIter.y) * (rgIter.getPoint().y - pointIter.y)));
                    }
                }
                secondDist += Math.round(0.738 * Math.sqrt((base.getPoint().x - JMaps.getStation().x) * (base.getPoint().x - JMaps.getStation().x) + (base.getPoint().y - JMaps.getStation().y) * (base.getPoint().y - JMaps.getStation().y)));

                GUI.setsecondaryCableLabel(String.valueOf(secondDist) + "m");

                GUI.setProgressBarValue(100);
                GUI.updateProgressBar();

                GUI.refreshControls();
            }
        }
        if (GUI.getStateOfFastCheckBox()){
            if (!JMaps.getHousesList().isEmpty()) {
                
                ////////////////////////////////////////////////////////////////////////////////

                            pathSearcher.searchSocks();

                            double minDistance = Double.POSITIVE_INFINITY;
                            double dist;
                            for (RoadGraph i: JMaps.getRoadGraphList()){
                                dist = Math.sqrt((i.getPoint().x - JMaps.getStation().x)*(i.getPoint().x - JMaps.getStation().x)+(i.getPoint().y - JMaps.getStation().y)*(i.getPoint().y - JMaps.getStation().y));
                                if (dist < minDistance){
                                    minDistance = (int)dist;
                                    base = i;
                                    i.setImportant(true);
                                }
                            }

                            curves2.clear();

                            ArrayList<RoadGraph> temp;

                            GUI.setProgressBarValue(0);
                            double sockListSize = JMaps.getSocketList().size(), count = 1;
                            //System.out.println("size " + JMaps.getSocketList().size());

                            if (JMaps.getSocketList().size()!=1)
                                for (RoadGraph x: JMaps.getSocketList()){

                                    if (curves2.isEmpty()){

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
                                                temp = pathSearcher.getPath(base, k);
                                                curves2.addAll(temp);
                                                for (int i2=0; i2<temp.size()-1; i2++)
                                                    pairs.add(new Pair(temp.get(i2), temp.get(i2+1)));
                                                break;
                                            }
                                    } else {

                                        RoadGraph farestSocket = JMaps.getSocketList().get(0);
                                        RoadGraph nearestInTree = curves2.iterator().next();
                                        double absoluteMIN = 0;

                                        for (RoadGraph j: JMaps.getSocketList()){

                                            minDistance = Double.POSITIVE_INFINITY;
                                            for (RoadGraph i: curves2){
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
                                        for (RoadGraph i: curves2){
                                            dist = Math.sqrt((farestSocket.getPoint().x - i.getPoint().x)*(farestSocket.getPoint().x - i.getPoint().x)+(farestSocket.getPoint().y - i.getPoint().y)*(farestSocket.getPoint().y - i.getPoint().y));
                                            if (dist < minDistance){
                                                minDistance = (int)dist;
                                                nearestInTree = i;
                                            }
                                        }
                                        for (RoadGraph k: JMaps.getRoadGraphList())
                                            if (k.getNumber() == farestSocket.getNumber()) {
                                                temp = pathSearcher.getPath(nearestInTree, k);
                                                curves2.addAll(temp);
                                                for (int i2=0; i2<temp.size()-1; i2++)
                                                    pairs.add(new Pair(temp.get(i2), temp.get(i2+1)));
                                                break;
                                            }
                                    }

                                    GUI.setProgressBarValue((int)Math.round((double)(count/sockListSize)*100));
                                    GUI.updateProgressBar();
                                    count++;

                                }
                            else {
                                for (RoadGraph i: JMaps.getSocketList())
                                    for (RoadGraph i1: JMaps.getRoadGraphList()){
                                        if (i.getNumber() == i1.getNumber()){
                                            temp = pathSearcher.getPath(base, i1);
                                            for (int i2=0; i2<temp.size()-1; i2++)
                                                pairs.add(new Pair(temp.get(i2), temp.get(i2+1)));
                                        }
                                    }
                                GUI.setProgressBarValue(100);
                                GUI.updateProgressBar();
                            }


                            for(Pair pairIter: pairs)
                                CustomPainting.paintLine(GUI.get2DGraphicsBufferedImage(), Color.blue, pairIter.getFirst().getPoint(), pairIter.getSecond().getPoint(), 6);

                            CustomPainting.drawStation(GUI.get2DGraphicsBufferedImage(), base.getPoint());

                            for (Point i: JMaps.getHousesList()){
                                CustomPainting.paintCircle(GUI.get2DGraphicsBufferedImage(), Color.RED, new Point(i.x,i.y), 10);
                            }
                            for (RoadGraph i: JMaps.getSocketList()){
                                for (Point j: i.getHousesInEachSock())
                                    CustomPainting.paintLine(GUI.get2DGraphicsBufferedImage(), Color.BLACK, i.getPoint(), j, 3);
                                CustomPainting.drawSplitter(GUI.get2DGraphicsBufferedImage(), i.getPoint());
                            }

                            GUI.refreshControls();
                        
                ////////////////////////////////////////////////////////////////////////////////
            }
        }
    }
}
