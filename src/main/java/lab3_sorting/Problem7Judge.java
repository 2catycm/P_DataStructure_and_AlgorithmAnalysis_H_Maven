package lab3_sorting;

import java.util.Arrays;
import java.util.Random;

public class Problem7Judge {
    public static void main(String[] args) {
        int n = 3; int x1 = 0, x2 = 1;
        Line2D[] line2Ds =  new Line2D[n];
//        Problem7WithMathematics.offset = 1;
        while (true) {
            final Random random = new Random();
            for (int i = 0; i < line2Ds.length; i++) {
                line2Ds[i] = new Line2D( random.nextInt(10), random.nextInt(10));
            }
            final boolean solve = Problem7.solveBruteForce(Arrays.copyOf(line2Ds, n), x1, x2);
            final boolean solve1 = Problem7.solve(line2Ds, x1, x2);

            if (solve!=solve1) {
                System.err.printf("expected:%b, actually:%b\nat Array:%s\n\n", solve, solve1, Arrays.toString(line2Ds));
            }
//            else System.out.println("correct");
        }
    }

}
