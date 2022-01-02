package lab2_binary_search;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.IntBinaryOperator;
class SingleTestNew{

    public static void main(String[] args) {
        //    expected:753, actually:763
//    at Array:[338, 836, 412, 753, 763]
        //是原本的judge有问题吗？不是。
        System.out.println(Problem7NewNew.solve(new int[]{338, 836, 412, 753, 763}, 3));


//        expected:175, actually:433
//        at Array:[175, 433, 166, 152, 529]
//        问题的根源在这：            if (max_sum>=0) //代表比所问的数大的数存在
        //如果已知有 一半的数小于我，一半的数大于等于我，那么无论是我自己，还是比我大的人，都不能是中位数
        //证明：根据这题中位数的定义， 一半的数小于我，中位数是他们中最大的那个，而不是我。
            System.out.println(Problem7NewNew.solve(new int[]{175, 433, 166, 152, 529}, 3));

    }
}
public class Problem7NewNew {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int[] arrayA = new int[n];
        for (int i = 0; i < n; i++) {
            arrayA[i] = in.nextInt();
        }
        System.out.println(solve(arrayA, k));
    }
    static int solve(int[] arrayA, int k){
        //转换为一个答案区间
        int[] answerDomain = new int[arrayA.length];
        System.arraycopy(arrayA,0,answerDomain,0, arrayA.length);
        Arrays.sort(answerDomain);

        int left = 0, right = arrayA.length-1, mid, length;
        while ((length = right-left+1) > 1){       //不！！！我们要让right取得到，这样，最后的等于，是有意义的！                              //右边取不到，最后一次while情况是left=right-1，一个元素的情况。
            mid = left+length/2;//对于偶数来说，中间偏高一点或者偏低一点都没所谓吗？
            //不，对于这题，如果我们往高处取，那么对于长度为2的case，就会先试探大值，如果大值取不到，只剩一个数，那个数就是答案。如果取得到，也是只剩一个数。
            int testedAnswer = answerDomain[mid];
            if (isProperMedian(testedAnswer, arrayA, k)) //我知道我这个可以，可能也是最好的了，但我想看看上面行不行？
                    left = mid; //保留mid的前提下上寻。
            else{//                right = mid; //我自己实现不了，但是我
                right = mid-1; //我确认比我更大的也实现不了。
            }
        }
        return answerDomain[left];
    }
   //Return true if the testedAnswer or something larger can be implemented as median. Otherwise, return else.
    private static boolean isProperMedian(int testedAnswer, int[] arrayA, int k) {
//        final int data = testedAnswer.getData();
//        int[] comparisonSequence = new int[arrayA.length];
//        int[] comparisonPartialSum = new int[arrayA.length];
////        int currentMinimum = Integer.MAX_VALUE;
//        for (int i = 0, comparison; i < comparisonSequence.length; i++) {
////            comparison = Integer.compare(arrayA[i], data);
//            comparison = Integer.compare(arrayA[i], testedAnswer);
//            comparisonSequence[i]= comparison;
////            comparisonPartialSum[i] = comparison;
////            if (i!=0)
////                comparisonPartialSum[i]+=comparisonPartialSum[i-1];
//        }
        int[] comparisonSequence = Arrays.stream(arrayA).parallel().map(e->e>=testedAnswer?1:-1).toArray();

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
                return true;
        }
        return false;
    }
}

