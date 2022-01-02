package lab1_welcome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class problem5 {
    //    199986662 600000010
    private static final Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        int cnt = 0;
        while (++cnt < 5 && in.hasNext()) {
            lowerBound = in.nextLong();
            upperBound = in.nextLong();
            solve();
        }
    }
    private static long lowerBound, upperBound;
    private static short lowerBitCount, upperBitCount;
    private static ArrayList<ArrayList<String>> numbersOfBit;
    private static void solve() {
        lowerBitCount = countBit(lowerBound);
        upperBitCount = countBit(upperBound);
        //find min
        long min = findMin();
        minBitCount = countBit(min);
        //find max
        long max = findMax();
        //8位数的时候，有625个; 9位数的时候，有1875个; 10位数 5*625, 11位数 3*5*625, 12位数 5*625 *5 = 15625
        //12位找max
        //如果使用组合算法，需要遍历 前面+这次的次数，但是大概还是不会超过五位数
        //如果用折半枚举法, 大概要枚举六位数少一些(跳过5、2、4、3、7等数字)
        // 6位
        System.out.println(min + " " + max);
    }

    private static long findMax() {
        for (int i = minBitCount+1; i <= upperBitCount; i++) {
            constructNumbersOfNBit(numbersOfBit, i);
        }
        for (int i = upperBitCount; i >= minBitCount; i--) {
            final Optional<Long> max = numbersOfBit.get(i).stream().map(Long::parseLong).filter(e -> lowerBound <= e && e <= upperBound).max(Long::compareTo);
            if (max.isPresent()){
                return max.get();
            }
        }
        return -1;
    }
    private static int minBitCount;
    private static long findMin() {
        //思路：从3位数开始往上找，直到lowerBound的位数出现，然后开始判断是否符合条件
        numbersOfBit = new ArrayList<>(lowerBitCount);
        numbersOfBit.add(null);//0
        numbersOfBit.add(new ArrayList<String>(Arrays.asList("0", "1", "8")));//3
        numbersOfBit.add(new ArrayList<String>(Arrays.asList("11", "69", "88", "96")));//5  //9位数的时候，有1875个; 10位数
        for (int i = 3; i <= lowerBitCount; i++) {
            constructNumbersOfNBit(numbersOfBit, i);
        }
        for (int i = lowerBitCount; i <= upperBitCount; i++) {
            constructNumbersOfNBit(numbersOfBit, i);
            final Optional<Long> min = numbersOfBit.get(i).stream().map(Long::parseLong).filter(e -> lowerBound <= e && e <= upperBound).min(Long::compareTo);
            if (min.isPresent()){
                return min.get();
            }
        }
        return -1;
    }

    private static void constructNumbersOfNBit(ArrayList<ArrayList<String>> numbersOfBit, int i) {
        if (numbersOfBit.size() >= i+1)//已经存在i位的结果就不用算了
            return;
        if (i>=6)
            numbersOfBit.set(i-3,null); //算第六个的时候，第三个可以被抛弃。四和五都要保留、一和二也要保留。
        ArrayList<String> result;
        if (i%2==1){//比如3位， 那么就是2位中间插入一位
            result = new ArrayList<>(numbersOfBit.get(i-1).size()*numbersOfBit.get(1).size());
            for(String e:numbersOfBit.get(i-1))
                for (String f:numbersOfBit.get(1))
                    result.add(e.substring(0,i/2)+f+e.substring(i/2));
        }else{//比如4位， 那么就是2位中间插入两位
            //发现一个惊天的bug， 600000009 没有被我发现，是因为 get(2)里面没有00， 插入不了00.
            //我们可以把00补到get(2)里面，就算2位数判断的时候混进来一个一位数，filter的时候也会筛掉。
            //错误，不能这样搞， 00只能被插入到有意义的数当中。
            //顺便启发了我：不应该用Long来存储，本来就是字符串操作，最后再变成long或许更好。
            result = new ArrayList<>(numbersOfBit.get(i-2).size()*numbersOfBit.get(2).size());
            for(String e:numbersOfBit.get(i-2)) {
                for (String f : numbersOfBit.get(2))
                    result.add(e.substring(0, (i - 2) / 2) + f + e.substring((i - 2) / 2));
                result.add(e.substring(0, (i - 2) / 2) + "00" + e.substring((i - 2) / 2));
            }

        }
        numbersOfBit.add(result);
    }

    private static short countBit(long lowerBound) {
        return (short) (lowerBound + "").length();
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
