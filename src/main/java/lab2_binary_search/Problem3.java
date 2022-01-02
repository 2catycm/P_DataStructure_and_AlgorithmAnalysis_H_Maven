package lab2_binary_search;

import java.util.Scanner;

public class Problem3 {
    private static int b = 0;
    private static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        short T = in.nextShort();
        for (int i = 0; i < T; i++) {
            b = in.nextInt();
            solve();
        }
    }

    private static void solve() {
        double Xn = 0;
        double fun = 0;
        while (Math.abs((fun = function(Xn)))>=0.00001){
            Xn = Xn - fun/derivedFunction(Xn);
        }
        System.out.printf("%.10f\n",Xn);
    }

    private static double function(double x){
        return Math.pow(Math.E, x/20)*x - b;
    }
    private static double derivedFunction(double x){
        return Math.pow(Math.E, x/20)*(x/20+1);
    }

}
