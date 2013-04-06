import java.util.ArrayList;

public class JMaps {

    private static ArrayList<RoadGraph> roadGraphList = new ArrayList<RoadGraph>();

    public static void main(String args[]){
        try {
            new GUI("JMaps");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void addRoadGraphList(RoadGraph roadGraph){
        roadGraphList.add(roadGraph);
    }
    public static ArrayList<RoadGraph> getRoadGraphList(){
        return roadGraphList;
    }
}