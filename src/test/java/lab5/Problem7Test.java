package lab5;

import org.junit.jupiter.api.Test;
import util.judge.Judge;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
//测试轮1：expected:6,
//actually:5,
//at query: 1, 5; at Array:0 1 0 1 3 暴力有问题。
//第二轮
//expected:4,
//actually:3,
//at query: 1, 5; at Array:1 3 4 1 0 //确实是4
class Problem7Test {
    @Test
    void singleJudge(){
        int[] arrayA = {1,3,4,1,0 };
        int l = 1;
        int r = 5;
        assertEquals(Problem7.bruteForceSolve(arrayA, l,r), Problem7.solve(arrayA, l, r));
    }
    private static boolean correctCasesHint = false;
    private static int option = 0;
    private static int[] sizes = {5, 2, 10000, 8, 10};
    private static int[] ranges = {5, 100, 5, 5, 5};
    private static int size = sizes[option];
    private static int range = ranges[option];
    private static int casePassed = 0;
    @Test
    void judge() {
        int[] arrayA = Judge.nextRandomIntArray(size, range);
        while (true){
            int l = Judge.random.nextInt(arrayA.length)+1;
            int r = Judge.random.nextInt(arrayA.length)+1;
            if (l>r){
                int temp = r;
                r = l;
                l = temp;
            }
            long solveExpected = 0, solveActually;
            try {
                solveExpected = Problem7.bruteForceSolve(arrayA, l, r);
                solveActually = Problem7.solve(arrayA, l, r);
            }catch (Exception e){
                System.err.printf("expected:%d, \nactually:%s, \nat query: %d, %d; at Array:%s\n\n", solveExpected,e.getMessage(), l, r, Judge.ArraysToString(arrayA));
                continue;
            }
            if (solveExpected!=solveActually) {
                System.err.printf("expected:%s, \nactually:%s, \nat query: %d, %d; at Array:%s\n\n", solveExpected, solveActually,  l, r,Judge.ArraysToString(arrayA));
            }else if (correctCasesHint){
                System.out.print(++casePassed+" cases has passed.\r");
            }
            if (casePassed>10000)
                arrayA = Judge.nextRandomIntArray(size, range);
        }
    }
}