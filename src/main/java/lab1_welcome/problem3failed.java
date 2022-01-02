package lab1_welcome;

import java.util.Scanner;

public class problem3failed {
    private static final Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        int n = in.nextInt();
        String[] result = new String[1000];//nulls
//        for (int i = 0; i < 1000; i++) {
//            System.out.println(result[i]);
//        }

        for (int i = 0; i < n; i++) {
            int decimal = in.nextInt()-1;
            final String s = result[decimal];
            if (s !=null)
                System.out.println(s);
            else {
                final String replaced = Integer.toString(decimal, 3).replace('2', '6').replace('0', '2').replace('1', '3');
                result[decimal] = replaced;
                System.out.println(replaced);
            }
        }
    }
}
//enum Number{
//    TWO(2),
//    THREE(3),
//    SIX(6);
//    private int show;
//
//    Number(int show) {
//        this.show = show;
//    }
//
//    @Override
//    public String toString() {
//        return ""+show;
//    }
//}