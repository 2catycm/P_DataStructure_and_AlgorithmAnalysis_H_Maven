package lab10_advanced_graph;

import java.util.HashMap;
import java.util.stream.IntStream;
//# include "OnlineJudgeIO.java"
//# include "DirectedGraph.java"
//# include "Reachability.java"
//# include "强联通和深搜拓扑序.java"

//# pragma OJ Main
public class ProblemG_CatchSpy {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        //输入
        final var n = in.nextInt();
        final var j = in.nextInt();
        HashMap<Integer, Integer> directSpy = new HashMap<>(j);//id->cost
        for (int i = 1; i <= j; i++) {
            directSpy.put(in.nextInt(), in.nextInt());
        }
        final var directedGraph = new DirectedGraph(n, in.nextInt(), in);
        //判断连通性
        final var reachability = new Reachability(directedGraph, directSpy.keySet());
        if (!reachability.isReachable()) {
            out.println("NO");
//            out.println(reachability.getUnreachableCnt());
            final var isVisited = reachability.getIsVisited();
            out.println(IntStream.range(1, n + 1).filter(i -> !isVisited.get(i) && !directSpy.containsKey(i)).count());
            return;
        }
        out.println("YES");
        //缩点
        HashMap<Integer, Integer> compressedDirectSpy = new HashMap<>(j);//new id->cost
        final var scc = new StronglyConnectedComponents(directedGraph, directedGraph.reverse());
        if (scc.isStronglyConnected()){
            out.println(0);
            return;
        }
        final var compressed = new DirectedGraph(scc.getCount());
        int[] inDegree = new int[compressed.verticesCnt+1];
        int[] outDegree = new int[compressed.verticesCnt+1]; //初始化：零图的degree还是零
        for (int i = 1; i <= directedGraph.verticesCnt; i++) {
            final var idOfI = scc.getIdOf(i);
            //原来i是可以被直接抓的话，那么在缩点图当中，为了最小代价而奉献。
            if (directSpy.containsKey(i)){
                final var newCost = directSpy.get(i);
                assert (inDegree[idOfI]==0);//不仅现在，之后也不会加的
                if (compressedDirectSpy.containsKey(idOfI)){
                    final var oldCost = compressedDirectSpy.get(idOfI);
                    if (newCost <oldCost)
                        compressedDirectSpy.put(idOfI, newCost);
                }else {
                    compressedDirectSpy.put(idOfI, newCost);
                }
            }
            //继续进行缩点。
            for (var relative:directedGraph.relativesOf(i)){
                //原图中，这是一条i-》relative的有向边。
                //我们把它改成分量标识到分量标识的有向边。
                final var to = scc.getIdOf(relative);
                if (idOfI ==to) continue;
                compressed.addEdge(idOfI, to);
                inDegree[to]++;
                outDegree[idOfI]++;
            }
        }
        //找入度为0的点（都是可以被抓的）求代价和。
        //#IFDEF DEBUG
        if (IntStream.range(1, compressed.verticesCnt + 1)
                .filter(i -> inDegree[i] == 0).
                anyMatch(i->!compressedDirectSpy.containsKey(i))) {
            throw new RuntimeException("算法有问题");
        }
        //#ENDIF
        final var sum = IntStream.range(1, compressed.verticesCnt + 1).
                parallel().filter(i -> inDegree[i] == 0).
                mapToLong(compressedDirectSpy::get).sum();
        out.println(sum);
    }
}
