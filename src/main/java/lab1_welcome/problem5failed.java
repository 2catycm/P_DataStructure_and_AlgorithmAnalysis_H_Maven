package lab1_welcome;

import java.util.Scanner;

public class problem5failed {
    //慢：1 99934323256
//    199986661
//            600000009

    //1999966661
    private static final Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        int cnt = 0;
        while (++cnt < 3 && in.hasNext()){
            long lowerBound = in.nextLong();
            long upperBound = in.nextLong();
            solve(lowerBound, upperBound);
        }
    }

//    private static void solve(long lowerBound, long upperBound) {
//        //find min
//        long min;
//        for (min = lowerBound; min < upperBound+1; min++)
//            if (isMagicNumber(min))
//                break;
//    }

    private static void solve(long lowerBound, long upperBound) {
        //find min
        long min;
        for (min = lowerBound; min < upperBound+1; min++) {
            if (isMagicNumber(min))
//                System.out.println(min);
                break;
            if(199986662<= min && min<=600000008)
                min = 600000008;
        }
        //find max
        long max;
        for (max = Math.min(999999666666L, upperBound); max >= lowerBound; max--) {
            if (isMagicNumber(max))
                break;
//            if(max == 600000008)
//                min = 600000008;
        }
        System.out.println(min+" "+max);
    }

    private static boolean isMagicNumber(long i) {
//        StringBuilder sb = new StringBuilder(i+"").reverse();
        final char[] chars = (i+"").toCharArray();
        for (int j = 0; j < chars.length; j++) {
            switch (chars[j]) {
                case '0':
                case '1':
                case '8':
                    break;
                case '6':
                    chars[j] = '9';
                    break;
                case '9':
                    chars[j] = '6';
                    break;
                default:
                    return false;
            }
        }
        return new StringBuilder(String.valueOf(chars)).reverse().toString().equals(i+"");
    }
}
