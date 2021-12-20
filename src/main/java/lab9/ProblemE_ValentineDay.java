package lab9;

import java.util.BitSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

//# pragma OJ Main
public class ProblemE_ValentineDay {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();

    public static void main(String[] args) {
        int n = in.nextInt();
        int m = in.nextInt();
        final var digraph = new DirectedGraph(n + m); //at most m virtual nodes .
        for (int i = 1; i <= m; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            int option = in.nextInt();
            if (option == 1) {
                digraph.addEdge(u, v);
            } else {
                assert (option == 2);
                int anonymousNode = n + i; //different edge share unique anonymous node.
                digraph.addEdge(u, anonymousNode);
                digraph.addEdge(anonymousNode, v);
            }
        }
        final var minimumTime = new MinimumTime(digraph, 1, n);
        out.println(minimumTime.getMinimumTime());
    }

    //cities 1 2 ... n
    //m directed roads among cities.
    // 1 unit time to travel a road
    //some roads take 2 unit time.
    //minimum time to go from 1 to n.
    static class MinimumTime {
        private DirectedGraph digraph;
        private int origin, destination;
//        private Integer minimumTime = null;
        private Integer minimumTime = -1;

        public Integer getMinimumTime() {
            return minimumTime;
        }

        public void bfs() {
            if (origin==destination){
                minimumTime = 0;
                return;
            }
            BitSet isFound = new BitSet();//表示发现，但可能在队列里面等待访问邻接。
            //被发现但是没有访问过邻接的顶点们
            Queue<Integer> toBeVisit = new LinkedList<>();
            Queue<Integer> depth = new LinkedList<>();
            isFound.set(origin);
            toBeVisit.offer(origin);
            depth.offer(0);
            while (!toBeVisit.isEmpty()){
                final var visiting = toBeVisit.poll();
                final int d = depth.poll();
                for(var relative:digraph.relativesOf(visiting)){
                    if (relative==destination){
                        minimumTime = d+1;
                        return;
                    }
                    if (isFound.get(relative)) continue;
                    isFound.set(relative);
                    toBeVisit.offer(relative);
                    depth.offer(d+1);
                }
            }
        }

        public MinimumTime(DirectedGraph digraph, int origin, int destination) {
            this.digraph = digraph;
            this.origin = origin;
            this.destination = destination;
            bfs();
        }
    }
}
//# include "OnlineJudgeIO.java"
//# include "DirectedGraph.java"