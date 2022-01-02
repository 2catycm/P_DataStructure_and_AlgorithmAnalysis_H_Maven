package lab2_binary_search;

import java.util.Scanner;

public class Problem2 {
    private static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        int T = in.nextInt();
        for (int i = 0; i < T; i++) {
            System.out.println(sum(in.nextInt()));
        }
    }

    private static long sum(int n) {
//        return ((long)n)*(n+1)*(n+2)/6;
//        return ((long)n)*(n+1)*(n+2)/6;
        return ((long)n)*(n+1)/2*(n+2)/3; //算到第二个的时候一定可以除个2
    }
}
