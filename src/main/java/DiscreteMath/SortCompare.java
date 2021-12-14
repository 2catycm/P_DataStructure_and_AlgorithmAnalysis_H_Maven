package DiscreteMath;


import util.algorithm.SortingHelper;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.Random;

public class SortCompare {
    public static void main(String[] args) {
        final var ints = new Integer[]{9, 8, 7, 6, 5, 4, 3, 2, 1};
        for(int i = 1;;i++){
            SortingHelper.knuthShuffle(ints);
//            shellSort(ints, Integer::compare);
//            hInsertionSort(ints, Integer::compare, 1);
            quickSort(ints, Integer::compare);
            System.out.println((float)compareCnt/i);
        }
    }
    private static int compareCnt = 0;
    public static <T> void shellSort(T[] array, Comparator<T> tComparator){
        //h = 3x+1 < array.length 是可以排的
//        for (int h = ((array.length-1)/3*3+1); h >=1 ; h/=3) {
//
//        }
        int q = 3;
//        int q = 4;
        for (int x = (array.length-1)/q, h; x >= 0 ; x--) {
            h = q*x+1;
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
                compareCnt++;
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
            compareCnt++;
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
                compareCnt++;
                if (tComparator.compare(array[i], pivot)<0) aux[start++] = array[i];
                else                                        aux[end--] = array[i];
        }
        aux[start] = pivot;
        System.arraycopy(aux, startInclusive, array, startInclusive, endExclusive-startInclusive);
        return start;
    }
}
