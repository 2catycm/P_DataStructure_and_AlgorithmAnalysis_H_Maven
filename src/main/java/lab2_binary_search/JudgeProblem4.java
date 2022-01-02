package lab2_binary_search;

import org.apache.commons.lang3.time.StopWatch;

import java.util.Arrays;

public class JudgeProblem4 {

    public static void main(String[] args) {
//        cpp();
        java();
    }
    private static int n = 500000;//50000
    private static void java() {
        int[] array = new int[n];
        Arrays.fill(array,1);
//        for (int i = 0; i < array.length; i++) {
//            array[i] = 1
//        }
        final int min = Arrays.stream(array).min().getAsInt();
        final int max = Arrays.stream(array).max().getAsInt();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        System.out.println(Problem4.solve(array, max, min));
        stopWatch.stop();
        System.out.println(stopWatch.formatTime());
        System.out.println((n-1)*n/2);
    }

    //java lab2/JudgeProblem4 | .\lab2\problem4.exe
    //javac lab2/JudgeProblem4.java
    private static void cpp() {
        System.out.println(n);
        for (int i = 0; i < n; i++) {
            System.out.print(1+" ");
        }
        System.out.println();
    }
}
