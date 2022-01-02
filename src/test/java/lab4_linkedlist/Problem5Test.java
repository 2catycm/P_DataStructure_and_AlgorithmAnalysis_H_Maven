package lab4_linkedlist;

import org.junit.jupiter.api.Test;
import util.judge.Judge;

import java.util.Arrays;

//option 3 found error at version1
//4 4 1 2 2 0 2 1 1 1
//3 3 1 3 0 2 2 4
//option 4 found error at version newSolve
//1 10
//2 2 3 4 0 1 1 3 0 0
class Problem5Test {
    private static boolean correctCasesHint = false;
    private static int option = 2;
    private static int[] sizes = {5, 2, 10000, 8, 10};
    private static int[] ranges = {5, 100, 5, 5, 5};
    private static int size = sizes[option];
    private static int range = ranges[option];
    private static int casePassed = 0;
    @Test
    void judge() {
        while (true){
            final int[] arrayA = Judge.nextRandomIntArray(size, range);
            final BidirectionalLinkedNodes<Integer> nodes1 = new BidirectionalLinkedNodes<>();
            Arrays.stream(arrayA).forEachOrdered(nodes1::pushAfter);
            final BidirectionalLinkedNodes<Integer> nodes2 = new BidirectionalLinkedNodes<>();
            Arrays.stream(arrayA).forEachOrdered(nodes2::pushAfter);
            try {
                Problem5.bruteForceSolve(nodes1);
                Problem5.newNewSolve(nodes2);
            }catch (NullPointerException e){
                System.err.printf("expected:%s, \nactually:null pointer exception, \nat Array:%s\n\n", nodes1, Judge.ArraysToString(arrayA));
                continue;
            }
            if (!nodes1.equals(nodes2)) {
                System.err.printf("expected:%s, \nactually:%s, \nat Array:%s\n\n", nodes1, nodes2, Judge.ArraysToString(arrayA));
            }else if (correctCasesHint){
                System.out.print(++casePassed+" cases has passed.\r");
            }
//            assertEquals(nodes1, nodes2);
        }
    }

}