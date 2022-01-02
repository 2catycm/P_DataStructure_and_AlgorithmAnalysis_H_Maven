package lab3_sorting;

import java.io.BufferedInputStream;
import java.util.Scanner;

//易错点： 逆序对可能有n^2个！！
public class Problem1 {
    public static void main(String[] args) {
//        QReader in = new QReader();
        Scanner in = new Scanner(new BufferedInputStream(System.in));
        int T = in.nextInt(), n, m;
        int[] arrayA, arrayB;
        for (int i = 0; i < T; i++) {
            n = in.nextInt(); m = in.nextInt();
            arrayA = new int[n]; arrayB = new int[m];
            for (int j = 0; j < n; j++) {
                arrayA[j] = in.nextInt();
            }
            for (int j = 0; j < m; j++) {
                arrayB[j] = in.nextInt();
            }
            System.out.println(solve(arrayA,arrayB));
        }
    }

    //目标： merge 排序， 每次选择最小的数到数列
    //同时，获得逆序数
    //实例：   1 4 9        2 4 6
    //逆序数的计算，    arrayA的每一个元素， 都要与arrayB的每一个元素比，如果arrayA的元素更大，就发现了逆序对。
    // 9比6大，也比2和4大，因此，每次发现逆序对，需要把以前的也算入吗？
    //不，如果是9一直在等待能够打地过自己的对手，那么会一直加。
    //是这样的，4打不过某个数了（4也打不过4），才让9出战的。
    //4的战绩是1，所以9出战后继承了4的战绩1，同时打下了新的对手。
    //但是9继承4的战绩，依然可以被继承
    private static String solve(int[] arrayA, int[] arrayB) {
        StringBuilder result = new StringBuilder();
//        int inversionCount = 0, previousICount = 0;
        long inversionCount = 0, previousICount = 0; //易错点： 逆序对可能有n^2个！！
        int comparison;
        int i,j;
        for (i = 0, j = 0; i < arrayA.length && j < arrayB.length;) { //i指向arrayA还没有归并的最小值。j同理
            comparison = arrayA[i] - arrayB[j];
            if (comparison>0){
                previousICount++;//符合逆序对的情况
                result.append(arrayB[j]).append(" ");//B更小，消耗掉这个最小值
                j++; //最小值更新
            }else{ //不符合逆序对的情况
                result.append(arrayA[i]).append(" ");//可以认为A更小，相等也可以把它消耗掉
                i++;
                inversionCount+=previousICount;//战绩继承
//                previousICount = 0; //不会清零
            }
        }
        //某个数组已经被消耗完了。
        if (j==arrayB.length) //j消耗完了，i还没有
            for (; i < arrayA.length; i++) {
                inversionCount+=previousICount;
                result.append(arrayA[i]).append(" ");
            }
        else
            for (; j < arrayB.length; j++) {
//                inversionCount+=previousICount;
                result.append(arrayB[j]).append(" ");
            }
        return inversionCount + "\n" + result;
    }
}
