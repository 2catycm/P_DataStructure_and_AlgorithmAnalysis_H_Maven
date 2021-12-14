package lab2;

import java.util.Scanner;

public class Problem6New {//重构: 搜索的结果不应该返回found还是notfound，应该返回found了几个
    //经过分析，由于我们的策略是解方程，然后我们的方程是单调递增的，所以，我们findRank函数得到的值，是本数rank的最小值。
    private static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        short T = in.nextShort();
        int N;
        long M;
        for (int i = 0; i < T; i++) {
            N = in.nextInt();
            M = in.nextLong();
            System.out.println(solve(N, M));
        }
    }
    static long solve(int n, long m) {//n、i、j是int, 但是m是long！！
        long minimum = matrixA(1, Math.min(6172, n));                                if (m==1) return minimum;
        long maximum = n>=12342?matrixA(n, n):Math.max(matrixA(n, 1), matrixA(n, n)); if (m==(long)n*n) return maximum;
        long claimedSolution, minimumRequiredRank, maximumRequiredRank;
        while(minimum<=maximum){//都要取得到
            claimedSolution = minimum+(maximum-minimum)/2;
            BinarySearchResult searchResult = findRank(n, claimedSolution);
//            if(searchResult.hasFoundTarget()){
            minimumRequiredRank = searchResult.getIndex();
            maximumRequiredRank = searchResult.getIndex()+ searchResult.getTargetCount() -1;//比如找到两个，那么就可以是第三名或者第四名。没有找到，就不能符合条件
            if (maximumRequiredRank<minimumRequiredRank) {
                if (minimumRequiredRank == m)
                    minimum = claimedSolution + 1; //else ;
                else if (minimumRequiredRank<m)//rank不够大，左边的被排除
                    minimum = claimedSolution+1;
                else
                    maximum = claimedSolution-1;
            }
            else if (minimumRequiredRank<=m&&m<=maximumRequiredRank)
                return claimedSolution;
            else if (minimumRequiredRank<m)//rank不够大，左边的被排除
                minimum = claimedSolution+1;
            else
                maximum = claimedSolution-1;
//            }else{
//
//            }
        }
        throw new RuntimeException("没有解出来，算法有错误");
    }
    //返回的Result，不会返回它的first、last这样的信息，只有排名信息，因为这一题不用考虑
    static BinarySearchResult findRank(int n, long claimedSolution) {
        long targetCount = 0; long rank = 1;//前面有2个比我小，意味着我是第二名。
        for (int j = 1; j <= n; j++) { //列遍历
            //每一列，利用单调性，找到比它小的有多少个数
            final BinarySearchResult binarySearchResult = solveRowIndexByEquation(claimedSolution, j, n);
            targetCount+= binarySearchResult.getTargetCount();
            rank+=binarySearchResult.getIndex();
        }
        return new BinarySearchResult(targetCount, rank);
    }
//    private static BinarySearchResult solveRowIndexByBinarySearch(long target, int colIndex, int n) {
//
//    }
    private static BinarySearchResult solveRowIndexByEquation(long target, int colIndex, int n) {
        final double v = solveEquationForRowIndex(target, colIndex, n);
        if (v<1)
            return new BinarySearchResult(0, 0); //第一点，没有发现目标；第二点，目标值太小了，没有比它更小的，所以加0（包括无解的情况）
        else if(v>n)
            return new BinarySearchResult(0, n); //没有发现目标，但是有n个数比它小。
        else {
            final int vInt = (int) v;
            if (v- vInt >0) //不是整数，说明没有发现目标，但是有v个数比它小
                //注意精度！0.00000000001没有问题
                //0.0001有问题，认为-38024875有出现
                //0的话没有问题
                return new BinarySearchResult(0, vInt);
            else
                return new BinarySearchResult(1, vInt -1); //发现目标，目标前面的数比目标小。因为是单调的，所以只有一个数能符合要求，所以不用考虑重复统计。
                //有且只有一个目标。
        }
    }
    private static class BinarySearchResult{
        private final long targetCount;
        private final long index;

        public long getTargetCount() {
            return targetCount;
        }

        public long getIndex() {
            return index;
        }

        public BinarySearchResult(long targetCount, long index) {
            this.targetCount = targetCount;
            this.index = index;
        }

        @Override
        public String toString() {
            return "BinarySearchResult{" +
                    "targetCount=" + targetCount +
                    ", index=" + index +
                    '}';
        }
    }
    private static double solveEquationForRowIndex(long target, int colIndex, int n) { //solve rowIndex such that fun(rowIndex)==target
        double a = 1, b = 12345+colIndex, c = (long) colIndex *(colIndex-12345)-target;
        //注意到定义域内是单调递增的
        double delta = b*b-4*a*c;
        if (delta<0)
            return -1;
        return (Math.sqrt(delta)-b)/(2*a);
    }
    private static long matrixA(int rowIndex, int colIndex){ //按照数学的规范来，而不是垃圾数组从零开始规范。//经过分析，不会超过long
        return (long)rowIndex*rowIndex+ 12345L *rowIndex+ (long) colIndex *colIndex- 12345L *colIndex+ (long) rowIndex *colIndex;
    }
}
