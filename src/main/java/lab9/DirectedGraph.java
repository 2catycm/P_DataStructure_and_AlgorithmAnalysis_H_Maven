package lab9;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

class DirectedGraph {
    private interface IsLegalEdge{
        boolean test(int u, int v, DirectedGraph digraph);
    }
    private static IsLegalEdge digraphLegalEdge = (u, v, digraph) -> !(u==v || digraph.adjacencyTables[u].contains(v)); //禁止自环和平行边。
    private static IsLegalEdge dagLegalEdge = (u, v, digraph) -> !(u==v || digraph.adjacencyTables[u].contains(v));//TODO
    public static DirectedGraph nextRandomDirectedGraph(int verticesCnt, int edgesCnt){
        return nextRandomDirectedGraph(verticesCnt, edgesCnt, digraphLegalEdge);
    }
    public static DirectedGraph nextRandomDirectedGraph(int verticesCnt, int edgesCnt, IsLegalEdge test){
        final var random = new Random();
        final var result = new DirectedGraph(verticesCnt);
        for (int i = 0; i < edgesCnt; i++) {
            int u, v;
            do {
                u = random.nextInt(verticesCnt)+1;
                v = random.nextInt(verticesCnt)+1;
            }while(!test.test(u, v, result));
            result.addEdge(u, v);
        }

        return result;
    }
    public int getVerticesCnt() {
        return verticesCnt;
    }

    public int getEdgesCnt() {
        return edgesCnt;
    }

    //支持1-v的节点下标

    /**
     * add edge (u, v), i.e. u->v
     * @param vertexU
     * @param vertexV
     */
    public void addEdge(int vertexU, int vertexV){
        adjacencyTables[vertexU].add(vertexV);
        edgesCnt++;
    }
    //支持1-v的节点下标
    public Iterable<Integer> relativesOf(int vertexV){
        return adjacencyTables[vertexV];
    }
    public DirectedGraph(int verticesCnt, int edgesCnt, OJReader in){
        this(verticesCnt);
        for (int i = 0; i < edgesCnt; i++) {
            addEdge(in.nextInt(), in.nextInt());
        }
    }
    public DirectedGraph(int verticesCnt) {
        this.verticesCnt = verticesCnt;
        this.adjacencyTables = new LinkedList[verticesCnt+1];
        for (int i = 1; i < adjacencyTables.length; i++) {
            adjacencyTables[i] = new LinkedList<>();
        }
    }
    protected int verticesCnt;//支持1-v的节点下标
    protected int edgesCnt;
    protected LinkedList<Integer>[] adjacencyTables;

    @Override
    public String toString() {
        return "DirectedGraph{" +
                "verticesCnt=" + verticesCnt +
                ", edgesCnt=" + edgesCnt +
                ", adjacencyTables=" + Arrays.toString(adjacencyTables) +
                '}';
    }
}
