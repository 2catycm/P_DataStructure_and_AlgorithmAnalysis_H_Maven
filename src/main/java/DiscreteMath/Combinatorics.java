package DiscreteMath;

import java.math.BigInteger;

public class Combinatorics {
    static BigInteger factorial(int n){
        BigInteger product = BigInteger.ONE;
        for (int i = 0; i < n; i++) product =  product.multiply(BigInteger.valueOf(i + 1));
        return product;
    }
    static BigInteger combination(int n, int k){
        assert (!factorial(k).multiply(factorial(n - k)).equals(BigInteger.ZERO));
        return factorial(n).divide(factorial(k).multiply(factorial(n - k)));
    }

    public static void main(String[] args) {
        for (int n = 1; n < 100; n++) {
            for (int r = 1; r < n; r++) {
//        int n = 7, r = 5;
                BigInteger LHS = BigInteger.ZERO;
                for (int k = 0; k <= r; k++) {
                    LHS= LHS.add(combination(n+k, k));
//                    LHS = LHS.add(combination(n+r-k, n));
                }
                final var RHS = combination(n + r + 1, r);
                try {
                    assert (LHS.equals(RHS));
                }catch(AssertionError assertion){
                    throw new ArithmeticException(String.format("failed at n=%d, r=%d", n, r));
                }
            }
        }
        System.out.println("Success!");
    }
}
