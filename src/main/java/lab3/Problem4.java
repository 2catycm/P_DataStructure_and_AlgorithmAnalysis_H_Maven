package lab3;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Problem4 {
    private static OJReaderProblem4 in = new OJReaderProblem4();
    private static PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    public static void main(String[] args) {
        Integer[] arrayA = in.nextIntegerArray();
        randomSolve(arrayA);
    }

    private static void randomSolve(Integer[] arrayA) {
//        final List<Integer> list = Arrays.asList(arrayA);
        do {
//            Collections.shuffle(list);
            SortingProblem4.knuthShuffle(arrayA);
        }while (hasAverage(arrayA));
        for(Integer e:arrayA)
            printWriter.println(e);
        printWriter.close();
    }

    private static boolean hasAverage(Integer[] arrayA) {
        final List<Integer> list = Arrays.asList(arrayA);
        return hasAverage(list);
    }
    private static boolean hasAverage(List<Integer> list) {
        for (int i = 1; i < list.size()-1; i++) {
            if (list.get(i)*2==(list.get(i-1)+list.get(i+1)))
                return true;
        }
        if (list.get(0)*2==(list.get(list.size()-1)+list.get(1)))
            return true;
        return list.get(list.size() - 1)*2 == (list.get(0) + list.get(list.size() - 2));
    }

    private static void solve(Integer[] arrayA) {
        SortingProblem4.归并排序(arrayA);
        for (int i = 0; i < arrayA.length/2; i++) {
            printWriter.println(arrayA[i]);
            printWriter.println(arrayA[arrayA.length-i-1]);
        }
        printWriter.close();
    }
}
final class SortingProblem4 {
    public static <T extends Comparable<T>> void knuthShuffle(T[] arrayA){
        final Random random = new Random();int r;
        for (int i = 1; i < arrayA.length; i++) {
            r = random.nextInt(i+1);//i的位置也要取得到
            交换(arrayA,r,i);
        }
    }
    private SortingProblem4() {}
    public static <T extends Comparable<T>> void 交换(T[] arrayA, int i, int j) {
        T temp = arrayA[i];
        arrayA[i] = arrayA[j];
        arrayA[j] = temp;
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
            if (第一个数大于第二个数(arrayA[i], arrayB[j])){
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

class OJReaderProblem4 extends StreamTokenizer {
    public OJReaderProblem4() {
        super(new BufferedReader(new InputStreamReader(System.in)));
    }
    public int nextInt(){
        try {
            super.nextToken();
        } catch (IOException e) {
            throw new InputMismatchException("");
        }
        return (int) super.nval;
    }
    public int[] nextIntArray(){
        return nextIntArray(this.nextInt());
    }
    public int[] nextIntArray(int length){
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = this.nextInt();
        }
        return array;
    }

    public Integer[] nextIntegerArray() {
        return nextIntegerArray(this.nextInt());
    }
    public Integer[] nextIntegerArray(int length) {
        Integer[] array = new Integer[length];
        for (int i = 0; i < length; i++) {
            array[i] = this.nextInt();
        }
        return array;
    }
}