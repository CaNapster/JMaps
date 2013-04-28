import java.util.HashSet;
import java.util.TreeSet;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: napster
 * Date: 4/27/13
 * Time: 10:42 PM
 * To change this template use File | Settings | File Templates.
 */
class EdgeForKruskal implements Comparable<EdgeForKruskal> {
    int vertexA, vertexB;
    double weight;

    public EdgeForKruskal(int vertexA, int vertexB, double weight) {
        this.vertexA = vertexA;
        this.vertexB = vertexB;
        this.weight = weight;
    }

    public int getVertexA() {
        return vertexA;
    }

    public int getVertexB() {
        return vertexB;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "(" + vertexA + ", " + vertexB + ") : Weight = " + weight;
    }

    public int compareTo(EdgeForKruskal edge) {
        //== is not compared so that duplicate values are not eliminated.
        return (this.weight < edge.weight) ? -1 : 1;
    }
}


public class KruskalEdges {
    Vector<HashSet<Integer>> vertexGroups = new Vector<HashSet<Integer>>();
    TreeSet<EdgeForKruskal> kruskalEdges = new TreeSet<EdgeForKruskal>();

    public TreeSet<EdgeForKruskal> getEdges() {
        return kruskalEdges;
    }

    HashSet<Integer> getVertexGroup(int vertex) {
        for (HashSet<Integer> vertexGroup : vertexGroups) {
            if (vertexGroup.contains(vertex)) {
                return vertexGroup;
            }
        }
        return null;
    }

    public void insertEdge(EdgeForKruskal edge) {
        int vertexA = edge.getVertexA();
        int vertexB = edge.getVertexB();

        HashSet<Integer> vertexGroupA = getVertexGroup(vertexA);
        HashSet<Integer> vertexGroupB = getVertexGroup(vertexB);

        if (vertexGroupA == null) {
            kruskalEdges.add(edge);
            if (vertexGroupB == null) {
                HashSet<Integer> htNewVertexGroup = new HashSet<Integer>();
                htNewVertexGroup.add(vertexA);
                htNewVertexGroup.add(vertexB);
                vertexGroups.add(htNewVertexGroup);
            } else {
                vertexGroupB.add(vertexA);
            }
        } else {
            if (vertexGroupB == null) {
                vertexGroupA.add(vertexB);
                kruskalEdges.add(edge);
            } else if (vertexGroupA != vertexGroupB) {
                vertexGroupA.addAll(vertexGroupB);
                vertexGroups.remove(vertexGroupB);
                kruskalEdges.add(edge);
            }
        }
    }
}

