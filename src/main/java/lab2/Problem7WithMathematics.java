package lab2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

//expected:597, actually:511
//        at Array:[348, 970, 642, 32, 500, 511, 930, 597, 121, 447]
//结论有误！ 597作为右边的一位

//2和3的时候是恒正确的。
public class Problem7WithMathematics {
    static int offset = 2;
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
        offset = k%2==0?k:Math.max(k-2,1);
        int maxMedian = -1;
        //初始化窗口
        OrderedArrayList<Integer> currentWindow;
        for (int i = k; i < Math.min(k+offset, arrayA.length+1); i++) { //数学证明，窗口长度只能是k或者k+1
            currentWindow = new OrderedArrayList<>();
            for (int j = 0; j < i; j++) {
                currentWindow.add(arrayA[j]); //j+i
            }
            Collections.sort(currentWindow);
            maxMedian = Math.max(maxMedian, currentWindow.getMedian());//第一种情况
            //下面开始滑动定长为i的窗口。
            for (int j = i; j < arrayA.length; j++) {//j是currentWindow的尾巴
                currentWindow.orderedAdd(arrayA[j]);
                currentWindow.orderedRemove(arrayA[j-i]);
                maxMedian = Math.max(maxMedian, currentWindow.getMedian());
            }
        }
        return maxMedian;
    }
}
//维护当前窗口，保证有序、可以得知中位数、
// 可以去除其中的某个元素、加入某个元素（通过BinarySearch实现）
class OrderedArrayList<E extends Comparable<E>> extends ArrayList<E> {
    public boolean orderedRemove(E element){
        final int index = binarySearch(element, false);
        if (index==-1)return false;
        this.remove(index);
        return true;
    }
    public void orderedAdd(E element){ //原本的add保留，不过增加一种新的add
        final int index = binarySearch(element, true);
        this.add(index,element); //这个居然返回值是void，震惊了，为什么？
    }
    public E getMedian(){
        return this.get((this.size()-1)/2);
    }
    private int binarySearch(E target, boolean forInsertion){ //找到了
        int left = 0, right = size(), mid;//不取右边
        while (left < right) {
            mid = left + (right - left) / 2;
            final int comparison = this.get(mid).compareTo(target);
            if (comparison == 0)
                return mid;             //找到了就返回任何一个index（不需要区分第一个还是最后一个）
            else if (comparison < 0)
                left = mid + 1;
            else
                right = mid;
        }
        return forInsertion?left:-1;                      //找不到就返回一个合适的index
    }
}