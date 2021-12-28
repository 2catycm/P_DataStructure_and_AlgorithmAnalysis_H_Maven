package lab10;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

class EdgeWeightedDirectedGraph{
    public class Edge{
        protected int other;
        protected int weight;
        public Edge(int other, int weight) {
            this.other = other;
            this.weight = weight;
        }
        public int getOther() {
            return other;
        }

        public int getWeight() {
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

    public void addEdge(int vertexU, int vertexV, int weight){
        adjacencyTables[vertexU].add(new Edge(vertexV, weight));
        edgesCnt++;
    }
    public Iterable<Edge> relativesOf(int vertexV){
        return adjacencyTables[vertexV];
    }

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
//class EdgeWeightedDirectedGraph extends DirectedGraph{
//    protected LinkedList<Integer>[] weights;
//    public EdgeWeightedDirectedGraph(int verticesCnt, int edgesCnt, OJReader in) {
//        super(verticesCnt, edgesCnt, in);
//    }
//    public EdgeWeightedDirectedGraph(int verticesCnt) {
//        super(verticesCnt);
//    }
//
//
//
//    public String drawableString(){
//        final var stringBuilder = new StringBuilder();
//        for (int i = 1; i <= verticesCnt; i++) {
//
//        }
//        return stringBuilder.toString();
//    }
//    @Override
//    public String toString() {
//        return "DirectedGraph{" +
//                "verticesCnt=" + verticesCnt +
//                ", edgesCnt=" + edgesCnt +
//                ", adjacencyTables=" + Arrays.toString(adjacencyTables) +
//                '}';
//    }
//}
