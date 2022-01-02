package lab2_binary_search;

import java.util.Scanner;

public class Problem1 {
    private static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
//        int length = in.nextInt(); //n
        int[] arrayA = new int[in.nextInt()];
        for (int i = 0; i < arrayA.length; i++) {
            arrayA[i] = in.nextInt();
        }
        int caseCount = in.nextInt();
        for (int i = 0; i < caseCount; i++) {
            System.out.println(contains(arrayA, in.nextInt())?"YES":"NO");
        }
//        Collections.binarySearch()
    }

    private static boolean contains(int[] arrayA, int target) {
        int left = 0, right = arrayA.length, mid;//不取右边
        while (left<right){   //比如0,1 只有一个
            mid = left+ (right-left)/2;
            if (arrayA[mid]==target)
                return true;
            else if (arrayA[mid]<target)
                left = mid+1;    //比如，变成1
            else
                right = mid;  //注意， right不取
        }
        return false;
    }
}
