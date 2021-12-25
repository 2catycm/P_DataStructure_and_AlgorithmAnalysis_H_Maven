package lab10;

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
        final var shortestDistance = new DijkstraShortestPath(edgeWeightedDirectedGraph, 1).getShortestDistance(n);
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
                return Long.compare(distance, o.distance);
            }
        }
        BitSet isVisited = new BitSet();
        final var offers = new PriorityQueue<Offer>(Comparator.reverseOrder());
        distances[source] = 0;
        offers.offer(new Offer(source, distances[source]));
        while (!offers.isEmpty()){
            final var top = offers.poll();
            if (isVisited.get(top.vertex))
                continue;
            if (top.vertex==graph.verticesCnt) //针对本题的特殊判断。
                return;
//            if (top.distance>distances[top.vertex]) //不是大于等于。 这里是判断是否已经提到空中
//                continue;
            isVisited.set(top.vertex);
            for (var edge:graph.relativesOf(top.vertex)){
                final var newCost = edge.weight + distances[top.vertex];
                if (isVisited.get(edge.other))
                    continue; //这个对于性能很重要。
                if (newCost >= distances[edge.other])
                    continue;
                distances[edge.other] = newCost;
//                offers.remove(new Offer(edge.other, distances[edge.other]));
                offers.offer(new Offer(edge.other, newCost));
            }
        }
    }
    private void pfs() {
        final var queue = new IndexInferiorityQueue<Long>(graph.verticesCnt); // vertex->distance map.
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
    public DijkstraShortestPath(EdgeWeightedDirectedGraph graph, int source) {
        this.graph = graph;
        this.source = source;
        distances = new long[graph.verticesCnt+1];
        Arrays.fill(distances, Long.MAX_VALUE);
        distances[source] = 0;
//        pfs();
        pfs2();
    }
    private EdgeWeightedDirectedGraph graph;
    private int source;
}
//# include "OnlineJudgeIO.java"
//# include "优先队列.java"
//# include "EdgeWeightedDirectedGraph.java"