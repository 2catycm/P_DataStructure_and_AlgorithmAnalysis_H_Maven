package lab8_advanced_tree;

//# pragma OJ Main
public class Problem4 {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();

    public static void main(String[] args) {
        final var ints = in.nextIntegerArray();
        final var inferiorityQueue = new PriorityQueue<Integer>(ints);
//        final var inferiorityQueue = new InferiorityQueue<Long>(ints.length);
//        for (int i = 0; i < ints.length; i++) {
//            inferiorityQueue.offer((long)ints[i]);
//        }
        long cost = 0;
        while (inferiorityQueue.size>1){
            var first = inferiorityQueue.poll();
            var second = inferiorityQueue.poll();
            var merge = first+second;
            cost+=merge;
            inferiorityQueue.offer(merge);
        }
        out.println(cost);
//        TreeMap
    }
}
//# include "OnlineJudgeIO.java"
//# include "优先队列.java"