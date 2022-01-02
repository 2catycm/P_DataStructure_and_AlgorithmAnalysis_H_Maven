package lab1_welcome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class problem5deprecated {
    //    199986662 600000010
    private static final Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        int cnt = 0;
        while (++cnt < 3 && in.hasNext()) {
            long lowerBound = in.nextLong();
            long upperBound = in.nextLong();
            solve(lowerBound, upperBound);
        }
    }

    private static void solve(long lowerBound, long upperBound) {
        //find min
        long min = findMin(lowerBound, upperBound);
        //find max
        long max = findMax(lowerBound, upperBound);
        //两个方法的思路完全不同，故分拆成两个方法
        System.out.println(min + " " + max);
    }

    private static long findMin(long lowerBound, long upperBound) {
        short lowerBitCount = countBit(lowerBound);
        short upperBitCount = countBit(upperBound);
        //思路：从3位数开始往上找，直到lowerBound的位数出现，然后开始判断是否符合条件
        ArrayList<ArrayList<Long>> numbersOfBit = new ArrayList<>(lowerBitCount);
        numbersOfBit.add(null);
        numbersOfBit.add(new ArrayList<Long>(Arrays.asList(0L, 1L, 8L)));
        numbersOfBit.add(new ArrayList<Long>(Arrays.asList(11L, 69L, 88L, 96L)));
        for (int i = 3; i <= lowerBitCount; i++) {
            constructNumbersOfNBit(numbersOfBit, i);
        }
        for (int i = lowerBitCount; i <= upperBitCount; i++) {
            constructNumbersOfNBit(numbersOfBit, i);
            final Optional<Long> min = numbersOfBit.get(i).stream().filter(e -> lowerBound <= e && e <= upperBound).min(Long::compareTo);
            if (min.isPresent()){
                return min.get();
            }
        }
        return -1;
    }

    private static void constructNumbersOfNBit(ArrayList<ArrayList<Long>> numbersOfBit, int i) {
        if (numbersOfBit.size() >= i+1)//已经存在i位的结果就不用算了
            return;
        ArrayList<Long> result;
        if (i%2==1){//比如3位， 那么就是2位中间插入一位
            result = new ArrayList<>(numbersOfBit.get(i-1).size()*numbersOfBit.get(1).size());
            for(long e:numbersOfBit.get(i-1))
                for (long f:numbersOfBit.get(1))
                    result.add(Long.parseLong((e+"").substring(0,i/2)+f+(e+"").substring(i/2)));
        }else{//比如4位， 那么就是2位中间插入两位
            //发现一个惊天的bug， 600000009 没有被我发现，是因为 get(2)里面没有00， 插入不了00.
            //我们可以把00补到get(2)里面，就算2位数判断的时候混进来一个一位数，filter的时候也会筛掉。
            //顺便启发了我：不应该用Long来存储，本来就是字符串操作，最后再变成long或许更好。
            result = new ArrayList<>(numbersOfBit.get(i-2).size()*numbersOfBit.get(2).size());
            for(long e:numbersOfBit.get(i-2))
                for (long f:numbersOfBit.get(2))
                    result.add(Long.parseLong((e+"").substring(0,(i-2)/2)+f+(e+"").substring((i-2)/2)));
        }
        numbersOfBit.add(result);
    }

    private static short countBit(long lowerBound) {
        return (short) (lowerBound + "").length();
    }


    private static long findMax(long lowerBound, long upperBound) {
        return 0;
    }


    private static boolean isMagicNumber(long i) {
//        StringBuilder sb = new StringBuilder(i+"").reverse();
        final char[] chars = (i + "").toCharArray();
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
        return new StringBuilder(String.valueOf(chars)).reverse().toString().equals(i + "");
    }
}
