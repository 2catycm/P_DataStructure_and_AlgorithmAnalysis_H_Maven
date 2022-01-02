package lab8_advanced_tree;

//# pragma OJ Main
public class Problem1 {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        final var N = in.nextInt();
        final var integers = new PriorityQueue<Integer>(N);
        for (int i = 0; i < N; i++) {
            final int[] counter = {0};
            integers.offer(in.nextInt(), value -> counter[0]++);
            out.println(counter[0]);
        }
    }
}
//# include "OnlineJudgeIO.java"
//# include "优先队列.java"