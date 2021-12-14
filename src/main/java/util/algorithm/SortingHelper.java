package util.algorithm;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import static util.algorithm.ShellSort.交换;

public final class SortingHelper {
    private SortingHelper() {}
    public static <T> void 交换(T[] arrayA, int i, int j) {
        T temp = arrayA[i];
        arrayA[i] = arrayA[j];
        arrayA[j] = temp;
    }
    public static <T extends Comparable<T>> boolean 检查数组是否有序(ArrayList<T> arrayA) {
        for (int i = 1; i < arrayA.size(); i++)
            if (arrayA.get(i-1).compareTo(arrayA.get(i))>0) return false;
        return true;
    }
    public static <T extends Comparable<T>> boolean 检查数组是否有序(T[] arrayA) {
        for (int i = 1; i < arrayA.length; i++)
            if (第一个数大于第二个数(arrayA[i-1], arrayA[i])) return false;
        return true;
    }
    public static <T extends Comparable<T>> boolean 第一个数小于第二个数(T t, T t1) {
        return t.compareTo(t1)<0;
    }
    public static <T extends Comparable<T>> boolean 第一个数大于第二个数(T t, T t1) {
        return t.compareTo(t1)>0;
    }

    //knuth shuffle 正确性分析： 公平性： n!种可能性都是等可能。
    //法1，摸牌理解法
    //引理1： 往n-1张中随机插入一张牌，可以视作array 的n-1个元素和一个新的元素， n-1个元素每一个都能随机与新的元素交换。
    //数学归纳： 第一次摸牌是随机的。
    //第k次摸牌如果是均匀的，那么第k+1次摸牌也是均匀的。
    //法2，Ann 组合法。
    //第一步，选定第一张牌是哪一张牌，第二步，选定第二张牌是哪一张牌。
    //映射到数组的交换操作上，
    public static <T extends Comparable<T>> void knuthShuffle(T[] arrayA){
        final Random random = new Random();int r;
        for (int i = 1; i < arrayA.length; i++) {
            r = random.nextInt(i+1);//i的位置也要取得到
            交换(arrayA,r,i);
        }
    }
    private static final Random random = new Random();
    public static  void knuthShuffle(int[] arrayA){
        int r;
        for (int i = 1; i < arrayA.length; i++) {
            r = random.nextInt(i+1);//i的位置也要取得到, 可以跟自己交换
            int temp = arrayA[r];
            arrayA[r] = arrayA[i];
            arrayA[i] = temp;
        }
    }
    public static void main(String[] args) {
        Integer[] a = {1,2,3,4,5,6,7,7,8};
        knuthShuffle(a);
        System.out.println(Arrays.toString(a));
    }
}
final class MergeSort{
    public static <T extends Comparable<T>> void 归并排序(T[] arrayA) {
        归并排序(arrayA, 0, arrayA.length);
    }

    public static <T extends Comparable<T>> void 归并排序(T[] arrayA, int startInclusive, int endExclusive) {
        final int length = endExclusive - startInclusive;
        if (length <=1)
            return;
        int mid = startInclusive+length/2;
        归并排序(arrayA, startInclusive, mid);
        归并排序(arrayA, mid, endExclusive);
        归并(arrayA,startInclusive, endExclusive, mid);
    }
    public static <T extends Comparable<T>> void 归并(T[] arraySrc, int startInclusive, int endExclusive, int mid){//mid是左子数组的Exclusive边界，右子数组的Inclusive边界。
        int lengthA = mid - startInclusive;
        int lengthB = endExclusive - mid;
//        T[] arrayA = new T[lengthA];
//        T[] arrayB = new T[lengthB];
        T[] arrayA = (T[]) Array.newInstance(arraySrc[0].getClass(), lengthA);
        T[] arrayB = (T[]) Array.newInstance(arraySrc[0].getClass(), lengthB);

        System.arraycopy(arraySrc,startInclusive, arrayA, 0, lengthA);
        System.arraycopy(arraySrc,mid, arrayB, 0, lengthB);
        int i,j;
        int k = startInclusive;
        for (i = 0, j = 0; i < arrayA.length && j < arrayB.length;) { //i指向arrayA还没有归并的最小值。j同理
//            comparison = arrayA[i] - arrayB[j];
            if (SortingHelper.第一个数大于第二个数(arrayA[i], arrayB[j])){
                arraySrc[k++] = arrayB[j];//B更小，消耗掉这个最小值
                j++; //最小值更新
            }else{
                arraySrc[k++] = arrayA[i];//可以认为A更小，相等也可以把它消耗掉
                i++;
            }
        }
        //某个数组已经被消耗完了。
        if (j==arrayB.length) //j消耗完了，i还没有
            for (; i < arrayA.length; i++) {
                arraySrc[k++] = arrayA[i];
            }
        else
            for (; j < arrayB.length; j++) {
                arraySrc[k++] = arrayB[j];
            }
    }
}
final class QuickSort{
    private QuickSort(){}
    private static Random random = new Random();
    public static <T> void quickSort(T[] array, Comparator<T> tComparator){
        T[] aux = (T[]) Array.newInstance(array[0].getClass(), array.length);
        quickSort(array, 0, array.length, tComparator, aux);
    }
    public static <T> void quickSort(T[] array, int startInclusive, int endExclusive, Comparator<T> tComparator, T[] aux){
        if (endExclusive<=startInclusive)
            return;
        int i = partition(array, startInclusive, endExclusive, tComparator, aux);//i是pivot的位置
        quickSort(array, startInclusive, i, tComparator, aux);
        quickSort(array, i+1, endExclusive, tComparator, aux);
    }
    public static int getRandomPivotIndex(int startInclusive, int endExclusive){
        int[] temp = new int[5];
        for (int i = 0; i < 5; i++) {
            temp[i] = startInclusive+random.nextInt(endExclusive-startInclusive);
        }
        insertionSort(temp);
        int a = temp[2];
        temp = null;
        return a;
    }
    public static void insertionSort(int[] array){
        for (int i = 1; i < array.length; i++) {//新插入的纸牌的指针是i
            for (int j = i; j > 0 ; j--) {
                if (array[j]<array[j-1])//新插入的i比较小，往前走
                    交换(array,j,j-1);
                else break;
            }
        }
    }
    public static void 交换(int[] arrayA, int i, int j) {
        int temp = arrayA[i];
        arrayA[i] = arrayA[j];
        arrayA[j] = temp;
    }
    public static <T> int partition(T[] array, int startInclusive, int endExclusive, Comparator<T> tComparator, T[] aux) {
        int temp;
        for (temp = startInclusive+1; temp < endExclusive; temp++) {
            if (tComparator.compare(array[temp-1], array[temp])!=0)
                break;
        }
        if (temp == endExclusive)
            return startInclusive+(endExclusive-startInclusive)/2;
        final int randomPivotIndex = getRandomPivotIndex(startInclusive, endExclusive);
        int start = startInclusive, end = endExclusive-1;
        final T pivot = array[randomPivotIndex];
        for (int i = startInclusive; i < endExclusive; i++) {
            if (i!=randomPivotIndex)
                if (tComparator.compare(array[i], pivot)<0) aux[start++] = array[i];
                else                                        aux[end--] = array[i];
        }
        aux[start] = pivot;
        System.arraycopy(aux, startInclusive, array, startInclusive, endExclusive-startInclusive);
        return start;
    }

    public static void main(String[] args) {
        while (true) {
            int n = 1000000;
            int size = 10000;
            Integer[] array = new Integer[n];
//        for (Integer e:array)
//            e = random.nextInt(10000); //wrong，没有引用
            for (int i = 0; i < n; i++) {
                array[i] = random.nextInt(size);
//                array[i] = 1;
            }
            quickSort(array, Integer::compare);
            if (!SortingHelper.检查数组是否有序(array))
                System.err.printf("wrong at Array: %s\n", Arrays.toString(array));
        }
    }
}
final class ShellSort{ //don‘t extend the Sorting static method library, because if I want its static method, I just use it, I do not extend it.
    private ShellSort(){}
    public static <T> void shellSort(T[] array, Comparator<T> tComparator){
        //h = 3x+1 < array.length 是可以排的
//        for (int h = ((array.length-1)/3*3+1); h >=1 ; h/=3) {
//
//        }
        for (int x = (array.length-1)/3, h; x >= 0 ; x--) {
            h = 3*x+1;
            hInsertionSort(array, tComparator, h);
        }
        //h = 9×4^i-9×2^i+1 < array.length


    }
    public static <T> void hInsertionSort(T[] array, Comparator<T> tComparator, int h){
//        for (int i = 0; i <= array.length-h; i++) {
//            insertionSort(array, tComparator, h, i);
//        }
        for (int i = 0; i < h; i++) {
            insertionSort(array,tComparator, h, i);
        }
    }
    public static <T> void insertionSort(T[] array, Comparator<T> tComparator, int gap, int start){
        for (int i = start+gap; i < array.length; i+=gap) {//新插入的纸牌的指针是i
            for (int j = i; j > start ; j-=gap) {
                final int comparison = tComparator.compare(array[j], array[j-gap]);
                if (comparison<0)//新插入的i比较小，往前走
                    交换(array,j,j-gap);
                else break;
            }
        }
    }
    public static <T> void 交换(T[] arrayA, int i, int j) {
        T temp = arrayA[i];
        arrayA[i] = arrayA[j];
        arrayA[j] = temp;
    }

    public static void main(String[] args) {
        Integer[] test = {3,2 ,5,1,2};
        ShellSort.shellSort(test, Integer::compare);
        System.out.println(Arrays.toString(test));
    }
}
final class BubbleSort{

}