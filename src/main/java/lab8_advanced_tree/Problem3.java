package lab8_advanced_tree;

import java.util.Arrays;

//# pragma OJ Main
public class Problem3 {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();

    public static void main(String[] args) {
        final var N = in.nextInt();
        final var M = in.nextInt();
        final var K = in.nextInt();
        final var arrayA = in.nextIntArray(N);
        final var arrayB = in.nextIntArray(M);
        out.printlnArray(solve(arrayA, arrayB, K));
    }

//    static int[] solve(int[] arrayA, int[] arrayB, int k) { //long
    static Long[] solve(int[] arrayA, int[] arrayB, int k) {
        Arrays.sort(arrayB);
        InferiorityQueue<IndexedData<Long>> inferiorityQueue = new InferiorityQueue<>(arrayA.length);
        final var arrayBIndex = new int[arrayA.length];
        for (int i = 0; i < arrayA.length; i++) {
            inferiorityQueue.offer(new IndexedData<Long>((long)arrayA[i]*arrayB[arrayBIndex[i]], i));
        }
        final var arrayC = new Long[k]; int arrayCIndex = 0;
        for (int i = 0; i < k; i++) {
            final var poll = inferiorityQueue.poll();
            arrayC[arrayCIndex++] = poll.getData();
            final var order = poll.getOrder();
            arrayBIndex[order]++;
            if (arrayBIndex[order]<arrayB.length)
                inferiorityQueue.offer(new IndexedData((long)arrayA[order]*arrayB[arrayBIndex[order]], order));
        }
        return arrayC;
    }
}
//# include "OnlineJudgeIO.java"
//# include "优先队列.java"
//# include "IndexedData.java"