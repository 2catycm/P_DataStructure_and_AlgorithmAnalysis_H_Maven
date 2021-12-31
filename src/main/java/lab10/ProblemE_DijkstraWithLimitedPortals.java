package lab10;

import java.awt.*;
import java.nio.channels.Pipe;
import java.util.Arrays;
import java.util.LinkedList;

//# pragma OJ Main
public class ProblemE_DijkstraWithLimitedPortals {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();

    public static void main(String[] args) {
        final var n = in.nextInt();
        final var m = in.nextInt();
        final var p = in.nextInt();
        final var k = in.nextInt();
        final var graph = new EdgeWeightedDirectedGraph(n, m, in);
        LinkedList<Integer>[] portals = new LinkedList[n + 1];
//        Arrays.fill(portals, new LinkedList<>()); //危险！共享了同一个linkedlist
        for (int i = 1; i < portals.length; i++) {
            portals[i] = new LinkedList<>();
        }
        for (int i = 0; i < p; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            portals[u].add(v);
        }
        out.println(new PortalShortestPath(graph, portals, k, in.nextInt(), in.nextInt()).solve());
    }
}

class PortalShortestPath {
    private class VertexAndPortal {
        private int vertex, portalUsed;

        public VertexAndPortal(int vertex, int portalUsed) {
            this.vertex = vertex;
            this.portalUsed = portalUsed;
        }

        public VertexAndPortal(int index) {
            this.vertex = index / (k+1) /*+1*/;
            this.portalUsed = index % (k+1);
        }

        public int index() {
            return (vertex/*-1*/) * (k+1) + portalUsed;
        }
    }

//    private long[][] distances;
    public long solve() {
//        distances = new long[graph.verticesCnt + 1][k + 1];
//        for (int i = 0; i <= graph.verticesCnt; i++) {
//            Arrays.fill(distances[i], Long.MAX_VALUE);
//        }
//        distances[1][0] = 0;
//        iq.offer(new VertexAndPortal(src, 0).index(), distances[1][0]);
        final var iq = new IndexInferiorityQueue<Long>((k+1)*(graph.verticesCnt + 1)); //存储当前最小值
        Arrays.fill(iq.keys, Long.MAX_VALUE);
        iq.offer(new VertexAndPortal(src, 0).index(), 0L); //把index记录为0，其他记录为无穷
        while (!iq.isEmpty()) {
            final var topIndex = iq.pollIndex();
            final var node = new VertexAndPortal(topIndex);
//            assert (distances[node.vertex][node.portalUsed]==iq.keyOf(topIndex));
            //是否已经到达终点？
            if (node.vertex==dst)
                return iq.keyOf(topIndex);
            //先做常规操作。
            for (var relative:graph.relativesOf(node.vertex)){
                final var newCost = relative.weight + iq.keyOf(topIndex);
                final var relativeNodeIndex = new VertexAndPortal(relative.other, node.portalUsed).index();
                if (newCost < iq.keyOf(relativeNodeIndex)){
                    if (iq.contains(relativeNodeIndex)) iq.updateKey(relativeNodeIndex, newCost);
                    else iq.offer(relativeNodeIndex, newCost);
                }
            }
            //然后做传送操作。
            if (node.portalUsed==k)
                continue;
            for (var portalDst:portals[node.vertex]){
                final var newCost = iq.keyOf(topIndex);
                final var relativeNodeIndex = new VertexAndPortal(portalDst, node.portalUsed+1).index();//使用多一个传送门的代价。
                if (newCost < iq.keyOf(relativeNodeIndex)) {
                    if (iq.contains(relativeNodeIndex)) iq.updateKey(relativeNodeIndex, newCost);
                    else iq.offer(relativeNodeIndex, newCost);
                }
            }
        }
        throw new IllegalArgumentException("Unreachable destination! ");
    }

    public PortalShortestPath(EdgeWeightedDirectedGraph graph, LinkedList<Integer>[] portals, int k, int src, int dst) {
        this.graph = graph;
        this.portals = portals;
        this.k = k;
        this.src = src;
        this.dst = dst;
    }

    private EdgeWeightedDirectedGraph graph;
    private LinkedList<Integer>[] portals;
    private int k, src, dst;
}
//# include "OnlineJudgeIO.java"
//# include "优先队列.java"
//# include "EdgeWeightedGraphs.java"