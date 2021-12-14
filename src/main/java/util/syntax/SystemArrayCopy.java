package util.syntax;

import java.util.Arrays;

public class SystemArrayCopy {
    public static void main(String[] args) {
//        int[] a = {1,2,3,4,5,6,7,8};
        Integer[] a = {1,2,3,4,5,6,7,8};
//        final var ints = new int[9];
        final var ints = new Integer[9];
        System.arraycopy(a,0, ints, 1, 8);
        System.out.println(Arrays.toString(ints));
    }
}
