package lab3;

import java.util.Comparator;
import java.util.InputMismatchException;

public class Problem2QuickMedian {
//    6
//    1 2 3 6 5 4 bug
    private static OJReaderProblem3 in = new OJReaderProblem3();
    public static void main(String[] args) {
        final int[] ints = in.nextIntArray();
//        System.out.println(solve(ints));
        final int length = ints.length;
        if (length %2==0)
            System.out.println(solve(ints, length/2-1)+solve(ints, length/2)); //无需拷贝ints，理论上没问题，但是可能大数组会慢，最好还是有个int mid
        else
            System.out.println(solve(ints, (length-1)/2));
    }
    //返回的是中位数，而不是索引
//    发现1 3 2 4 6 5对3做partition有问题 //不，没问题，是right的问题
    static int solve(int[] arrayA, int targetLocation){
        int[] aux = new int[arrayA.length];
        int left = 0, right = arrayA.length;
//        int targetLocation = (arrayA.length-1)/2;
        while (left<right){
            final int locationOfLeft = partition(arrayA, left, right, left, aux);
            if (locationOfLeft==targetLocation)
                return arrayA[locationOfLeft];
            else if (locationOfLeft<targetLocation)
                left = locationOfLeft+1;
            else
//                right = locationOfLeft-1;
            right = locationOfLeft;
        }
        throw new InputMismatchException("算法有问题");
    }
    public static <T> int partition(int[] array, int startInclusive, int endExclusive, int randomPivotIndex, int[] aux) {
        Comparator<Integer> tComparator = Integer::compare;
        int temp;
        for (temp = startInclusive+1; temp < endExclusive; temp++) {
            if (tComparator.compare(array[temp-1], array[temp])!=0)
                break;
        }
        if (temp == endExclusive)
            return startInclusive+(endExclusive-startInclusive)/2;

        int start = startInclusive, end = endExclusive-1;
        final int pivot = array[randomPivotIndex];
        for (int i = startInclusive; i < endExclusive; i++) {
            if (i!=randomPivotIndex)
                if (tComparator.compare(array[i], pivot)<0) aux[start++] = array[i];
                else                                        aux[end--] = array[i];
        }
        aux[start] = pivot;
        System.arraycopy(aux, startInclusive, array, startInclusive, endExclusive-startInclusive);
        return start;
    }
//    static int partition(int[] arr)
}
