package lab10;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

//# pragma OJ Main
public class ProblemB_SCC {
//    private static Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    static class Edge{
        int u;
        int v;
        public Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }
    }
    private static Edge[] roads;
    private static DirectedGraph[] graphs;
    private static DirectedGraph[] reverses;
    private static int n;
    public static void main(String[] args) {
        n = in.nextInt();
        int m = in.nextInt();
        roads = new Edge[m];
        graphs = new DirectedGraph[m+1]; //加了index条路的图
        graphs[0] = new DirectedGraph(n);
        reverses = new DirectedGraph[m+1];
        reverses[0] = new DirectedGraph(n);
        for (int i = 0; i < m; i++) {
            roads[i] = new Edge(in.nextInt(), in.nextInt());
        }
        int left = 1, right = m, mid;
        while (left<=right){
            mid = left+(right-left)/2;
            if (check(mid)){
                right = mid-1;
            }else{
                left = mid+1;
            }
        }
        out.println(left>m?-1:left);
    }
    private static boolean check(int roadCnt){
        final var graph = graphOf(roadCnt);
        final var rGraph = reverseGraphOf(roadCnt);
//        System.out.println(graph);
//        System.out.println(rGraph);
        return new StronglyConnectedComponents(graph, rGraph).isStronglyConnected();
    }
    private static DirectedGraph graphOf(int roadCnt){
        if (graphs[roadCnt]!=null)
            return graphs[roadCnt];
        int left= 1, right = roadCnt, mid;
        while (left<=right){
            mid = left+(right-left)/2;
            if (graphs[mid]!=null)
                left = mid+1;
            else
                right = mid-1;
        }//right 是最大已造相邻图。
        graphs[roadCnt] = graphs[right].clone();
        for (int i = right; i < roadCnt; i++) {
            graphs[roadCnt].addEdge(roads[i].u, roads[i].v);
        }
        return graphs[roadCnt];
    }
    private static DirectedGraph reverseGraphOf(int roadCnt){
        if (reverses[roadCnt]!=null)
            return reverses[roadCnt];
        int left= 1, right = roadCnt, mid;
        while (left<=right){
            mid = left+(right-left)/2;
            if (reverses[mid]!=null)
                left = mid+1;
            else
                right = mid-1;
        }
        reverses[roadCnt] = reverses[right].clone();
        for (int i = right; i < roadCnt; i++) {
            reverses[roadCnt].addEdge(roads[i].v, roads[i].u);
        }
        return reverses[roadCnt];
    }
}

//# include "OnlineJudgeIO.java"
//# include "DirectedGraph.java"
//# include "强联通和深搜拓扑序.java"