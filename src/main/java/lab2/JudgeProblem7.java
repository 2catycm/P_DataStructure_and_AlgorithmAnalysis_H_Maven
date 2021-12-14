package lab2;

import java.util.Arrays;
import java.util.Random;

public class JudgeProblem7 {
    public static void main(String[] args) {
        int n = 5;
        int[] arrayA = new int[n];
//        Problem7WithMathematics.offset = 1;
        while (true) {
            final Random random = new Random();
            for (int i = 0; i < arrayA.length; i++) {
                arrayA[i] = random.nextInt(1000);
            }
//            System.out.println(Arrays.toString(arrayA));
            final int solve = Problem7.solve(arrayA, 3);
//            final int solve1 = Problem7WithMathematics.solve(arrayA, 1000);  //破解优化：100000
            final int solve1 = Problem7NewNew.solve(arrayA, 3);
//            System.out.println(solve1);
//            System.out.println(solve +" "+ solve1);
//            System.out.println(solve==solve1);
            if (solve!=solve1) {
                System.err.printf("expected:%d, actually:%d\nat Array:%s\noffset=%d\n", solve, solve1, Arrays.toString(arrayA), Problem7WithMathematics.offset);
//                Problem7WithMathematics.offset++;
            }
            else System.out.println("correct");
        }
    }
}

//k=2\3时， offset = 2
//猜测，3的时候只需要1
//k=4时，offset = 4;
//k=5时，offset只需要3
//k=6时，需要6
//7,5
//8,8
//9,7