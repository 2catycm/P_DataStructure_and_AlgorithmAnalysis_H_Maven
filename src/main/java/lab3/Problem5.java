package lab3;

import java.io.*;
import java.lang.reflect.Array;
import java.util.InputMismatchException;
import java.util.Random;

//题意理解错误，以为可以随便交换
public class Problem5 {
    private static OJReaderProblem5 in = new OJReaderProblem5();
    public static void main(String[] args) {
//        final Integer[] ints = in.nextIntegerArray();
//        System.out.println(solveByUsingMinimum(ints));

        final int[] ints = in.nextIntArray();
        System.out.println(solveByMergeSort(ints));

//        badSolve(ints);
        //语法: 复制array必须已经初始化好
//        int[] src = {1, 23,4};
//        int[] temp;
//        System.arraycopy(src, 0, temp,0, 3);
    }
    //有问题。5 6 4 2 3 5
    static long solveByMergeSort(int[] arrayA) {
        long cost = 0;
        int[] temp = new int[arrayA.length];
//        System.arraycopy(arrayA, 0, temp, 0, arrayA.length);
        for (int i = 1; i < arrayA.length; i<<=1) {//一开始merge 1个成两个
            int j;
            for (j = 0; j < arrayA.length; j+=i<<1) {//j是头，只要头没有超过数组尾巴，就可以接受
                cost+= mergeReversePairNew(arrayA, temp, j, j+i, Math.min(arrayA.length, j+(i<<1)));  //移位的优先级不高！j+i<<1是错的
            }
//            cost+=mergeReversePair(arrayA,temp, j-i<<2, j-i, arrayA.length);//有可能重复计算吗？比如
        }
        return cost;
    }
    static long mergeReversePairNew(int[] array, int[] temp, int start, int mid, int end){//mid, end 都是左边取不到，右边取得到的约定。
        long inversionCount = 0, previousICount = 0;
        //这个方法以结果数组为视角进行叙事，逻辑与之前有所不同的。
        System.arraycopy(array, start, temp,start, end-start);
        int i = start, j = mid;//i是前半部分，j是后半部分，需要统计逆序对。
        for (int k = start; k < end; k++) {
            if (i>=mid)         array[k] = temp[j++];//i被耗尽了，直接取j好了， 再也没有打得过j的i了。
            else if (j>=end)    {
                array[k] = temp[i++];
                inversionCount+=previousICount;//j里面一个能打的都没有，全被我们i打光了。
                //现在我们的i继承了前面每一个i战士的光荣战绩。
            }
            else if (temp[i]>temp[j]){
//                previousICount++;//符合逆序对情况
                previousICount+= temp[j];//我们把被我们杀死的j的头颅记下战功。
                array[k] = temp[j++]; //j更小，优先消耗j
            }
            else{
                array[k] = temp[i++];
                inversionCount+=previousICount;//战绩继承
            }
        }
        return inversionCount;
//        return inversionCount*array[start];
    }
    /*static long mergeReversePair(int[] array, int[] temp, int start, int mid, int end){//mid, end 都是左边取不到，右边取得到的约定。
        //边界检查
*//*        if (mid>array.length)
            return 0;
        if (end>array.length)
            return mergeReversePair(array, temp, start, mid, array.length);*//*
        //这是以两个数组的视角叙事的：
*//*        int k = 0;
        for (int i = 0, j = mid; i < mid && j<end; i++, j++) {

        }*//*
        long inversionCount = 0, previousICount = 0;
        //这个方法以结果数组为视角进行叙事，逻辑与之前有所不同的。
        System.arraycopy(array, start, temp,start, end-start);
        int i = start, j = mid;//i是前半部分，j是后半部分，需要统计逆序对。
        for (int k = start; k < end; k++) {
            if (i>=mid)         array[k] = temp[j++];//i被耗尽了，直接取j好了， 再也没有打得过j的i了。
            else if (j>=end)    {
                array[k] = temp[i++];
                inversionCount+=previousICount;//j里面一个能打的都没有，全被我们i打光了。
                //现在我们的i继承了前面每一个i战士的光荣战绩。
            }
            else if (temp[i]>temp[j]){
                previousICount++;//符合逆序对情况
//                previousICount+= temp[j];//我们把被我们杀死的j的头颅记下战功。
                array[k] = temp[j++]; //j更小，优先消耗j
            }
            else{
                array[k] = temp[i++];
                inversionCount+=previousICount;//战绩继承
            }
        }
//        return inversionCount;
        return inversionCount*array[start];
    }*/
//    static int solveReversePair(Integer[] ints) {//complete binary tree
//        int sum = 0;
//        //性质分析：
//        //1.逆序对可以理解成，每次增加一个数，这个数比前面的多少个数小，就加多少个。
//        //2.如果前面的数是有序的，就可以二分查找位置来获得，但是移动需要成本。
//        //3.了解一下二叉堆，或许有用
//
//        return sum;
//    }
//    static int solveByUsingMinimum(Integer[] ints){
//        //获得顺序信息
//        IndexedInteger[] ordered = IntStream.range(0, ints.length).mapToObj(i -> new IndexedInteger(ints[i], i)).toArray(IndexedInteger[]::new);
//        SortingProblem5.归并排序(ordered);
//        int cost = 0;
//        //根据信息，使用最小值操作原数组。
//        for (int i = 0; i < ordered.length; i++) {
//            if (ordered[i].getData()==ints[i])
//                continue;
//            //寻找not in place的最大值
//            for (int j = ordered.length-1; j >= 0; j--) {
//                if (ordered[j].getData()==ints[j])
//                    continue;
//                //用最小值的代价，使之in place
//                //对原本的ints进行操作
//
//                int temp = ints[ordered[j].getOrder()];
//                ints[ordered[j].getOrder()] = ints[ordered[i].getOrder()];
//                ints[ordered[i].getOrder()] = temp;
//                cost+=ordered[i].getData();
//                if (ordered[i].getData()==ints[i])
//                    break;
//            }
//        }
//        return cost;
//    }
    static int bruteForceReversePair(Integer[] ints) {
        int sum = 0;
        for (int i = 0; i < ints.length; i++) {
            for (int j = i+1; j < ints.length; j++) {
                if (ints[j]<ints[i])
                    sum+=ints[j];
            }
        }
        return sum;
    }
//    static int solveWrong(Integer[] ints) {
//        IndexedInteger[] copied = IntStream.range(0, ints.length).mapToObj(i -> new IndexedInteger(ints[i], i)).toArray(IndexedInteger[]::new);
//        SortingProblem5.归并排序(copied);
////        System.out.println(Arrays.toString(copied));
//        int sum = 0;
//        for (int i = 0; i < copied.length; i++) {
////            int comparison = copied[i].getData() - ;
//            if (copied[i].getData()==ints[i])
//                continue;
//            //不一样，copied的是准确的，我们要找到 copied[i]的原来位置，交换
//            sum+=copied[i].getData();
//            SortingProblem5.交换(ints,copied[i].getOrder(),i);
//        }
//        return sum;
//    }

//    private static void badSolve(Integer[] ints){
//        final int max = Arrays.stream(ints).max().getAsInt();
//        final int sum = Arrays.stream(ints).sum();
//        printWriter.println(sum-max);
//        printWriter.close();
//    }
//分析：
//selection sort与数据无关，一定要n-1次交换
//证明：归并排序、快速排序、插入排序需要的交换数都大于选择排序
//而且，它们也必须对每一个元素进行一次交换。
}
class IndexedInteger implements Comparable<IndexedInteger>{
    private int data;
    private int order;
//    private ArrayList<Integer> orderList = new ArrayList<>();
    public IndexedInteger(int data, int order) {
        this.data = data;
        this.order = order;
//        orderList.add(order);
    }

//    public ArrayList<Integer> getOrderList() {
//        return orderList;
//    }

    public void setData(int data) {
        this.data = data;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getData() {
        return data;
    }
    public int getOrder() {
        return order;
    }
    @Override
    public int compareTo(IndexedInteger o) {
        return Integer.compare(data,o.data);
    }
    @Override
    public String toString() {
        return "IndexedInteger{" +
                "data=" + data +
                ", order=" + order +
                '}';
    }
}
final class SortingProblem5 {
    public static <T extends Comparable<T>> void knuthShuffle(T[] arrayA){
        final Random random = new Random();int r;
        for (int i = 1; i < arrayA.length; i++) {
            r = random.nextInt(i+1);//i的位置也要取得到
            交换(arrayA,r,i);
        }
    }
    private SortingProblem5() {}
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
class OJReaderProblem5 extends StreamTokenizer {
    public OJReaderProblem5() {
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
