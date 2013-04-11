import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: napster
 * Date: 4/9/13
 * Time: 3:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class pathSearcher {
    private static int maxSock = 8;
    private static int distMult = 2;
    private static double dist,dist2;
    private static RoadGraph nearest, nearestSocket;
    private static int minDistance;

    public static void setDistMult(int value){
        pathSearcher.distMult = value;
    }
    public static void setMaxSock(int value){
        pathSearcher.maxSock = value;
    }
    public static void searchSocks(){
        JMaps.getSocketList().clear();
        JMaps.getRoadGraphList().clear();
        RoadGraphLoader.loadGraph();

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
                //CustomPainting.paintLine(GUI.get2DGraphicsBufferedImage(),Color.black, new Point(arr.x, arr.y), nearestSocket.getPoint());
            }
            else {
                nearest.setSocketState(true);
                JMaps.getSocketList().add(new RoadGraph(nearest.getNumber(), nearest.getPoint(), nearest.getList()));
                JMaps.getSocketList().get(JMaps.getSocketList().size()-1).incSocketConnetions();
                JMaps.getSocketList().get(JMaps.getSocketList().size()-1).setSocketState(true);
                //CustomPainting.paintLine(GUI.get2DGraphicsBufferedImage(),Color.black, new Point(arr.x, arr.y), nearest.getPoint());
            }
        }
    }

    public static ArrayList<RoadGraph> getPath(RoadGraph from, RoadGraph to){
        ArrayList<RoadGraph> path = new ArrayList<RoadGraph>();
        ArrayList<Vertex> vertexArrayList = new ArrayList<Vertex>();
        path.clear();
        vertexArrayList.clear();
        Vertex vertex1;

        for (RoadGraph i: JMaps.getRoadGraphList()){
            vertex1 = new Vertex(i.getNumber());
            vertexArrayList.add(vertex1);
        }

        for (Vertex vertexarr: vertexArrayList){
            RoadGraph rg = null;
            for (RoadGraph x: JMaps.getRoadGraphList()){
                if (vertexarr.name == x.getNumber()){
                    rg = x;
                    break;
                }
            }

            for (Integer list: rg.getList()){
                RoadGraph rg2 = null;
                for (RoadGraph x: JMaps.getRoadGraphList()){
                    if (list == x.getNumber()){
                        rg2 = x;
                        break;
                    }
                }

                double temp = (
        (rg2.getPoint().x
                - rg.getPoint().x)
                *
                (rg2.getPoint().x
                        - rg.getPoint().x)
                        +
                (rg2.getPoint().y
                        - rg.getPoint().y)
                        *
                        (rg2.getPoint().y
                                - rg.getPoint().y)
                              );

                Vertex rg3 = null;
                for (Vertex x: vertexArrayList){
                    if (list == x.name){
                        rg3 = x;
                        break;
                    }
                }

                vertexarr.adjacencies.add(new Edge(rg3, temp));
            }
        }



        computePaths(vertexArrayList.get(JMaps.getRoadGraphList().indexOf(from)));
        List<Vertex> temppath;

        temppath = getShortestPathTo(vertexArrayList.get(JMaps.getRoadGraphList().indexOf(to)));

        for (Vertex temp: temppath){
            for (RoadGraph i: JMaps.getRoadGraphList()){
                if (i.getNumber() == temp.name) {
                    path.add(i);
                    break;
                }
            }
        }
        return path;
    }

    private static void computePaths(Vertex source)
    {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        vertexQueue.add(source);
        while (!vertexQueue.isEmpty()) {
            Vertex u = vertexQueue.poll();
            for (Edge e : u.adjacencies) {
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < v.minDistance) {
                    vertexQueue.remove(v);
                    v.minDistance = distanceThroughU ;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }
        }
    }

    private static java.util.List<Vertex> getShortestPathTo(Vertex target){
        java.util.List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
//        Collections.reverse(path);
        return path;
    }
}

class Vertex implements Comparable<Vertex> {
    public final int name;
    public ArrayList<Edge> adjacencies = new ArrayList<Edge>();
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;
    public Vertex(int argName) {
        name = argName;
    }
    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
    }
}
class Edge {
    public final Vertex target;
    public final double weight;
    public Edge(Vertex argTarget, double argWeight){
        target = argTarget;
        weight = argWeight;
    }
}
