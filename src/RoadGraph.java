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
    private Point point;

    public RoadGraph(int number, Point point){
        this.number = number;
        this.point = point;
    }

    public void setNumber(int number){
        this.number = number;
    }
    public void addToList(int vertex){
        this.list.add(vertex);
    }
    public void deleteFromList(int vertex){
        for(int i: this.list)
            if (this.list.get(i) == vertex){
                this.list.remove(i);
                return;
            }
    }
    public void setList(ArrayList<Integer> list){
        this.list.clear();
        this.list = list;
    }
    public void setPoint(Point point){
        this.point = point;
    }
    public int getNumber(){
        return this.number;
    }
    public ArrayList<Integer> getList(){
        return this.list;
    }
    public Point getPoint(){
        return this.point;
    }
    public static void setDrawSize(int size){
        RoadGraph.drawSize = size;
    }
    public static int getDrawSize(){
        return RoadGraph.drawSize;
    }
}
