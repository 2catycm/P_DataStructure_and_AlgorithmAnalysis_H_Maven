package lab4_linkedlist;



import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Problem1Judge {
    public static void main(String[] args) {
        int n = 3; int m = 4; int bound = 5;
        LinkedNodes<Term> linkedList1;
        LinkedNodes<Term> linkedList2;
//        Problem7WithMathematics.offset = 1;
        final Random random = new Random();
        while (true) {
            final int[] ints1_1 = nextRandomIntArray(n, random, bound);
            final int[] ints2_1 = nextRandomIntArray(m, random, bound);
            final int[] ints1_2 = nextRandomIntArray(n, random, bound);
            final int[] ints2_2 = nextRandomIntArray(m, random, bound);
            Arrays.sort(ints1_2);
            Arrays.sort(ints2_2);
            linkedList1 = new LinkedNodes<>();
            linkedList2 = new LinkedNodes<>();
            for (int i = 0; i < n; i++) {
                linkedList1.add(new Term(ints1_1[i], ints1_2[i]));
            }
            for (int i = 0; i < m; i++) {
                linkedList2.add(new Term(ints2_1[i], ints2_2[i]));
            }
            final String solve = String.valueOf(Problem1New.solveArray(linkedList1, linkedList2));
            final String solve1 = String.valueOf(Problem1New.solve(linkedList1, linkedList2));

            if (!Objects.equals(solve, solve1)) {
                System.err.printf("expected:%s, actually:%s\nat Array1:%s\nand Array2:%s\n\n", solve, solve1, linkedList1.toString(), linkedList2.toString());
            }
//            else System.out.println("correct");
        }
    }
    public static int[] nextRandomIntArray(int size, Random random, int bound){
        final int[] ints = new int[size];
        for (int i = 0; i < size; i++) {
            ints[i] = random.nextInt(bound);
        }
        return ints;
    }
}
