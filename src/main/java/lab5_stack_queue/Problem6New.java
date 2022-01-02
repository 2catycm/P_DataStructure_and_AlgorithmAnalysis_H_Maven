package lab5_stack_queue;

public class Problem6New {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();

    public static void main(String[] args) {
        int k = in.nextInt();
        int[] arrayA = in.nextIntArray();
        out.println(solve(arrayA, k));
    }

    static int solve(int[] arrayA, int k) {
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

    private static boolean check(int[] arrayA, int windowLength, int maxAllowedDifference) {
        MaximumIntDeque maxDeque = new MaximumIntDeque(arrayA, windowLength);
        MinimumIntDeque minDeque = new MinimumIntDeque(arrayA, windowLength);
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
}
//#include "OJReader.java"
//#include "OJWriter.java"
//#include "非泛型队列与栈包.java"
//不需要indexInteger