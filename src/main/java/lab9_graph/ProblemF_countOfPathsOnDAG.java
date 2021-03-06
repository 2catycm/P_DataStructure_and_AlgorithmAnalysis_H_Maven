package lab9_graph;

import java.util.*;

//# pragma OJ Main
public class ProblemF_countOfPathsOnDAG {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        int T = in.nextInt();
        for (int i = 0; i < T; i++) {
            int n = in.nextInt();
            int m = in.nextInt();
            int[] arrayA = new int[n+1];
            int[] arrayB = new int[n+1];
            for (int j = 1; j <= n; j++) {
                arrayA[j] = in.nextInt();
                arrayB[j] = in.nextInt();
            }
            final var dag = new DirectedGraph(n, m, in);
//            final var solution = new Solution(dag, arrayA, arrayB);
            final var solution = new SolutionNew(dag, arrayA, arrayB);
            final var answer = solution.getAnswer();
            if (answer.isPresent())
                out.println(answer.getAsLong());
            else{
                throw new RuntimeException("");
            }
        }
    }
    static class SolutionNew{
        private static long modulus = 1000000007;//(long) (1e9+7);
        public OptionalLong getAnswer() {
            long sum = 0;
            for (int i = 1; i < weighted_counts.length; i++) {
                sum+=weighted_counts[i]*arrayB[i];
                sum%=modulus;
            }
            return OptionalLong.of(sum);
        }
        private long[] weighted_counts;//index 1 to n.
        // counts all path from vertex i to any reachable to vertex i
        public void bfs(){
            weighted_counts = new long[dag.verticesCnt+1];
            Queue<Integer> queue = new LinkedList<>();
            for (int i = 1; i <= dag.verticesCnt; i++) {
                if (degrees[i]==0)
                    queue.offer(i);
            }
            while (!queue.isEmpty()){
                final var current = queue.poll();
                for (var relative:dag.relativesOf(current)){
                    degrees[relative]--;
                    if (degrees[relative]==0)
                        queue.offer(relative);
                    weighted_counts[relative] += weighted_counts[current]+arrayA[current]; //????????????????????????????????????
                    weighted_counts[relative]%=modulus;
                }
            }
        }
//        private BitSet isFound;
        private DirectedGraph dag;
        private int[] arrayA, arrayB;
        private int[] degrees;
        public SolutionNew(DirectedGraph dag, int[] arrayA, int[] arrayB) {
            this.dag = dag;
            this.arrayA = arrayA;
            this.arrayB = arrayB;
            degrees = new int[dag.verticesCnt+1];
            for (int i = 1; i <= dag.verticesCnt; i++) {
                for(var relative:dag.relativesOf(i))
                    degrees[relative]++;
            }
            bfs();
        }
    }

    static class Solution{
        private static long modulus = 1000000007;//(long) (1e9+7);
        public OptionalLong getAnswer(){
//            return Arrays.stream(weighted_counts).reduce(new LongBinaryOperator() {
//                @Override
//                public long applyAsLong(long left, long right) {
//                    return (left+right)%modulus;
//                }
//            });
            long sum = 0;
            for (int i = 1; i < weighted_counts.length; i++) {
                if (arrayA[i]==0)
                    continue;
                sum+=weighted_counts[i];
                sum%=modulus;
            }
            return OptionalLong.of(sum);
        }
        private long[] weighted_counts;//index 1 to n.
        // counts all path from vertex i to any reachable to vertex i.
        private BitSet isFound;
        private void dfs(int current){
            final var relatives = dag.relativesOf(current);
//            if (relatives.) //??????empty??? ??????????????????base case ?????????0
            isFound.set(current);//???????????????????????????dfs????????????????????????
            int ac = arrayA[current];
            if (ac==0)
                ac=1;
            for (var relative:relatives){
                    //??????????????????
                    weighted_counts[current] += (long) ac * (long)arrayB[relative] % modulus;
                    //???????????????????????????
                    //?????????????????????
                    if (!isFound.get(relative)) //?????????????????????????????????????????????
                        dfs(relative);

                    //?????????a???0??? ???relative???????????????0???(???????????????)
                    if (arrayA[relative] == 0) {
//                        assert (weighted_counts[relative] == 0);
                        //??????????????????????????????0.
                        weighted_counts[current] += (long)weighted_counts[relative] * (long)ac % modulus; //????????????relative???ac???1.
                        continue;
                    }
                    assert (weighted_counts[relative] % arrayA[relative] == 0);
                    weighted_counts[current] += (long)weighted_counts[relative] / (long)arrayA[relative] * (long)ac % modulus;
            }
        }

        private DirectedGraph dag;
        private int[] arrayA, arrayB;
        public Solution(DirectedGraph dag, int[] arrayA, int[] arrayB) {
            this.dag = dag;
            this.arrayA = arrayA;
            this.arrayB = arrayB;
            //dfs prerequisite
            weighted_counts = new long[dag.verticesCnt+1];
            isFound = new BitSet();
//            dfs(1); //?????????????????????????????????
            for (int i = 1; i <= dag.verticesCnt; i++) {
                if (!isFound.get(i))
                    dfs(i);
            }
        }
    }
}
//# include "OnlineJudgeIO.java"
//# include "DirectedGraph.java"