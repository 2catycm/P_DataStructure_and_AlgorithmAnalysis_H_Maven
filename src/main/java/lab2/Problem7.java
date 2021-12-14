package lab2;

import java.util.*;

public class Problem7 {
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
    static int solveBruteForce(int[] arrayA, int k){
        int maxMedian = -1;
//        PriorityQueue<Integer> integers;
        for (int i = 0; i < arrayA.length; i++) {
            for (int j = i+k-1; j < arrayA.length; j++) {//i和j都要能取到
                maxMedian = Math.max(maxMedian, findMedian(arrayA, i, j));
            }
        }
        return maxMedian;
    }

    private static int findMedian(int[] arrayA, int i, int j) {
        return Arrays.stream(arrayA,i,j+1).sorted().toArray()[(j-i)/2];
    }


    static int solve(int[] arrayA, int k){
        int maxMedian = -1;

        OrderedArrayList<Integer> currentWindow;
        for (int i = 0; i < arrayA.length-k+1; i++) {
            //初始化窗口
            currentWindow = new OrderedArrayList<>();
            for (int j = 0; j < k; j++) {
                currentWindow.add(arrayA[j+i]); //j+i
            }
            Collections.sort(currentWindow);
            maxMedian = Math.max(maxMedian, currentWindow.getMedian());
            //        开始滑动窗口
            for (int j = i+k; j < arrayA.length; j++) {//i和j都要能取到
                currentWindow.orderedAdd(arrayA[j]);
                maxMedian = Math.max(maxMedian, currentWindow.getMedian());
            }
        }
        return maxMedian;
    }
}
//维护当前窗口，保证有序、可以得知中位数、
// 可以去除其中的某个元素、加入某个元素（通过BinarySearch实现）
class OrderedArrayListIncomplete<E extends Comparable<E>> extends ArrayList<E>{
    public boolean orderedRemove(E element){
//        throw new NotImplementedException();
        return false;
    }
    public void orderedAdd(E element){ //原本的add保留，不过增加一种新的add
        final int index = binarySearchForInsertion(element);
        this.add(index,element); //这个居然返回值是void，震惊了，为什么？
    }
    public E getMedian(){
        return this.get((this.size()-1)/2);
    }
    private int binarySearchForInsertion(E target){ //找到了
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
        return left;                      //找不到就返回一个合适的index
    }
}