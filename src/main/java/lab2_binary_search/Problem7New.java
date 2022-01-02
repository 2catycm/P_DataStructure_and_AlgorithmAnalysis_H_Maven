package lab2_binary_search;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.IntBinaryOperator;

class SingleTest{
    //    expected:975, actually:194
//    at Array:[128, 146, 194, 975, 975]
    public static void main(String[] args) {
        System.out.println(Problem7New.solve(new int[]{128, 146, 194, 975, 975}, 3));
    }
    //逻辑错误！认为975不是中位数
    //因为前缀和认为与自己相等的数是0而不是1，所以975拥有的序列和小于0，被误判为失败！
}
public class Problem7New {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int[] arrayA = new int[n];
        for (int i = 0; i < n; i++) {
            arrayA[i] = in.nextInt();
        }
//        System.out.println(solveBruteForce(arrayA, k));
        System.out.println(solve(arrayA, k));
    }
    static int solve(int[] arrayA, int k){
        //转换为一个答案区间
//        Arrays.stream(arrayA).parallel().mapToObj(e->new IndexedInteger(e,))
//        final IndexedInteger[] answerDomain = IntStream.range(0, arrayA.length).parallel().mapToObj(i -> new IndexedInteger(arrayA[i], i)).sorted().toArray(IndexedInteger[]::new);
        //LastIndexOf写法
//        int left = 0, right = answerDomain.length, mid;
        int[] answerDomain = new int[arrayA.length];
        System.arraycopy(arrayA,0,answerDomain,0, arrayA.length);
        Arrays.sort(answerDomain);

        int left = 0, right = arrayA.length-1, mid, length;
        while ((length = right-left+1) > 1){       //不！！！我们要让right取得到，这样，最后的等于，是有意义的！                              //右边取不到，最后一次while情况是left=right-1，一个元素的情况。
            mid = left+length/2;//对于偶数来说，中间偏高一点或者偏低一点都没所谓吗？
            //不，对于这题，如果我们往高处取，那么对于长度为2的case，就会先试探大值，如果大值取不到，只剩一个数，那个数就是答案。如果取得到，也是只剩一个数。
//            IndexedInteger testedAnswer = answerDomain[mid];
            int testedAnswer = answerDomain[mid];
            int comparison = isProperMedian(testedAnswer, arrayA, k);
            if (comparison==0) //我知道我这个可以，可能也是最好的了，但我想看看上面行不行？
//                if (left==mid)
//                    break;
//                else
                    left = mid; //保留mid的前提下上寻。
            else if(comparison>0) //已经知道了一个更大的了, 但是我自己实现不了，也没必要实现了
                left = mid+1;
//            else continue; //我不能排除比我更大的就实现不了啊
            else{//                right = mid; //我自己实现不了，但是我
                right = mid-1; //我确认比我更大的也实现不了。
            }
//            if (isProperMedian(testedAnswer, arrayA, k))
//                left = mid;
//            else
//                right = mid;
        }
        if (isProperMedian(answerDomain[right], arrayA, k)!=0)
            return answerDomain[left]; //应该返回-1
        else
            return answerDomain[right];
    }
   //return 0 if the testedAnswer can be implemented as median. If the actual reachable median is bigger, return 1, and -1 if smaller.
//    private static int isProperMedian(IndexedInteger testedAnswer, int[] arrayA, int k) {
    private static int isProperMedian(int testedAnswer, int[] arrayA, int k) {
//        final int data = testedAnswer.getData();
        int[] comparisonSequence = new int[arrayA.length];
//        int[] comparisonPartialSum = new int[arrayA.length];
////        int currentMinimum = Integer.MAX_VALUE;
        for (int i = 0, comparison; i < comparisonSequence.length; i++) {
//            comparison = Integer.compare(arrayA[i], data);
            comparison = Integer.compare(arrayA[i], testedAnswer);
            comparisonSequence[i]= comparison;
//            comparisonPartialSum[i] = comparison;
//            if (i!=0)
//                comparisonPartialSum[i]+=comparisonPartialSum[i-1];
        }
        //前缀和的通常含义：sum[i] = a[0]+a[1]+...+a[i]
        //性质， sum[i] - sum[j] = a[j+1]+...+sum[i]
        //我的前缀和不一样：
        //  数组 1 2 3 4 5 6 7
        //我的和 0 1 3                           sum[i] = a[0]+a[1]+...+a[i-1] sum[0] = 0;
        //而且我是基于一个比较数组
        int[] comparisonPartialSum = new int[comparisonSequence.length+1]; comparisonPartialSum[0] = 0;
        System.arraycopy(comparisonSequence,0, comparisonPartialSum,1, comparisonSequence.length);
        Arrays.parallelPrefix(comparisonPartialSum, new IntBinaryOperator() {
            @Override
            public int applyAsInt(int left, int right) {
                return left+right;
            }
        });

        int currentMin = Integer.MAX_VALUE; //左指针自由域中对应的和的最小值
        int max_sum;
        //左指针在0,自由域只有0, 当右指针移动一格 ，左指针自由域变大
        for (int i = k-1; i < comparisonPartialSum.length-1; i++) { //右指针
            currentMin = Math.min(currentMin, comparisonPartialSum[i-k+1]);
            max_sum = comparisonPartialSum[i+1]- currentMin; //
            if (max_sum>0) //代表比所问的数大的数存在
                return 1;
            else if(max_sum==0)
                return 0;
        }
        return -1;
    }
}

//class IndexedInteger implements Comparable<IndexedInteger>{
//    private final int data;
//    private final int index;
//    public IndexedInteger(int data, int index) {
//        this.data = data;
//        this.index = index;
//    }
//
//    public int getData() {
//        return data;
//    }
//
//    public int getIndex() {
//        return index;
//    }
//
//    @Override
//    public int compareTo(IndexedInteger o) {
//        return Integer.compare(this.data, o.data);
//    }
//}