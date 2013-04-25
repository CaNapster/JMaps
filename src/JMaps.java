import java.awt.*;
import java.util.ArrayList;

public class JMaps {

    private static ArrayList<RoadGraph> roadGraphList = new ArrayList<RoadGraph>();
    private static ArrayList<Point> houses = new ArrayList<Point>();
    private static ArrayList<RoadGraph> socketList = new ArrayList<RoadGraph>();
    private static Point station = null;

    public static void main(String args[]){
        try {
            new GUI("JMaps");
            RoadGraphLoader.loadGraph();
            //CustomPainting.fullGraphRepaint(GUI.get2DGraphicsBufferedImage(), roadGraphList);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void setStation(Point p){
        JMaps.station = p;
    }
    public static Point getStation(){
        return JMaps.station;
    }
    public static void addRoadGraphList(RoadGraph roadGraph){
        roadGraphList.add(roadGraph);
    }
    public static ArrayList<RoadGraph> getRoadGraphList(){
        return roadGraphList;
    }
    public static ArrayList<RoadGraph> getSocketList(){
        return socketList;
    }
    public static ArrayList<Point> getHousesList(){
        return houses;
    }
}