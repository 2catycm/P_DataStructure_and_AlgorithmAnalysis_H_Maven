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

public class Problem6 {
    private static OJReaderProblem6 in = new OJReaderProblem6();
    public static void main(String[] args) {
        int n = in.nextInt();
        int p = in.nextInt();
        int q = in.nextInt();
        Plant[] plants = new Plant[n];
        for (int i = 0; i < n; i++) {
            plants[i] = new Plant(in.nextInt(), in.nextInt());
        }
        System.out.println(solve(plants, p, q));
    }
    static long solve(Plant[] plants, int p, int q){
        final long basicSum = Arrays.stream(plants).parallel().mapToLong(Plant::getStrength).sum();
        if (q==0) {
            return basicSum;
        }
        QuickSortProblem6.quickSort(plants, Comparator.comparingInt(Plant::getHeightMinusStrength).reversed());
        long sum = basicSum; int qRemain = q;
        //先释放q技能, 放完q次， 暂时认为只放给大于0的植物。
        int i = 0;
        for (; i < plants.length; i++) {
            final int heightMinusStrength = plants[i].getHeightMinusStrength();
            if(heightMinusStrength >0 && qRemain-->0)
                sum+=heightMinusStrength;
            else break;
        }
        //用p技能调整
        long maxSum = sum, currentSum;
        for (int j = 0; j < /*Math.min(plants.length, i+1)*/plants.length; j++) {//i已经到达了无法放q的时候
            currentSum = sum;
            //rollback q技能效果：
            if (j<i) //被放过技能
                currentSum-=plants[j].getHeightMinusStrength();
            else if (qRemain<=0) //没被放过技能，请求一个技能，发现没有技能，需要撤销前面别人的技能
                currentSum-=plants[i-1].getHeightMinusStrength();
            else ;//不然，就直接用，不撤销
            //开启p辅助，重新用q
            currentSum+= ((long)(plants[j].getHeight()) <<p) - plants[j].getStrength();
            maxSum = Math.max(maxSum, currentSum);
        }
        return maxSum;
    }
    static long soundBruteForce(Plant[] plants, int p, int q){
        final long basicSum = Arrays.stream(plants).parallel().mapToLong(Plant::getStrength).sum();
        if (q==0) {
            return basicSum;
        }
        QuickSortProblem6.quickSort(plants, Comparator.comparingInt(Plant::getHeightMinusStrength).reversed());
        Plant[] temp;
        //先释放p技能
        long currentSum, maxSum = basicSum; int qRemain;
        for (int i = 0; i < plants.length; i++) {
            currentSum = 0; qRemain = q-1;
//            temp = Arrays.copyOf(plants,plants.length);
            temp = Arrays.stream(plants).parallel().map(Plant::clone).toArray(Plant[]::new);
            //使用所有的p技能，和一次q技能
            temp[i].setHeight(temp[i].getHeight()<<p);
            temp[i].setStrength(temp[i].getHeight());
            //使用q技能
            ShellSort.shellSort(temp, Comparator.comparingInt(Plant::getHeightMinusStrength).reversed());
            for (int j = 0; j < temp.length; j++) {
                if (temp[j].getHeightMinusStrength()>0 && qRemain-->0)
                    temp[j].setStrength(temp[j].getHeight());
                else break;
            }
            currentSum = Arrays.stream(temp).parallel().mapToLong(Plant::getStrength).sum();
            maxSum = Math.max(maxSum, currentSum);
        }
        return maxSum;
    }
    
    //p是增高次数，q是强度=高。
    //先不考虑p释放给谁，q放好之后，撤销掉那些方q没用的人（高比强度还低）
    //q有以下基本优化性质：
    //一个植物最多拥有一个q，否则浪费，如果比n要多，那每个都可以拥有一个。
    //有些植物高度本来就高，如果p不够用，单是把q给它一个都能提高很多。
    //q=0， 那么p根本没用
    //q=1， 梭哈最大的给一个植物. 不，应该是梭哈潜力最大的一个植物。如果
    //最大的是 8  16      小的是    4 1     p=1，   q依然应该给小的
    static long solveBruteForce(Plant[] plants, int p, int q) {
        final long basicSum = Arrays.stream(plants).parallel().mapToLong(Plant::getStrength).sum();
        if (q==0) {
            return basicSum;
        }
//        if (q>=plants.length) //可以选择放，也可以选择不放（可能还不如strength）
//        if (p==0)
//            return
        QuickSortProblem6.quickSort(plants, Comparator.comparingInt(Plant::getHeightMinusStrength).reversed());
        long maxSum = basicSum/*Integer.MIN_VALUE*/, sum; int qRemain;
        //一颗植物可能出问题：梭哈行为给它还是达不到strength。
        for (int i = 0; i < plants.length; i++) {//梭哈给第i个植物.   现在是从高度价值最高到高度价值最低
            sum = basicSum;qRemain = q-1;//q已经用过一次了，就是给p的
            sum+= (((long)plants[i].getHeight()) <<p) - plants[i].getStrength();
            for (int j = 0; j < plants.length; j++) {
                if (i==j) continue;
                final int heightMinusStrength = plants[j].getHeightMinusStrength();
                if(heightMinusStrength >0 && qRemain-->0)
                    sum+=heightMinusStrength;
                else break;
            }
            //不必遍历多次
//            for (int j = 0; j < plants.length; j++) {
//                if (j==i)
//                    continue;
//                if (qRemain-->0) //可以到负数，反正不用
//                    sum+= Math.max(plants[j].getHeight(), plants[j].getStrength());
//                else sum+=plants[j].getStrength();
//            }

            maxSum = Math.max(maxSum, sum);
        }
        return maxSum;
    }
}
//class Strategy implements Comparable<Strategy>{
//    private Plant[] plants;
//    private Long sum;
//    public Strategy(Plant[] plants) {
//        this.plants = plants;
//    }
//    public long getSum() {
//        return sum==null?Arrays.stream(plants).parallel().mapToLong(Plant::calculatedStrength).sum():sum;
//    }
//    @Override
//    public int compareTo(Strategy o) {
//        return Long.compare(this.getSum(), o.getSum());
//    }
//}
class Plant implements Cloneable{
    private int height, strength;
//    private int hCount, sCount;
//
//    public int getHCount() {
//        return hCount;
//    }
//
//    public int getSCount() {
//        return sCount;
//    }
//    public long calculatedStrength(){
//        if (sCount>0)
//                return Math.max(height<<hCount, strength);
//        else    return strength;
//    }
    @Override
    public String toString() {
        return "Plant{" +
                "height=" + height +
                ", strength=" + strength +
                '}';
    }

    public Plant(int height, int strength) {
        this.height = height;
        this.strength = strength;
    }
    public int getHeightMinusStrength() {
        return height-strength;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    @Override
    public Plant clone() {
        try {
            Plant clone = (Plant) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
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
}
final class QuickSortProblem6{
    private QuickSortProblem6(){}
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
class OJReaderProblem6 extends StreamTokenizer {
    public OJReaderProblem6() {
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