package lab3_sorting;

import java.util.Arrays;
import java.util.Random;

/*
2 1 2
10 9
8 2
 */
public class Problem6Judge {
    public static void main(String[] args) {
//        int n = (int) (2*10e5); int p = 20, q = (int) (2*10e5);
//        int n = 10; int p = 20, q = 30;
//        int n=6, p=16, q=79384;
//        int n=6, p=0, q=7998;
//        int n=6, p=12, q=1;
        int n=4, p=5, q=5;
//        int n=10, p=20, q=(int) (2*10e8);
        Plant[] plants = new Plant[n];
//        Problem7WithMathematics.offset = 1;
        while (true) {
            final Random random = new Random();
            for (int i = 0; i < plants.length; i++) {
//                final int r1 = random.nextInt((int) (2 * 10e7));
//                final int r2 = random.nextInt((int) (2 * 10e7));
//                plants[i] = new Plant(Math.max(r1,r2), Math.min(r1,r2));
                plants[i] = new Plant(random.nextInt(4), random.nextInt(4));
            }
            final long solve = Problem6.soundBruteForce(Arrays.copyOf(plants, plants.length), p, q);
            final long solve1 = Problem6.solve(plants, p, q);
//            System.out.printf("solve: %d\n at Array: %s\n\n", solve, Arrays.toString(plants));

            if (solve!=solve1) {
                System.err.printf("expected:%d, actually:%d\nat Array:%s\n\n", solve, solve1, Arrays.toString(plants));
            }
//            else System.out.println("correct");
        }
    }
}
