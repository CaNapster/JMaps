import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class StartButtonListener implements ActionListener {
    ArrayList<Pair> pairs = new ArrayList<Pair>();
    Set<RoadGraph> curves = new HashSet<RoadGraph>();
    public RoadGraph base = null;
    public void actionPerformed(ActionEvent e) {
        if (!JMaps.getHousesList().isEmpty()){
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

        RoadGraph.makeSimple();
        curves.clear();

        ArrayList<RoadGraph> temp;

        GUI.setProgressBarValue(0);
        double sockListSize = JMaps.getSocketList().size(), count = 1;


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
                            temp = pathSearcher.getPath(base, k);
                            curves.addAll(temp);
                            for (int i2=0; i2<temp.size()-1; i2++)
                                pairs.add(new Pair(temp.get(i2), temp.get(i2+1)));
                            break;
                        }
                } else {

                    RoadGraph farestSocket = JMaps.getSocketList().get(0);
                    RoadGraph nearestInTree = curves.iterator().next();
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
            for (Point j: i.getHouses())
                CustomPainting.paintLine(GUI.get2DGraphicsBufferedImage(), Color.BLACK, i.getPoint(), j, 3);
            CustomPainting.drawSplitter(GUI.get2DGraphicsBufferedImage(), i.getPoint());
        }
            //BigDecimal wholeDist = BigDecimal.valueOf(0);
            double wholeDist=0.0;
            //0.8715
            for (Pair pointIter: pairs){

                //System.out.println(0.738*Math.sqrt((pointIter.getFirst().getPoint().x - pointIter.getSecond().getPoint().x) * (pointIter.getFirst().getPoint().x - pointIter.getSecond().getPoint().x) + (pointIter.getFirst().getPoint().y - pointIter.getSecond().getPoint().y) * (pointIter.getFirst().getPoint().y - pointIter.getSecond().getPoint().y)));
                //wholeDist.add(BigDecimal.valueOf(0.738*Math.sqrt((pointIter.getFirst().getPoint().x - pointIter.getSecond().getPoint().x) * (pointIter.getFirst().getPoint().x - pointIter.getSecond().getPoint().x) + (pointIter.getFirst().getPoint().y - pointIter.getSecond().getPoint().y) * (pointIter.getFirst().getPoint().y - pointIter.getSecond().getPoint().y))));
                wholeDist += 0.738*Math.sqrt((pointIter.getFirst().getPoint().x - pointIter.getSecond().getPoint().x) * (pointIter.getFirst().getPoint().x - pointIter.getSecond().getPoint().x) + (pointIter.getFirst().getPoint().y - pointIter.getSecond().getPoint().y) * (pointIter.getFirst().getPoint().y - pointIter.getSecond().getPoint().y));
            }
            System.out.println("whole:" + wholeDist);
            GUI.setsplitterLabel(" : " + String.valueOf(JMaps.getSocketList().size()));
            GUI.setmainCableLabel(String.valueOf(Math.round(wholeDist)));


            GUI.refreshControls();
        }
    }
}