package lab3_sorting;

import java.util.Arrays;
import java.util.Random;

//expected:13, actually:12
//        at Array:[0 3 1 4 5]
public class Problem5Judge {
    public static void main(String[] args) {
        int n = 5;
        int[] arrayA = new int[n];
        Integer[] arrayAI = new Integer[n];
//        Problem7WithMathematics.offset = 1;
        while (true) {
            final Random random = new Random();
            for (int i = 0; i < arrayA.length; i++) {
                arrayAI[i] = (arrayA[i] = random.nextInt(10));
            }
            final long solve = Problem5.bruteForceReversePair(arrayAI);
            final long solve1 = Problem5.solveByMergeSort(arrayA);

            if (solve!=solve1) {
                System.err.printf("expected:%d, actually:%d\nat Array:%s\n\n", solve, solve1, Arrays.toString(arrayA));
            }
//            else System.out.println("correct");
        }
    }
}
