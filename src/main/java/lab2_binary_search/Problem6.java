package lab2_binary_search;
//bug: 1 1 2 2 3 3 的3是第5小或者第六小的，如果找第五小的，要找到3。要找
//4
//        2 1
//        -12338
//        2 2
//        3
//        2 3
//        12
//        2 4
//        -12338 //第四小的有问题。好了找到问题了，return maximum
import java.util.*;
public class Problem6 {
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

    // 把两个array的m位数合并成一个array的最后只剩下一个array
    //不行！ 合并之后信息丢失
    //不要把这道题当作上一题的延申，不对。j的信息很重要，而且j的信息比上一题重要的多。
    //正确解法：二分答案+解方程。
    static long solve(int n, long m) {//n、i、j是int, 但是m是long！！
        long minimum = matrixA(1, Math.min(6172, n));                                if (m==1) return minimum;
        long maximum = n>=12342?matrixA(n, n):Math.max(matrixA(n, 1), matrixA(n, n)); if (m==(long)n*n) return maximum;
        long claimedSolution;
        while(minimum<=maximum){//都要取得到
            claimedSolution = minimum+(maximum-minimum)/2;
            BinarySearchResult searchResult = findRank(n, claimedSolution);
//            if(searchResult.hasFoundTarget()){
            final long rank = searchResult.getIndex();
            if (rank==m)
                if (searchResult.hasFoundTarget())
                    return claimedSolution;
                else //如果没找到就麻烦一些： 比如矩阵中有1 2; 4 8  找5,失败了, 5将在8的位置，但比如说这就是我们要找的位置，那么就要往上走
                    minimum = claimedSolution+1;
             else if (rank<m)//rank不够大，左边的被排除
                 minimum = claimedSolution+1;
             else
                 maximum = claimedSolution-1;
//            }else{
//
//            }
        }
        return 0;
    }
    //返回的Result，不会返回它的first、last这样的信息，只有排名信息，因为这一题不用考虑
    private static BinarySearchResult findRank(int n, long claimedSolution) {
        boolean hasFound = false; long rank = 1;//前面有2个比我小，意味着我是第二名。
        for (int j = 1; j <= n; j++) { //列遍历
            //每一列，利用单调性，找到比它小的有多少个数
            final BinarySearchResult binarySearchResult = solveRowIndexByEquation(claimedSolution, j, n);
            if (binarySearchResult.hasFoundTarget())
                hasFound = true;
            rank+=binarySearchResult.getIndex();
        }
        return new BinarySearchResult(hasFound, rank);
    }
    //TODO： 把二分答案写成迭代器
    private static BinarySearchResult solveRowIndexByEquation(long target, int colIndex, int n) {
        final double v = solveEquationForRowIndex(target, colIndex, n);
        if (v<1)
            return new BinarySearchResult(false, 0); //第一点，没有发现目标；第二点，目标值太小了，没有比它更小的，所以加0（包括无解的情况）
        else if(v>n)
            return new BinarySearchResult(false, n); //没有发现目标，但是有n个数比它小。
        else {
            final int vInt = (int) v;
            if (v- vInt >0.0001) //不是整数，说明没有发现目标，但是有v个数比它小
                return new BinarySearchResult(false, vInt);
            else
                return new BinarySearchResult(true, vInt -1); //发现目标，目标前面的数比目标小。因为是单调的，所以只有一个数能符合要求，所以不用考虑重复统计。
        }
    }
    private static class BinarySearchResult{
        private final boolean hasFoundTarget;
        private final long index;
        public BinarySearchResult(boolean hasFoundTarget, long index) {
            this.hasFoundTarget = hasFoundTarget;
            this.index = index;
        }

        public boolean hasFoundTarget() {
            return hasFoundTarget;
        }

        public long getIndex() {
            return index;
        }
    }

    static long veryVeryBruteForceSolve(int n, int m){
//        long[] matrixA = new long[n*n];  //数组不支持那么长的
        ArrayList<Long> matrixA = new ArrayList<>(n); //也不能传long进去
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
//                matrixA[k++] = matrixA(i+1, j+1);
                matrixA.add(matrixA(i+1, j+1)) ;
            }
        }//50000方就已经卡爆了
//        Arrays.sort(matrixA);
        Collections.sort(matrixA);
//        return matrixA[m-1]; //第一个最小的是第0个。
        return matrixA.get(m-1); //第一个最小的是第0个。
    }
    static long bruteForceSolve(int n, int m){
//        PriorityQueue<Long> stack = new PriorityQueue<>(m); //维护0到m-1 m个最小的数
        if (m==1) //最小值，那么 固定i=1，求最小值
            return matrixA(1, Math.min(6172, n));
        else if (m==(long)n*n)//最大值
            return n>=12342?matrixA(n, n):Math.max(matrixA(n, 1), matrixA(n, n));
        //假定m比中位小，这样计算的少
//        LinkedList<Long> mNumbers = new LinkedList<>();
        PriorityQueue<Long> mNumbers = new PriorityQueue<>(m, new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                return -Long.compare(o1,o2);
            }
        });
        for (int i = 0; i < m; i++) {//将第一列的前m个数收入囊中
            mNumbers.add(matrixA(i+1, 1));
        }
        for (int j = 2; j <= n; j++) {//从第二列到第n列
            //如果数在之中，则踢出当前最大值, 在合适的位置插入新的值，否则不理它
            //注意到每一列都是有序的，因此，可以先用二分搜索搜到位置
            //注意到这个搜索所需要的数组构造出来要花时间，不如解方程快，改成解方程
            long target = mNumbers.peek(); // 当前m位值
            double rowIndex = solveEquationForRowIndex(target, j, n);
            if (rowIndex<=1)
                continue; //全部都比当前m位值要大
            for (int i = 1; i <= (int)rowIndex; i++) {
                //发现问题了，linkedList不适合二分搜索，如果是ArrayList的话你就得复制，所以最优解是优先队列。
                mNumbers.remove();
                mNumbers.add(matrixA(i,j));
            }
        }
        return mNumbers.peek();
    }
    private static double solveEquationForRowIndex(long target, int colIndex, int n) { //solve rowIndex such that fun(rowIndex)==target
        double a = 1, b = 12345+colIndex, c = (long) colIndex *(colIndex-12345)-target;
        //注意到定义域内是单调递增的
        double delta = b*b-4*a*c;
        if (delta<0)
            return -1;
        return (Math.sqrt(delta)-b)/(2*a);
    }
    @Deprecated
    private static double solveEquationForRowIndex_bad(long target, int colIndex, int n) { //solve rowIndex such that fun(rowIndex)==target
        double Xn = 1+(n-1)/2;
        double fun = 0;
        double previous = Xn;
        while (Math.abs((fun = matrixA(Xn,colIndex))-target)>=0.001){
            Xn = Xn - fun/partialDerivative_rowIndex_matrixA(Xn, colIndex);
            if (Math.abs(previous-Xn)<=0.001)
                return -1; //那边只是想看是不是小于1.
            previous = Xn;
        }
//        System.out.printf("%.10f\n",Xn);
        return Xn;
        //大家好，我们不需要牛顿迭代法，这里牛顿迭代法卡死了，我也不知道为什么，可能求导数有问题
        //但是，这是个一元二次方程，为什么不用求根公式呢？

        //原来，有可能无解。 我们的target，可能不如这一列的最小值，如果是那样的话，当然我们跳过这一次就可以了。
    }
    @Deprecated
    private static double solveEquationForColIndex(long target, int rowIndex){ //solve colIndex such that fun(colIndex)==target
        double Xn = 0;
        double fun = 0;
        while (Math.abs((fun = matrixA(rowIndex,Xn))-target)>=0.001){
            Xn = Xn - fun/partialDerivative_colIndex_matrixA(rowIndex, Xn);
        }
//        System.out.printf("%.10f\n",Xn);
        return Xn;
    }
    //i 是行坐标， j是列坐标
    private static long matrixA(int rowIndex, int colIndex){ //按照数学的规范来，而不是垃圾数组从零开始规范。//经过分析，不会超过long
        return (long)rowIndex*rowIndex+ 12345L *rowIndex+ (long) colIndex *colIndex- 12345L *colIndex+ (long) rowIndex *colIndex;
    }
    private static double matrixA(double rowIndex, double colIndex){
        return rowIndex*rowIndex+ 12345*rowIndex+ colIndex *colIndex- 12345*colIndex+ rowIndex *colIndex;
    }
    private static double partialDerivative_colIndex_matrixA(double rowIndex, double colIndex){
        return colIndex*2-12345+ rowIndex;
    }
    private static double partialDerivative_rowIndex_matrixA(double rowIndex, double colIndex){
        return rowIndex*2+ 12345+ colIndex;
    }
}

