package lab7_tree;

import util.judge.Judge;

import java.io.IOException;
import java.util.Arrays;

class Problem7NewTest {
    private static boolean correctCasesHint = true;
    private static int option = 0;
    private static int[] sizes = {4, 1000000, 8, 25, 10};
    private static int size = sizes[option];
    private static int casePassed = 0;
//    @Test
    public static void main(String[] args) throws IOException {
        while (true) {
            final var edges = Judge.nextRandomUnweightedTree(size, 2);
            final var tree = new VoidUnWeighedEdgeBasedTree(edges);
//            assertEquals(ReferenceProblem7.referenceSolve(tree), Problem7New.solve(tree));

            Problem7New.TwoOptionalIntegers[] solveExpected = null, solveActually = null;
            try {
                solveExpected = ReferenceProblem7.referenceSolve(edges);
                solveActually = Problem7New.solve(tree);
            }catch (Exception e){
                System.err.printf("expected:%s, \nactually:%s, \nat Tree:%s\n\n", Judge.ArraysToString(solveExpected, "\n"),e.getMessage(), tree);
                continue;
            }
            if (!Arrays.equals(solveExpected, solveActually)) {
                System.err.printf("expected:%s, \nactually:%s, \nnat Tree:%s\n\n", Judge.ArraysToString(solveExpected, "\n"), Judge.ArraysToString(solveActually, "\n"), tree);
            }else if (correctCasesHint){
                System.out.print(++casePassed+" cases has passed.\r");
            }
        }
    }
}