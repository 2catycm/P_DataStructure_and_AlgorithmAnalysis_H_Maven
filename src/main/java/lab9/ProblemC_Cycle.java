package lab9;

//# pragma OJ Main
public class ProblemC_Cycle {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        final var n = in.nextInt();//nodes
        final var m = in.nextInt();//edges
        final var graph = new UndirectedGraph(n);
        for (int i = 0; i < m; i++) {
            graph.addEdge(in.nextInt(), in.nextInt());
        }
        System.out.println(graph.hasCycle() ? "Bad" : "Good");
    }
}
//# include "OnlineJudgeIO.java"
//# include "UndirectedGraph.java"