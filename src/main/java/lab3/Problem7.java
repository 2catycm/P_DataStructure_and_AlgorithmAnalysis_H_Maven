package lab3;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Random;
/*问题数据：3
0 1
9 1
2 1
1 3*/
public class Problem7 {
    private static OJReaderProblem7 in = new OJReaderProblem7();
    public static void main(String[] args) {
        int n  = in.nextInt();
        int x1 = in.nextInt();
        int x2 = in.nextInt();
        Line2D[] line2Ds = new Line2D[n];
        for (int i = 0; i < n; i++) {
            line2Ds[i] = new Line2D(in.nextInt(), in.nextInt());
        }
        System.out.println(solve(line2Ds, x1, x2) ? "YES" : "NO");
    }
    static boolean solveBruteForce(Line2D[] line2Ds, int x1, int x2){
        for (int i = 0; i < line2Ds.length; i++) {
            for (int j = i+1; j < line2Ds.length; j++) {
                final double v = line2Ds[i].intersectWith(line2Ds[j]);
                if((x1<v&&v<x2))
                    return true;
            }
        }
        return false;
    }
    static boolean solve(Line2D[] line2Ds, int x1, int x2) {
        //按照x1的值排好序。 这一次尝试手写一个希尔排序，应该很快。
//        ShellSort.shellSort(line2Ds, ((o1, o2) -> Long.compare(o1.valueAt(x1), o2.valueAt(x1))));
//        ShellSort.shellSort(line2Ds, (Comparator.comparingLong(o -> o.valueAt(x1))));
//        Arrays.sort(line2Ds, (Comparator.comparingLong(o -> o.valueAt(x1))));
//        QuickSort.quickSort(line2Ds, (((o1, o2) -> {
//            final int comparison = Long.compare(o1.valueAt(x1), o2.valueAt(x1));
//            return comparison ==0?Long.compare(o1.valueAt(x2), o2.valueAt(x2)):comparison;
//        })));
        QuickSort.quickSort(line2Ds, ((Comparator.comparingLong((Line2D o) -> o.valueAt(x1)).thenComparingLong(o -> o.valueAt(x2)))));

        //按照x1顺序(y值从小到大)遍历，看看x2的值有无逆序
        for (int i = 1; i < line2Ds.length; i++) {
            if (line2Ds[i].valueAt(x2)<line2Ds[i-1].valueAt(x2) /*&& line2Ds[i].valueAt(x1)!=line2Ds[i-1].valueAt(x1)*/)//=的情况我们不管，不会都等于的
                return true;
        }
        return false;
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
}
class Line2D{
    private final int k,b;
    public Line2D(int k, int b) {
        this.k = k;
        this.b = b;
    }
    public long valueAt(int x){
        return (long) k*x+b;
    }
    public int getK() {
        return k;
    }

    public int getB() {
        return b;
    }
    public double intersectWith(Line2D other){//返回相交的x位置
        return ((double) (other.b-b)) /(k-other.k);
    }
    @Override
    public String toString() {
        return "Line2D{" +
                "k=" + k +
                ", b=" + b +
                '}';
    }
}
class OJReaderProblem7 extends StreamTokenizer {
    public OJReaderProblem7() {
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