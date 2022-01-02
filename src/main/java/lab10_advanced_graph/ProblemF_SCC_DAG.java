package lab10_advanced_graph;

import java.util.Arrays;

//# pragma OJ Main
public class ProblemF_SCC_DAG {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        final var n = in.nextInt();
        final var m = in.nextInt();
        final var directedGraph = new DirectedGraph(n, m, in);
        final var scc = new StronglyConnectedComponents(directedGraph, directedGraph.reverse());
        //特殊情况，本来就是强联通的，不用管什么零入度零出度。
        if (scc.isStronglyConnected()){
            out.println(0);
            return;
        }
        //缩点，并且计算出入度。
        final var compressed = new DirectedGraph(scc.getCount());
        int[] inDegree = new int[compressed.verticesCnt+1];
        int[] outDegree = new int[compressed.verticesCnt+1]; //初始化：零图的degree还是零
        for (int i = 1; i <= directedGraph.verticesCnt; i++) {
            for (var relative:directedGraph.relativesOf(i)){
                //原图中，这是一条i-》relative的有向边。
                //我们把它改成分量标识到分量标识的有向边。
                final var from = scc.getIdOf(i);
                final var to = scc.getIdOf(relative);
                if (from==to) continue;
                compressed.addEdge(from, to);
                inDegree[to]++;
                outDegree[from]++;
            }
        }
        //缩点后，统计DAG零入度和零出度的点的数量。
        final var zeroIns = Arrays.stream(inDegree).parallel().filter(d -> d == 0).count() - 1;//减1是去除不用的零号元素，其值也被以为是0
        final var zeroOuts = Arrays.stream(outDegree).parallel().filter(d -> d == 0).count() - 1;
        out.println(Math.max(zeroIns, zeroOuts));
    }
}

//# include "OnlineJudgeIO.java"
//# include "DirectedGraph.java"
//# include "强联通和深搜拓扑序.java"