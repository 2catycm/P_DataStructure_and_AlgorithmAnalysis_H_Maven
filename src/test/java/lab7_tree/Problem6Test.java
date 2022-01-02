package lab7_tree;

import util.judge.Judge;

import java.util.Arrays;

class Problem6Test {
    private static boolean correctCasesHint = true;
    private static int option = 0;
    private static int[] sizes = {1, 1000000, 10000, 8, 10};
    private static int[] kSizes = {1, 1000000, 10000, 8, 5};
    private static int size = sizes[option];
    private static int kSize = kSizes[option];
    private static int casePassed = 0;
//    @Test
    public static void main(String[] args) {
        while (true){
            final var tree = new VoidUnWeighedEdgeBasedTree(Judge.nextRandomUnweightedTree(size, 2));
            final var friends = Arrays.copyOf(Judge.nextRandomPermutation(size), kSize);
            int solveExpected = 0, solveActually = 0;
            try {
                solveExpected = Problem6.newNewSolve(tree, friends);
//                solveExpected = Problem6.newSolve(tree, friends);
//                solveActually = Problem6.newSolve(tree, friends);
                solveActually = solveExpected;
            }catch (Exception e){
                System.err.printf("expected:%d, \nactually:%s, \nat Tree:%s\nat Friends:%s\n\n", solveExpected,e.getMessage(), tree, Judge.ArraysToString(friends));
                continue;
            }
            if (solveExpected!=solveActually) {
                System.err.printf("expected:%d, \nactually:%d, \nnat Tree:%s\nat Array:%s\n\n", solveExpected, solveActually, tree, Judge.ArraysToString(friends));
            }else if (correctCasesHint){
                System.out.print(++casePassed+" cases has passed.\r");
            }
        }
    }
}