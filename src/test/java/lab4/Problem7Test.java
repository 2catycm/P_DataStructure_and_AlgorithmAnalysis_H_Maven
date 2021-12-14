package lab4;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;
import util.algorithm.SortingHelper;
import util.judge.Judge;

import java.util.Arrays;
import java.util.stream.IntStream;

import static java.lang.Math.log;
import static lab4.Problem7Test.size;

//option 1 found error at version1
//2 1 k=1
//option 3
//expected:179,
//        actually:171,
//        when k = 5, at Array:7 2 1 3 4 6 8 5
//expected:224,
//        actually:200,
//        when k = 5, at Array:6 2 7 3 8 4 5 1

//最后一个bug， long sum才行。
class Problem7SingleTest{
    public static void main(String[] args) {
//        System.out.println(Problem7.solve(new int[]{2, 1}, 1));
//        System.out.println(Problem7.solve(new int[]{7,2,1,3,4,6,8,5}, 5));
        System.out.println(Problem7.solve(new int[]{6,2,7,3,8,4,5,1}, 5));
    }
}
class Complexity{
    public static void main(String[] args) {
        StopWatch sw = new StopWatch();
        long previous = -1;
        for (int i = 8; i < 100000; i+=i) {
            sw.reset();
            sw.start();
            Problem7.solve(Problem7Test.nextRandomPermutation(i), 5);
            sw.stop();
//            System.out.println("size = "+i+" at: "+sw.formatTime());
            final long nanoTime = sw.getNanoTime();
            System.out.println(log(nanoTime/previous)/log(2));
            previous = nanoTime;
        }
    }
}
class Problem7Test {
    public static boolean correctCasesHint = true;
//    public static int option = 2;
    public static int option = 6;
    public static int[] sizes = {5, 2, 100, 8, 10, 100000, 1000, 1000};
    public static int[] ks =   {5, 1, 5, 5, 5,  80,  48, 1};
    public static int size = sizes[option];
    public static int k = ks[option];
    public static int casePassed = 0;

//    @Test
    public static void main(String[] args) {
        while (true){
            final int[] randomPermutation = nextRandomPermutation(size);
            int solve1 = 0, solve2 = 0;
            try {
//                solve1 = Problem7.bruteForceSolve(randomPermutation, k);
                solve1 = Problem7.solve(randomPermutation, k);
//                solve2 = Problem7.solve(randomPermutation, k);
                solve2 = Problem7.newSolve(randomPermutation, k);
//                solve1 = solve2;
            }catch (NullPointerException e){
                System.err.printf("expected:%d, \nactually:null pointer exception, \nwhen k = %d, at Array:%s\n\n", solve1, k, Judge.ArraysToString(randomPermutation));
                continue;
            }
            if (solve1!=solve2) {
                System.err.printf("expected:%d, \nactually:%d, \nwhen k = %d, at Array:%s\n\n", solve1, solve2, k,Judge.ArraysToString(randomPermutation));
            }else if (correctCasesHint){
                System.out.print(++casePassed+" cases has passed.\r");
            }
//            assertEquals(nodes1, nodes2);
        }
    }
    public static int[] nextRandomPermutation(int size){
        final int[] ints = IntStream.range(1, size + 1).toArray();
        SortingHelper.knuthShuffle(ints);
        return ints;
    }
}
