package util.syntax;

import org.apache.commons.lang3.time.StopWatch;
import util.syntax.OJWriter;

import java.util.Arrays;
import java.util.Random;

public class TestClient{
    public static void main(String[] args) {
//        test1();
//        test2((int) 1e7);
        test2(5);//而且不保证顺序
        //所以绝对不要用！
    }

    private static void test2(int size) {
        int[] array = new int[size];
        final Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt();
        }
        System.out.println("start: ");
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final StringBuilder watchInfo = new StringBuilder();
        final OJWriter ojWriter = new OJWriter();
        ojWriter.printlnIntArray(array);
        watchInfo.append(stopWatch.formatTime()).append("\n");
        stopWatch.reset();
        stopWatch.start();
//        ojWriter.printIntArray2(array);
        watchInfo.append(stopWatch.formatTime()).append("\n");
        stopWatch.reset();
        stopWatch.start();

        System.out.println();
        System.err.println(watchInfo);

        //结论，stream并不快
    }

    private static void test1() {
//        String[] a = {"123","12342","hello world"};
        int size = (int) 1e7;
        int[] array = new int[size];
        final Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt();
        }
        System.out.println("start: ");
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final StringBuilder watchInfo = new StringBuilder();
        final OJWriter ojWriter = new OJWriter();
        ojWriter.printlnIntArray(array);
//        System.err.println(stopWatch.formatTime());
//        System.err.println((double) size / stopWatch.getNanoTime() * 10e9);
        //一秒钟  1.2192063527478534E8
        watchInfo.append(stopWatch.formatTime()).append("\n");
        stopWatch.reset();
        stopWatch.start();
        System.out.println(Arrays.toString(array));
        watchInfo.append(stopWatch.formatTime()).append("\n");
        stopWatch.reset();
        stopWatch.start();
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]+" ");
        }
//        System.err.println(stopWatch.formatSplitTime());
        watchInfo.append(stopWatch.formatTime()).append("\n");
        stopWatch.reset();
        stopWatch.start();

        System.out.println();
        System.err.println(watchInfo);

        //00:00:00.788
        //00:00:01.142
        //00:00:12.025
        //结论，可以提速。
        //结论，10e7可以输出
    }
}