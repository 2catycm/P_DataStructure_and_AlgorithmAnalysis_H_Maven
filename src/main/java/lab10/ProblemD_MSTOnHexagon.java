package lab10;

import java.util.BitSet;
import java.util.Comparator;
import java.util.PriorityQueue;

//# pragma OJ Main
public class ProblemD_MSTOnHexagon {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        final var hexagon = new Hexagon(in.nextInt(), in);
        final var prim = new Prim(hexagon, 1);
        out.println(prim.getCost());
    }
}
class Prim{
    private BitSet isVisited;
    private PriorityQueue<AbstractEdgeWeightedGraph<Long>.Edge> iq;
    private void visit(int vertex){
        isVisited.set(vertex);
        for (var edge:graph.relativesOf(vertex)){
            //这个edge是由内指向外的。
            if (!isVisited.get(edge.other))
                iq.add(edge);
        }
    }
    private void prim(int start){
        cost = 0;
        isVisited = new BitSet();
        iq = new PriorityQueue<AbstractEdgeWeightedGraph<Long>.Edge>(Comparator.comparing(edge -> edge.weight)); //小顶堆
        visit(start);
        while (!iq.isEmpty()){
            final var top = iq.poll();
            if (!isVisited.get(top.other)){
                cost+=top.weight;
                visit(top.other);
            }
        }
    }
    private long cost;
    public long getCost() {
        return cost;
    }
    public Prim(EdgeWeightedUndirectedGraph<Long> graph, int start) {
        this.graph = graph;
        prim(start);
    }
    private EdgeWeightedUndirectedGraph<Long> graph;
}
class Hexagon extends EdgeWeightedUndirectedGraph<Long>{
    private int n;

    public int getN() {
        return n;
    }

    public Hexagon(int n, OJReader in) {
        super(nodesCntOfSideLength(n));
        this.n = n;
        int[] weights = new int[super.verticesCnt+1]; //暂存权重，但是最后只是存在super的边当中。
        final var linesCnt = linesOfSideLength(n);
        int currentNode = 1; int prevLineCnt = 0;
        for (int i = 1; i <= linesCnt; i++) {
            final var nodesInLine = nodesInLine(i, n);
            for (int j = 1; j <= nodesInLine; j++) {
                weights[currentNode] = in.nextInt();
                //不是第一行，触发上连边。
                if (i!=1){
                    var adjNodeAtLastRow = currentNode - prevLineCnt;
                    if(i <= n + 1)
                        adjNodeAtLastRow--;
                    if(i > n + 1 || j > 1)
                        addEdge(currentNode, adjNodeAtLastRow, (long)weights[adjNodeAtLastRow]*weights[currentNode]);
                    adjNodeAtLastRow++;
                    if(i > n + 1 || j < nodesInLine)
                        addEdge(currentNode, adjNodeAtLastRow,(long)weights[adjNodeAtLastRow]*weights[currentNode]);
                }
                //不是第一列，触发左连边。
                if (j!=1){
                    addEdge(currentNode, currentNode-1,(long)weights[currentNode-1]*weights[currentNode]);
                }
                currentNode++;
            }
            prevLineCnt = nodesInLine; //这一行处理完了，缓存上一行的数量
        }
    }
    public static int nodesCntOfSideLength(int n){
        return 3*n*n+3*n+1;//不会爆long
    }
    public static int linesOfSideLength(int n){
        return 2*n+1;
    }
    public static int nodesInLine(int i, int n){
        return 2*n+1-Math.abs(i-n-1);
    }
}
//# include "OnlineJudgeIO.java"
//# include "EdgeWeightedGraphs.java"