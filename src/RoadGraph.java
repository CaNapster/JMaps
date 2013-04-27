import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: napster
 * Date: 4/6/13
 * Time: 8:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class RoadGraph {
    private static int drawSize;
    private int number;
    private ArrayList<Integer> list = new ArrayList<Integer>();
    private static ArrayList<Point> houseList = new ArrayList<Point>();
    private Point point;
    private boolean isSocket;
    private boolean isImportant;
    private ArrayList<Point> housesInEachSock = new ArrayList<Point>();
    private int socketConnections;
    private static ArrayList<RoadGraph> removeList = new ArrayList<RoadGraph>();

    public RoadGraph(int number, Point point) {
        this.number = number;
        this.point = point;
        this.isSocket = false;
        this.socketConnections = 0;
    }

    public RoadGraph(int number, Point point, boolean important) {
        this.number = number;
        this.point = point;
        this.isSocket = false;
        this.isImportant = important;
        this.socketConnections = 0;
    }

    public RoadGraph(int number, Point point, ArrayList<Integer> list, boolean important) {
        this.number = number;
        this.point = point;
        this.list = list;
        this.isSocket = false;
        this.isImportant = important;
        this.socketConnections = 0;
    }

    public RoadGraph(int number, Point point, ArrayList<Integer> list) {
        this.number = number;
        this.point = point;
        this.list = list;
        this.isSocket = false;
        this.isImportant = false;
        this.socketConnections = 0;
    }

    public void addHouseInEachSock(Point point) {
        this.housesInEachSock.add(point);
    }

    public ArrayList<Point> getHousesInEachSock() {
        return this.housesInEachSock;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void addToList(int vertex) {
        this.list.add(vertex);
    }

    public void deleteFromList(int vertex) {
        for (int i : this.list)
            if (this.list.get(i) == vertex) {
                this.list.remove(i);
                return;
            }
    }

    public void setList(ArrayList<Integer> list) {
        this.list.clear();
        this.list = list;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public int getNumber() {
        return this.number;
    }

    public void clearList() {
        this.list.clear();
    }

    public ArrayList<Integer> getList() {
        return this.list;
    }

    public ArrayList<Point> getHouseList() {
        return this.houseList;
    }

    public void addToHouseList(Point p) {
        this.houseList.add(p);
    }

    public Point getPoint() {
        return this.point;
    }

    public static void setDrawSize(int size) {
        RoadGraph.drawSize = size;
    }

    public static int getDrawSize() {
        return RoadGraph.drawSize;
    }

    public void incSocketConnetions() {
        this.socketConnections++;
    }

    public void decSocketConnections() {
        this.socketConnections--;
    }

    public void setSocketState(boolean state) {
        this.isSocket = state;
    }

    public boolean isSocket() {
        return this.isSocket;
    }

    public int getSocketConnections() {
        return this.socketConnections;
    }

    public boolean isImportant() {
        return this.isImportant;
    }

    public void setImportant(boolean flag) {
        this.isImportant = flag;
    }

    public static void makeSimple() {
        removeList.clear();
        for (RoadGraph i : JMaps.getRoadGraphList()) {
            if (!i.isImportant() && !i.isSocket()) {
                int curve1, curve2;
                curve1 = i.getList().get(0);
                curve2 = i.getList().get(1);

                for (RoadGraph k : JMaps.getRoadGraphList()) {
                    if (k.getNumber() == curve1) {
                        k.getList().remove(k.getList().indexOf(i.getNumber()));
                        k.getList().addAll(i.getList());
                        k.getList().remove(k.getList().indexOf(k.getNumber()));
                    }
                }
                for (RoadGraph k : JMaps.getRoadGraphList()) {
                    if (k.getNumber() == curve2) {
                        k.getList().remove(k.getList().indexOf(i.getNumber()));
                        k.getList().addAll(i.getList());
                        k.getList().remove(k.getList().indexOf(k.getNumber()));
                    }
                }
                removeList.add(i);
            }
        }
        JMaps.getRoadGraphList().removeAll(removeList);
        JMaps.getRoadGraphList().trimToSize();
    }
}
