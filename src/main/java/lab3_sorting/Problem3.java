package lab3_sorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Arrays;
import java.util.InputMismatchException;

public class Problem3 {
    private static OJReaderProblem3 in = new OJReaderProblem3();
    public static void main(String[] args) {
        int T = in.nextInt();
        Integer[] arrayA;
        for (int i = 0; i < T; i++) {
            arrayA = in.nextIntegerArray();
            System.out.println(solve(arrayA));
        }
    }
//    static String solveNotBruteForce(Integer[] arrayA){
//
//    }
    static String solve(Integer[] arrayA) {
        StringBuilder result = new StringBuilder();
        final InsertionSortingMaster insertionSortingMaster = new InsertionSortingMaster();
        final SelectionSortingMaster selectionSortingMaster = new SelectionSortingMaster();

//        insertionSortingMaster.排序(arrayA, true); //12   4 2 3 1 6  //1 5 4 2 3 1 6
        insertionSortingMaster.排序(Arrays.copyOf(arrayA, arrayA.length), false); //12   4 2 3 1 6  //1 5 4 2 3 1 6
        selectionSortingMaster.排序(arrayA);
        for (int i = 0; i < arrayA.length; i++) {
            result.append(arrayA[i]).append(i== arrayA.length-1?"\n":" ");
        }
        result.append(insertionSortingMaster.getCount()<selectionSortingMaster.getCount()?"Insertion":"Selection")
                .append(" Sort wins!");//没有\n
        //test
//        System.out.println(insertionSortingMaster.getCount()+" "+selectionSortingMaster.getCount());
        return result.toString();
    }
}
class InsertionSortingMaster extends SortingMasterProblem3{
    private long count = 0;
    public <T extends Comparable<T>> void 排序(T[] arrayA, boolean 模拟排序) { //模拟排序会改变排序的逻辑
        for (int i = 0; i < arrayA.length; i++) {
            for (int j = i; j > 0; j--) {//j>0
                count++;
                if (第一个数大于第二个数(arrayA[j-1], arrayA[j])) {
                    count++;
                    if (!模拟排序)
                        交换(arrayA, j - 1, j);
                }
                else break;
            }
        }
    }

        @Override
    public <T extends Comparable<T>> void 排序(T[] arrayA) {
        排序(arrayA, false);
    }
    public long getCount() {
        return count;
    }
}
class SelectionSortingMaster extends SortingMasterProblem3{
    private long count = 0;
    @Override
    public <T extends Comparable<T>> void 排序(T[] arrayA) {
        int k;
        for (int i = 0; i < arrayA.length/* - 1*/; i++) {
            k = i;//必须记录最小值的位置，而不是最小值的值，否则无法交换
            for (int j = i+1; j < arrayA.length ; j++) {
                count++;
                if (第一个数大于第二个数(arrayA[k], arrayA[j])) {
                    k = j;
                }
            }
            交换(arrayA, i, k);
            count++;
        }
    }
    public long getCount() {
        return count;
    }
}
abstract class SortingMasterProblem3 {//这个大师没有什么属性，所以其实应该写成静态方法库，而不是一个类。但是我真的很想用继承和多态去表达排序，让排序进行重写
    //    public abstract <T extends Comparable<T>> void sort(List<T> collection);
    public abstract <T extends Comparable<T>> void 排序(T[] arrayA);

    protected <T extends Comparable<T>> void 交换(T[] arrayA, int i, int j) {
        T temp = arrayA[i];
        arrayA[i] = arrayA[j];
        arrayA[j] = temp;
    }
    public <T extends Comparable<T>> boolean 检查数组是否有序(T[] arrayA) {
        for (int i = 1; i < arrayA.length; i++)
            if (第一个数大于第二个数(arrayA[i-1], arrayA[i])) return false;
        return true;
    }
    protected <T extends Comparable<T>> boolean 第一个数小于第二个数(T t, T t1) {
        return t.compareTo(t1)<0;
    }
    protected <T extends Comparable<T>> boolean 第一个数大于第二个数(T t, T t1) {
        return t.compareTo(t1)>0;
    }
}
class OJReaderProblem3 extends StreamTokenizer {
    public OJReaderProblem3() {
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