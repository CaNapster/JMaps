import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class StartButtonListener implements ActionListener {
    ArrayList<RoadGraph> curves = new ArrayList<RoadGraph>();

    public void actionPerformed(ActionEvent e) {
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

            RoadGraph.makeSimple();

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

            //go for it
            boolean flag;
            ArrayList<Pair> finalTree = new ArrayList<Pair>();
            for (EdgeForKruskal kruskalEdges : vv.getEdges()) {
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

            GUI.setsplitterLabel(" : " + String.valueOf(JMaps.getSocketList().size()));
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

            GUI.refreshControls();
        }
    }
}