package lab2;

import java.util.Arrays;
import java.util.Scanner;

public class Problem5 {
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        int n = in.nextInt(), T = in.nextInt();
        int[] arrayA = new int[n];
        int[] arrayB = new int[n];
        for (int i = 0; i < n; i++) {
            arrayA[i] = in.nextInt();
        }
        for (int i = 0; i < n; i++) {
            arrayB[i] = in.nextInt();
        }
        for (int i = 0; i < T; i++) {
            int lowerBound = in.nextInt();
            int upperBound = in.nextInt();
            System.out.println(solve_only_merge(arrayA, arrayB, lowerBound, upperBound));
        }
    }


    //O(n) 过不了
    static int solve_only_merge(int[] arrayA, int[] arrayB, int lowerBound, int upperBound) {
        lowerBound--;upperBound--;
//        Arrays.stream(arrayA).filter(i -> lowerBound <= i && i <= upperBound)
//        Arrays.stream(arrayB).filter(i -> lowerBound <= i && i <= upperBound);
//        System.out.println(Arrays.toString(Arrays.stream(arrayA, lowerBound, upperBound).toArray()));
        int k = 0; //mergedArray当前未赋值的最小索引
//        int[] mergedArray = new int[2 * (upperBound - lowerBound + 1)];
        int i,j;//不变量： i和j 分别是arrayA、arrayB中还没有被mergedArray收录的 最小值的索引
        for (i = lowerBound, j = lowerBound; i <= upperBound&& j <= upperBound; ) {
            if (arrayA[i]<arrayB[j]){
//                mergedArray[k++] = arrayA[i];
                if ((k++)==upperBound-lowerBound)
                    return arrayA[i];
                i++;
            }else {
//                mergedArray[k++] = arrayB[j];
                if ((k++)==upperBound-lowerBound)
                    return arrayB[j];
                j++;
            }
        }
        //比如，i已经做完了，i++触发了for的退出. 那么j就没做完，j是等待被做的。
//        for (int l = Math.min(i,j); l < upperBound+1; l++)
        if (i>j)
            for (int l = j; l <= upperBound; l++)
//                mergedArray[k++] = arrayB[l];
                if ((k++)==upperBound-lowerBound)
                    return arrayB[l];
/*else*/ if (i<j)
            for (int l = i; l <= upperBound; l++)
//                mergedArray[k++] = arrayA[l];
                if ((k++)==upperBound-lowerBound)
                    return arrayA[l];
        /*else*/
            throw new ArrayIndexOutOfBoundsException("i==j情况出现了");

//        System.out.println(Arrays.toString(mergedArray));

//        return mergedArray[upperBound-lowerBound];
    }
}
