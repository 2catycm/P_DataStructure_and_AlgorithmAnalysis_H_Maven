package lab5_stack_queue;

public class Problem6 {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();

    public static void main(String[] args) {
        int k = in.nextInt();
        Integer[] arrayA = in.nextIntegerArray();
//        out.println(bruteForceSolve(arrayA, k));
        out.println(solve(arrayA, k));
    }

    static int solve(Integer[] arrayA, int k) {
        int left = 1, right = arrayA.length, mid;//right 取得到,可以那么长，这是答案区间。
        while (left<=right){//最后情况 4是最后一个可以的，5是第一个不可以的，返回4。 4可以实现，于是冒险到5，被5拒绝回退到4，于是最后保守策略的是正确的。
            mid = left+(right-left)/2;
            if (check(arrayA, mid, k)){//可以实现
                left = mid+1;//冒险策略
            }else{
                right = mid-1;//保守策略
            }
        }
        return right;
    }

    private static boolean check(Integer[] arrayA, int windowLength, int maxAllowedDifference) {
        MaximumDeque<Integer> maxDeque = new MaximumDeque<>(arrayA, windowLength, Integer::compare);
        MaximumDeque<Integer> minDeque = new MaximumDeque<>(arrayA, windowLength, (o1, o2) -> Integer.compare(o2,o1));
        while(maxDeque.canSlide()){
            maxDeque.slide();
            minDeque.slide();
            if ((long)maxDeque.getMax()-minDeque.getMax()<=maxAllowedDifference)
                return true;
        }
        return false;
    }

    private static <T extends Comparable<T>>T maxIn(T[] arrayA, int startInclusive, int endExclusive) {
        T max = arrayA[startInclusive];
        for (int i = startInclusive+1; i < endExclusive; i++) {
            if (arrayA[i].compareTo(max)>0)
                max = arrayA[i];
        }
        return max;
    }

    static int bruteForceSolve(Integer[] arrayA, int k) {
        int maxLength = 0;
        for (int i = 0; i < arrayA.length; i++) {
            for (int j = i; j < arrayA.length; j++) {//包括一元素
                long max = Long.MIN_VALUE;
                long min = Long.MAX_VALUE;
                for (int l = i; l <= j; l++) {
                    max = Math.max(arrayA[l], max);
                    min = Math.min(arrayA[l], min);
                }
                if (max - min <= k)//作差时一定要long
                    maxLength = Math.max(j - i+1, maxLength);
            }
        }
        return maxLength;
    }
}
//#include "OJReader.java"
//#include "OJWriter.java"
//#include "StackAndQueue打包.java"
//不需要indexInteger