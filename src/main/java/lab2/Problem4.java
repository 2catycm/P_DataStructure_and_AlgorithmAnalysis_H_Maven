package lab2;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Scanner;

public class Problem4 {
    private static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        int[] arrayA = new int[in.nextInt()];
        int max = -1, min = Integer.MAX_VALUE;
        for (int i = 0; i < arrayA.length; i++) {
            final int i1 = in.nextInt();
            arrayA[i] = i1;
            max = Math.max(max, i1);
            min = Math.min(min, i1);
        }
        System.out.println(solve(arrayA, max, min));
    }
    // the algorithm to be test
    static int solve(int[] arrayA, int max, int min) {
        int result = 0;
        //4 1 2 7 1023
        int max_exponent = (int) (Math.ceil(Math.log(max)/Math.log(2))+1); //如lg7+2=3
        int min_exponent = (int) (Math.floor(Math.log(min)/Math.log(2))+1); //如lg4+1=3
        for (int j = min_exponent, target=1<<min_exponent; j <= max_exponent; j++, target<<=1){
//            result+=solve(arrayA, target);
            result+=solve_by_binary_search(arrayA, target);
        }

        //小知识点
        //判断一个数是不是二的多少次方 ，应该使用 n&(n-1)==0
        //对于这题来说，二分搜索的话没什么用
        return result;
    }

    private static int solve_by_binary_search(int[] arrayA, int target) {
        int result = 0;
        for (int i = 0; i < arrayA.length; i++) {
            final int i1 = arrayA[i];
            if (i1>target>>1) break;
            final int newTarget = target - i1;
            int index = binarySearch(arrayA, i+1, newTarget);
            if (index==-1) continue;
            result++;//起码那一个是
            for (int j = index+1; j < arrayA.length&&arrayA[j]==newTarget; j++) {
                result++;
            }
            for (int j = index-1; j >= i+1&&arrayA[j]==newTarget; j--) {
                result++;
            }
        }
        return result;
    }

    private static int binarySearch(int[] arrayA, int startFrom, int target) {
        int left = startFrom, right = arrayA.length, mid;//不取右边
        while (left<right){   //比如0,1 只有一个
            mid = left+ (right-left)/2;
            if (arrayA[mid]==target)
                return mid;
            else if (arrayA[mid]<target)
                left = mid+1;    //比如，变成1
            else
                right = mid;  //注意， right不取
        }
        return -1;
    }

    private static int solve(int[] arrayA, int target) {
        int result = 0;
        final HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < arrayA.length; i++) {
            final int key = arrayA[i];
            if (hashMap.containsKey(target-key))
                result+=hashMap.get(target-key);
            if (!hashMap.containsKey(key))
                hashMap.put(key,1);
            else
                hashMap.put(key, hashMap.get(key)+1);
        }
        return result;
    }
}
