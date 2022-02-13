package lab11_bonus;

import java.util.Arrays;

//# pragma OJ Main
public class ProblemB_DP {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    private static long modulus = 998244353;
    public static void main(String[] args) {
        solve = new long[1000+1];
        solve[1] = 1;
        solve[2] = 1;
        solve[3] = 2;
        combination = new Combination(1000);
        out.println("{");
        for (int i = 1; i <= 1000; i++) {
            out.print(solve(i)+", ");
        }
        out.println("}");
//        final var n = in.nextInt();
//        int capacity = Math.max(3, n);
//        solve = new long[capacity+1];
//        solve[1] = 1;
//        solve[2] = 1;
//        solve[3] = 2;
//        combination = new Combination(capacity);
//        out.println(solve(n));
    }
    private static long[] solve;
    private static Combination combination;
    static long solve(int n) {
//        if (n==0) throw new RuntimeException("有问题");
        if (n==0) {
            while (true) ;
        };
        if (solve[n]!=0) return solve[n];
        int h = (int) (Math.log(n)/Math.log(2));
        int left, right;
        final int baseN = (1<<(h-1))-1;
        final int newLineHalf = 1<<(h-1);
        final var newLine = n-1 - baseN * 2;
        if (newLine > newLineHalf){
            left = baseN+newLineHalf;
//            right = baseN+ 2*newLineHalf- newLine; //应该是4倍的
            right = baseN+newLine-newLineHalf;
        }else{
            left = baseN+ newLine;
            right = baseN;
        }
        final var result = ((solve(left)  * combination.combination(n - 1, right)%modulus * solve(right)))%modulus;
        solve[n] = result;
        return result;
    }
}
class Combination{
    private static long modulus = 998244353;
    private int maxN;
    private static long[][] combination;

    public Combination(int n) {
        this.maxN = n;
        combination = new long[n/2+1][n+1]; //k, n
        for (int i = 0; i < n/2+1; i++) {
            Arrays.fill(combination[i], Long.MIN_VALUE);
        }
        Arrays.fill(combination[0], 1);
        for (int i = 0; i < n+1; i++) {
            combination[1][i] = i;
        }
    }
    public long combination(int n, int k){
        if (k>n/2)
            return combination(n, n-k);
        if (combination[k][n]!=Long.MIN_VALUE)
            return combination[k][n];
        final var result = ((long)combination(n - 1, k - 1) + combination(n - 1, k))%modulus;
        combination[k][n] = result;
        return result;
    }
}
//# include "OnlineJudgeIO.java"