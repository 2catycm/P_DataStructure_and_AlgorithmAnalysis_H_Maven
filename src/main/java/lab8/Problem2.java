package lab8;

import java.util.Arrays;
import java.util.function.IntConsumer;

//# pragma OJ Main
public class Problem2 {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();

    public static void main(String[] args) {
        final var ints = in.nextIntArray();
        out.println(solve(ints));

    }

    private static String newSolve(int[] ints) {
//        final var integers = Arrays.stream(ints).boxed().toArray();
//        final int[] i = {0};
//        new PriorityQueue<Integer>(ints, value -> i[0]++);
//        if (i[0] == 0) {
//            return "Max";
//        }
//        i[0] = 0;
//        new InferiorityQueue<Integer>(ints, value -> i[0]++);
//        if (i[0] == 0) {
//            return "Min";
//        }
        return "Neither";
    }

    private static String solve(int[] ints) {
//        if (ints.length==1)
//            return
        //一定存在两个元素
        boolean isMaxHeap = (ints[0] > ints[1]);
        for (int i = 0; i < ints.length; i++) {
            int leftChild = ((i) << 1) + 1;
            int rightChild = leftChild + 1;
            if (leftChild >= ints.length)
                continue;
            if (rightChild < ints.length)
                if ((ints[rightChild] < ints[i]) ^ isMaxHeap) //不符合条件
                    return "Neither";
            if ((ints[leftChild] < ints[i]) ^ isMaxHeap)
                return "Neither";
        }
        return isMaxHeap ? "Max" : "Min";
    }
}
//# include "OnlineJudgeIO.java"