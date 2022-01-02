package lab9_graph;

import java.util.*;

//# pragma OJ Main
public class ProblemB_Knight {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();

    public static void main(String[] args) {
        int T = in.nextInt();
        for (int i = 0; i < T; i++) {
            final var startPosition = in.next();
            final var endPosition   = in.next();
            out.println(solve(startPosition.charAt(0) - 'a' + 1, startPosition.charAt(1) - '0',
                    endPosition.charAt(0) - 'a' + 1, endPosition.charAt(1) - '0'));
        }
    }
    //求解起始点到重点的最短路径。
    private static int solve(int s0, int s1, int e0, int e1) { //const
        return bfs(s0, s1, e0, e1);
    }
    public static int bfs(int s0, int s1, int e0, int e1) {
        boolean[][] isVisited = new boolean[8+1][8+1]; //不用0
        Queue<Integer> queue0 = new ArrayDeque<>();
        Queue<Integer> queue1 = new ArrayDeque<>();
        Queue<Integer> depthQueue = new ArrayDeque<>();
        queue0.offer(s0); queue1.offer(s1); depthQueue.offer(0);
        isVisited[s0][s1] = true;
        while (!queue0.isEmpty()){
            assert (!queue1.isEmpty());
            assert (!depthQueue.isEmpty());
            final var v0 = queue0.poll();
            final var v1 = queue1.poll();
            final var depth = depthQueue.poll();
            //首先，访问当前节点。
            if (v0==e0 && v1==e1)
                return depth;
            //最后，子节点加入队列。
            for (int i = 0; i < 8; i++) {
                final var child0 = v0 + step0[i];
                final var child1 = v1 + step1[i];
                if (!inBound(child0, child1) || isVisited[child0][child1])
                    continue;
                isVisited[child0][child1] = true;
                queue0.offer(child0);
                queue1.offer(child1);
                depthQueue.offer(depth+1);
            }
        }
        //没有搜索到路径。
        return -1;
    }
    private static int[] step0 = {-2, -1, 2, 1, 2, 1, -2, -1};
    private static int[] step1 = {-1, -2, 1, 2, -1, -2, 1, 2};
    private static boolean inBound(int x, int y){
        return 1<=x&&x<=8 && 1<=y&&y<=8;
    }


}
//# include "OnlineJudgeIO.java"