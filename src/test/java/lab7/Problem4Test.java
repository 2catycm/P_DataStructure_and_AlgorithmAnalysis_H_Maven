package lab7;

import lab5.Problem7;
import org.junit.jupiter.api.Test;
import util.algorithm.SortingHelper;
import util.judge.Judge;
import util.syntax.Format;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class Problem4Test {
    private static boolean correctCasesHint = true;
    private static int option = 0;
    private static int[] sizes = {5, 2, 10000, 8, 10};
    private static int size = sizes[option];
    private static int casePassed = 0;
    @Test
    void judge() {
        while (true){
            int[] arrayA = new int[size];
            for (int i = 0; i < size; i++) {
                arrayA[i] = i;
            }
            int[] arrayB = Arrays.copyOf(arrayA, arrayA.length);
            while (invalid(arrayB))
                SortingHelper.knuthShuffle(arrayB);
            int solveExpected = 0, solveActually = 0;
            try {
                final var preOrder = Format.arrayToString(arrayA, "", "");
                final var postOrder = Format.arrayToString(arrayB, "", "");
                solveExpected = Problem4.newSolve(preOrder, postOrder);
                solveActually = Problem4.solve(preOrder, postOrder);
            }catch (Exception e){
                System.err.printf("expected:%d, \nactually:%s, \nat Array:%s\n\n", solveExpected,e.getMessage(), Judge.ArraysToString(arrayB));
                continue;
            }
            if (solveExpected!=solveActually) {
                System.err.printf("expected:%d, \nactually:%d, \nat Array:%s\n\n", solveExpected, solveActually, Judge.ArraysToString(arrayB));
            }else if (correctCasesHint){
                System.out.print(++casePassed+" cases has passed.\r");
            }
        }

    }

    private boolean invalid(int[] arrayB) {
        return arrayB[arrayB.length-1]!=0;
    }
}