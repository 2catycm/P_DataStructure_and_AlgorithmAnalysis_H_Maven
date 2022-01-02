package lab10_advanced_graph;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.PriorityQueue;

//# pragma OJ Main
public class ProblemA_Dijkstra {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();

    public static void main(String[] args) {
        int n = in.nextInt();
        int m = in.nextInt();
        final var edgeWeightedDirectedGraph = new EdgeWeightedDirectedGraph(n, m, in);
        final var shortestDistance = new DijkstraShortestPath(edgeWeightedDirectedGraph, 1, false).getShortestDistance(n);
        out.println(shortestDistance ==Long.MAX_VALUE?-1:shortestDistance);
    }
}
class DijkstraShortestPath{
    public long getShortestDistance(int destination){
        return distances[destination];
    }
    private final long[] distances;
//    private int[] parents;

    private void pfs2() { //不依赖于index pq
        class Offer implements Comparable<Offer>{
            int vertex;
            long distance;
            public Offer(int vertex, long distance) {
                this.vertex = vertex;
                this.distance = distance;
            }
            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Offer offer = (Offer) o;

                if (vertex != offer.vertex) return false;
                return distance == offer.distance;
            }

            @Override
            public int hashCode() {
                int result = vertex;
                result = 31 * result + (int) (distance ^ (distance >>> 32));
                return result;
            }

            @Override
            public int compareTo(Offer o) {
//                System.out.println("comparing "+distance+" "+o.distance);
//                System.out.println(Long.compare(distance, o.distance));
                return Long.compare(distance, o.distance);
            }
        }
        BitSet isVisited = new BitSet();
//        final var offers = new PriorityQueue<Offer>(Comparator.reverseOrder());
        final var offers = new PriorityQueue<Offer>(new Comparator<Offer>() {
            @Override
            public int compare(Offer o1, Offer o2) {
                return o1.compareTo(o2);
            }
        });
        distances[source] = 0;
        offers.offer(new Offer(source, distances[source])); //起点和起点的距离零放入优先队列
        while (!offers.isEmpty()){
            final var top = offers.poll();
            if (isVisited.get(top.vertex)) //如果节点已经是最短，那么就是幽灵offer
                continue;
            //现在优先队列的top是最短距离的，而且节点之前没有被提到过，所以一定已经是最短，标记为isVisited
            isVisited.set(top.vertex);
            if(top.distance!=distances[top.vertex]){
                throw new RuntimeException("算法有问题。");
            }
            for (var edge:graph.relativesOf(top.vertex)){
                if (isVisited.get(edge.other))
                    continue; //对方已经是最短了. 直接跳过。
                final var newCost = edge.weight + distances[top.vertex]; //从top连接relative，可以提供一种可能性。
                if (newCost >= distances[edge.other])
                    continue;
                distances[edge.other] = newCost;
//                offers.remove(new Offer(edge.other, distances[edge.other]));
                offers.offer(new Offer(edge.other, newCost)); //这个之后可能被更短的offer覆盖掉。但是没有关系。
            }
        }
    }
    private void pfs() {
        final var queue = new IndexInferiorityQueue<Long>(graph.verticesCnt); // vertex->distance map.
        distances[source] = 0;
        queue.offer(source, distances[source]);
        while (!queue.isEmpty()) {
            final var top = queue.pollIndex(); //把当前最小的提离桌面。
            for (var relative:graph.relativesOf(top)){ //为每一个出边提供新的offer，对方可以接受，也可以不接受。
                final var newCost = relative.weight + distances[top];
                if (newCost < distances[relative.other]){
                    distances[relative.other] = newCost;
                    if (queue.contains(relative.other)) queue.updateKey(relative.other, newCost);
                    else queue.offer(relative.other, newCost);
                }
            }
        }
    }
    public DijkstraShortestPath(EdgeWeightedDirectedGraph graph, int source, boolean methodOne) {
        this.graph = graph;
        this.source = source;
        distances = new long[graph.verticesCnt+1];
        Arrays.fill(distances, Long.MAX_VALUE);
        distances[source] = 0;
        if (methodOne){
            pfs();
        }else
            pfs2();
    }
    private EdgeWeightedDirectedGraph graph;
    private int source;
}
//# include "OnlineJudgeIO.java"
//# include "优先队列.java"
//# include "EdgeWeightedGraphs.java"