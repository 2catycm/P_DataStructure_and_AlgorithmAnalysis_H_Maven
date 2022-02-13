package DiscreteMath;

import util.syntax.OJReader;
import util.syntax.OJWriter;

import java.util.BitSet;

public class SieveOfEratosthenes {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();

    public static void main(String[] args) {
        int n = in.nextInt();
        long start = System.currentTimeMillis();
//        int solve2 = solve(n);
        int solve = solveImproved(n);
        long end = System.currentTimeMillis();
        System.out.println(solve + " primes");
        System.out.println((end - start) + " milliseconds");
    }

    //solving: 1到n的质数数量，质数是只有1和自己是因子，且恰好有这两个因子的数，不包括1.
    private static int solve(int n) {
        var bitSet = new BitSet(n + 1); //质数是true，合数或者1是false。
        int count = 0;
        for (int i = 2; i <= n; i++)
            bitSet.set(i);//首先假定所有非1数都是质数
        int i;
        for (i = 2; i * i <= n; i++) {//一个合数可以被一个不超过它的平方根的素数整除。
            if (bitSet.get(i)) {//如果i是素数，检查一下后面所有数，会不会被发现是合数。
                count++; //此时可以完全确定i是素数。
                for (int k = 2 * i; k <= n; k += i) { //k是1到n之间，i这个素数的倍数
                    bitSet.clear(k);
                }
            }
        }
        //继续使用刚才的i，进入下一个阶段：计量根号n下界 以上的素数。
        for (; i <= n; i++) {
            if (bitSet.get(i)) count++;
        }
        return count;
    }

    private static int solveImproved(int n) {
        var bitSet = new BitSet(n + 1); //质数是true，合数或者1是false。
        bitSet.set(2, n);
        int i;
        for (i = 2; i * i <= n; i++) {//一个合数可以被一个不超过它的平方根的素数整除。
            if (bitSet.get(i)) {//如果i是素数，检查一下后面所有数，会不会被发现是合数。
                for (int k = 2 * i; k <= n; k += i) { //k是1到n之间，i这个素数的倍数
                    bitSet.clear(k);
                }
            }
        }
        return bitSet.cardinality();
    }
}
