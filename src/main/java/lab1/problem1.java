package lab1;

import java.util.Scanner;

public class problem1 {
    private static final Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        final int[] arrayA = new int[in.nextInt()];
        for (int i = 0; i < arrayA.length; i++) {
            arrayA[i] = in.nextInt();
        }
        final int[] arrayB = new int[in.nextInt()];
        for (int i = 0; i < arrayB.length; i++) {
            arrayB[i] = in.nextInt();
        }
        for (int i = 0; i < arrayB.length; i++) {
            int element = arrayB[i];
            boolean yes = false;
            for (int j = 0; j < arrayA.length; j++) {
                if (arrayA[j]==element) {
                    yes = true;
                    break;
                }
            }
            System.out.println(yes?"yes":"no");
        }
    }
}

/**
 * @author 叶璨铭
 */
class MyArrayAlgorithmLibrary {
    //return index of array
    //assert the array has been sorted
    public static int binarySearch(Comparable[] array, Comparable element){
        return binarySearch(array, element, 0, array.length);
    }
    private static int binarySearch(Comparable[] array, Comparable element, int from, int to){
        int mid = (from+to)/2;
        switch ((int) Math.signum(array[mid].compareTo(element))){
            case 0:
                return mid;
//                break;
            case 1:
//                return binarySearch(from, )
                break;
            case -1:
                break;
        }
        return -1;
    }
    public static void sort(Comparable[] array){

    }
    //for debug purpose
    public static boolean isSorted(Comparable[] array){

        return false;
    }
}
