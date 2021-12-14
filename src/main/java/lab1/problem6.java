package lab1;

import java.util.Scanner;

public class problem6 {
    //{C,C/x,Csinx,Ccosx,C/sinx,C/cosx,              C^x,          Cx}
    // no no no    no     no(放缩审敛法)  no           C要求[0,1)(比值审敛法)  no
    //以上为非零即散的。
    private static int[] coefficients;
    private static String[] pattern = {"", "/x", "sinx","cosx","/sinx", "/cosx", "^x", "x"};
    private static final Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        int t = in.nextInt();
        for (int i = 0; i < t; i++) {
            solve(in.next());
        }
    }

    private static void solve(String text) {
        coefficients = new int[8];
        String[] expressions = text.split("[+]");
        for(String expression:expressions){
            int i;
            for (i = expression.length()-1; i >= 0; i--)
                if (Character.isDigit(expression.charAt(i)))
                    break;
            int C = Integer.parseInt(expression.substring(0, i+1));
            final String substring = expression.substring(i + 1);
            for (int j = 0; j < 8; j++)
                if (pattern[j].equals(substring)){
                    coefficients[j]+=C;
                    break;
                }
        }
        boolean convergent = true;
        for (int coefficient:coefficients)
            if (coefficient!=0) {
                convergent = false;
                break;
            }
//        for (int i = 0; i < 8; i++) {
//            final int coefficient = coefficients[i];
////            if (i==6) {
////                convergent = 0<=coefficient && coefficient<1;
////            }//C是整数所以不需要这样判断。
//        }
        System.out.println(convergent?"yes":"no");
    }
}
