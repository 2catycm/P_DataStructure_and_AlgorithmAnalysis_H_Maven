package lab10_advanced_graph;

import java.util.BitSet;
import java.util.HashMap;
import java.util.stream.IntStream;

//# pragma OJ Main
public class ProblemG_CatchSpy_luogu {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        //输入
        final int n = in.nextInt();
        final int j = in.nextInt();
        HashMap<Integer, Integer> directSpy = new HashMap<>(j);//id->cost
        for (int i = 1; i <= j; i++) {
            directSpy.put(in.nextInt(), in.nextInt());
        }
        final DirectedGraph directedGraph = new DirectedGraph(n, in.nextInt(), in);
        //判断连通性
        final var reachability = new Reachability(directedGraph, directSpy.keySet());
        if (!reachability.isReachable()) {
            out.println("NO");
//            out.println(reachability.getUnreachableCnt());
            final BitSet isVisited = reachability.getIsVisited();
            out.println(IntStream.range(1, n + 1).filter(i -> !isVisited.get(i) && !directSpy.containsKey(i)).min().getAsInt());
            return;
        }
        out.println("YES");
        //缩点
        HashMap<Integer, Integer> compressedDirectSpy = new HashMap<>(j);//new id->cost
        final StronglyConnectedComponents scc = new StronglyConnectedComponents(directedGraph, directedGraph.reverse());
        final DirectedGraph compressed = new DirectedGraph(scc.getCount());
        int[] inDegree = new int[compressed.verticesCnt+1];
        int[] outDegree = new int[compressed.verticesCnt+1]; //初始化：零图的degree还是零
        for (int i = 1; i <= directedGraph.verticesCnt; i++) {
            final int idOfI = scc.getIdOf(i);
            //原来i是可以被直接抓的话，那么在缩点图当中，为了最小代价而奉献。
            //此为错误代码：
            //错误反例：如果v1不可以被直接抓，v2可以直接被抓，但是v1v2强联通，v1理应更新代价map！
            //不，没错：v1必须直接被抓才存在代价。
            if (directSpy.containsKey(i)){
                final int newCost = directSpy.get(i);
                assert (inDegree[idOfI]==0);//不仅现在，之后也不会加的
                if (compressedDirectSpy.containsKey(idOfI)){
                    final int oldCost = compressedDirectSpy.get(idOfI);
                    if (newCost <oldCost)
                        compressedDirectSpy.put(idOfI, newCost);
                }else {
                    compressedDirectSpy.put(idOfI, newCost);
                }
            }
            //继续进行缩点。
            for (int relative:directedGraph.relativesOf(i)){
                //原图中，这是一条i-》relative的有向边。
                //我们把它改成分量标识到分量标识的有向边。
                final int to = scc.getIdOf(relative);
                if (idOfI ==to) continue;
                compressed.addEdge(idOfI, to);
                inDegree[to]++;
                outDegree[idOfI]++;
            }
        }
        //找入度为0的点（都是可以被抓的）求代价和。
//        if (IntStream.range(1, compressed.verticesCnt + 1).filter(i -> inDegree[i] == 0).anyMatch(i->!compressedDirectSpy.containsKey(i))) {
//            throw new RuntimeException("算法有问题");
//        }
        final long sum = IntStream.range(1, compressed.verticesCnt + 1).parallel().filter(i -> inDegree[i] == 0).mapToLong(compressedDirectSpy::get).sum(); //expected 2, actually 0
        out.println(sum);
    }
}

//# include "OnlineJudgeIO.java"
//# include "DirectedGraph.java"
//# include "强联通和深搜拓扑序.java"