package lab5_stack_queue;
//第一步的测试数据：
//9 1
//5 1 2 3 1 2 1 2 4
//1 8

public class Problem7 {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        int n = in.nextInt();
        int q = in.nextInt();
        int[] arrayH = in.nextIntArray(n);
        for (int i = 0; i < q; i++) {
            int l = in.nextInt();
            int r = in.nextInt();
//            out.println(bruteForceSolve(arrayH, l, r));
            out.println(solve(arrayH, l, r));
        }
    }
    private static boolean isFirstTime = true;
    private static int[] asLeftCount;
    private static IntPrefixSumTable asLeftCountSum;
    private static int[] asRightCount;
    private static IntPrefixSumTable asRightCountSum;
    private static IntDoublingSTTable stTable;
    static long solve(int[] arrayH, int l, int r) {
        if (isFirstTime)
            init(arrayH);
//        out.printIntArray(asLeftCount);
//        out.printIntArray(asRightCount);
//        final var indexOfMax = stTable.indexOfMax(l, r + 1);
        l--;r--;//从0开始是基本共识，国际社会不能为违背这一共识。
        final var indexOfMax = stTable.indexOfMaxAmong(l, r+1);
        long pairCount = 0;
//        for (int i = l; i < indexOfMax; i++) {
//            pairCount+=asLeftCount[i];
//        }
//        for (int i = indexOfMax+1; i <= r; i++) {
////            pairCount+=asLeftCount[i];//写错了
//            pairCount+=asRightCount[i];
//        }
        pairCount+=asLeftCountSum.sumAmong(l, indexOfMax);
        pairCount+=asRightCountSum.sumAmong(indexOfMax+1, r+1);
        return pairCount;
    }

    private static void init(int[] arrayH) {
        isFirstTime = false;
        //ST table
        stTable = new IntDoublingSTTable(arrayH);
        asLeftCount = new int[arrayH.length];
        asRightCount = new int[arrayH.length];
        //mono stack
        IntArrayStack rightCountStack = new IntArrayStack(arrayH.length);
        for (int i = 0; i < arrayH.length; i++) {
            //right
            int temp = -1;//最后一个被打败的元素
            //7 4 4 3 2 4,  4打败3 2， 打败4，但是不再往前走了
            //不，不会有连续的4，因为是严格单调递减的，所以左边必定还会更大，所以继续走。
            while (!rightCountStack.栈空()&&arrayH[rightCountStack.栈顶()]<=arrayH[i]){
                temp = rightCountStack.出栈();
                asRightCount[i]++;
            }
            if (!rightCountStack.栈空()&&(temp==-1||arrayH[temp]!=arrayH[i]))//弹掉不是因为相等的情况
                asRightCount[i]++;
            rightCountStack.入栈(i);
        }
        IntArrayStack leftCountStack = new IntArrayStack(arrayH.length);
        for (int i = arrayH.length-1; i >= 0; i--) {
            int temp = -1;
            while (!leftCountStack.栈空()&&arrayH[leftCountStack.栈顶()]<=arrayH[i]){
                temp = leftCountStack.出栈();
                asLeftCount[i]++;
            }
            if (!leftCountStack.栈空()&&(temp==-1||arrayH[temp]!=arrayH[i]))
                asLeftCount[i]++;
            leftCountStack.入栈(i);
        }
        asLeftCountSum = new IntPrefixSumTable(asLeftCount);
        asRightCountSum = new IntPrefixSumTable(asRightCount);
    }

    static long bruteForceSolve(int[] arrayH, int l, int r) {
        long pairCount = 0;l--;r--;
        for (int i = l; i <= r; i++) {
            step2:
            for (int j = i+1; j <= r; j++) {
                final var happyHeight = Math.min(arrayH[i], arrayH[j]);
                for (int k = i+1; k < j; k++) {
//                    if (arrayH[k]>happyHeight)
                    if (arrayH[k]>=happyHeight)
                        continue step2;
                }
                pairCount++;
//                System.out.printf("(i, j) = (%d,%d)\n", i,j);
//                System.out.printf("(h[i], h[j]) = (%d,%d)\n\n", arrayH[i],arrayH[j]);
            }
        }
        return pairCount;
    }
}
//#include "OJReader.java"
//#include "OJWriter.java"
//#include "非泛型队列与栈包.java"
//#include "倍增st表和前缀和.java"
//#include "IntPrefixSumTable.java"