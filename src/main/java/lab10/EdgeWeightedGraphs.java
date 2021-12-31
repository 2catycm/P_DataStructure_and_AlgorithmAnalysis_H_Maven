package lab10;

import java.util.Arrays;
import java.util.LinkedList;

class EdgeWeightedUndirectedGraph<Weight extends Comparable<Weight>> extends AbstractEdgeWeightedGraph<Weight> {
    public EdgeWeightedUndirectedGraph(int verticesCnt) {
        this.verticesCnt = verticesCnt;
        this.adjacencyTables = new LinkedList[verticesCnt+1];
        for (int i = 1; i < adjacencyTables.length; i++) {
            adjacencyTables[i] = new LinkedList<>();
        }
    }
    public void addEdge(int vertexU, int vertexV, Weight weight){
        adjacencyTables[vertexU].add(new Edge(vertexV, weight));
        adjacencyTables[vertexV].add(new Edge(vertexU, weight));
        edgesCnt++;
    }
}
class EdgeWeightedDirectedGraph extends AbstractEdgeWeightedGraph<Integer> {
    public EdgeWeightedDirectedGraph(int verticesCnt) {
        this.verticesCnt = verticesCnt;
        this.adjacencyTables = new LinkedList[verticesCnt+1];
        for (int i = 1; i < adjacencyTables.length; i++) {
            adjacencyTables[i] = new LinkedList<>();
        }
    }
    public EdgeWeightedDirectedGraph(int verticesCnt, int edgesCnt, OJReader in){
        this(verticesCnt);
        for (int i = 0; i < edgesCnt; i++) {
            addEdge(in.nextInt(), in.nextInt(), in.nextInt());
        }
    }
    @Override
    public void addEdge(int vertexU, int vertexV, Integer weight) {
        adjacencyTables[vertexU].add(new Edge(vertexV, weight));
        edgesCnt++;
    }
}
abstract class AbstractEdgeWeightedGraph<Weight extends Comparable<Weight>> {
    public class Edge{
        protected int other;
        protected Weight weight;
        public Edge(int other, Weight weight) {
            this.other = other;
            this.weight = weight;
        }
        public int getOther() {
            return other;
        }

        public Weight getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "other=" + other +
                    ", weight=" + weight +
                    '}';
        }
    }
    protected int verticesCnt;//支持1-v的节点下标
    protected int edgesCnt;
    protected LinkedList<Edge>[] adjacencyTables;

    public abstract void addEdge(int vertexU, int vertexV, Weight weight);
    public Iterable<Edge> relativesOf(int vertexV){
        return adjacencyTables[vertexV];
    }

    public int getVerticesCnt() {
        return verticesCnt;
    }

    public int getEdgesCnt() {
        return edgesCnt;
    }

    @Override
    public String toString() {
        return "EdgeWeightedDirectedGraph{" +
                "verticesCnt=" + verticesCnt +
                ", edgesCnt=" + edgesCnt +
                ", adjacencyTables=" + Arrays.toString(adjacencyTables) +
                '}';
    }
}
