package util.algorithm;

import java.lang.reflect.Array;
import java.util.Arrays;

public abstract class SortingMaster {//这个大师没有什么属性，所以其实应该写成静态方法库，而不是一个类。但是我真的很想用继承和多态去表达排序，让排序进行重写
//    public abstract <T extends Comparable<T>> void sort(List<T> collection);
    public abstract <T extends Comparable<T>> void 排序(T[] arrayA);

    protected static <T extends Comparable<T>> void 交换(T[] arrayA, int i, int j) {
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
class MergeSortMaster extends SortingMaster{
    @Override
    public <T extends Comparable<T>> void 排序(T[] arrayA) {
        排序(arrayA, 0, arrayA.length);
    }

    public <T extends Comparable<T>> void 排序(T[] arrayA, int startInclusive, int endExclusive) {
        final int length = endExclusive - startInclusive;
        if (length <=1)
            return;
        int mid = startInclusive+length/2;
        排序(arrayA, startInclusive, mid);
        排序(arrayA, mid, endExclusive);
        归并(arrayA,startInclusive, endExclusive, mid);
    }
    public <T extends Comparable<T>> void 归并(T[] arraySrc, int startInclusive, int endExclusive, int mid){//mid是左子数组的Exclusive边界，右子数组的Inclusive边界。
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

    public static void main(String[] args) {
        Integer[] 测试 = {2,1,4,5,2,12,100,99,33,44,33};
        final MergeSortMaster mergeSortMaster = new MergeSortMaster();
        mergeSortMaster.排序(测试);
        System.out.println(mergeSortMaster.检查数组是否有序(测试));
        System.out.println(Arrays.toString(测试));
    }
}