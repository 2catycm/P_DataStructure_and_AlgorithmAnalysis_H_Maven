package lab8;

import org.junit.jupiter.api.Test;
import util.judge.Judge;
import util.syntax.Format;

import java.util.Arrays;
import java.util.Scanner;

class Problem7_NoIntersectionsPIzTest {
    @Test
    void singleTest(){
        int M = 2;
        final var tree = new VoidWeighedEdgeBasedTree(size);
        tree.addEdge(1, 2, 2);
        tree.addEdge(1, 3, 3);
        tree.addEdge(3, 4, 3);
        tree.addEdge(3, 5, 3);
        tree.addEdge(4, 6, 3);
        tree.addEdge(5, 7, 2);
        tree.addEdge(6, 8, 0);
        System.out.println(Problem7_NoIntersectionsPIz.solve(tree, M));
        System.out.println(Problem7ChangedSolve.solve(tree, M));
    }
    private static boolean correctCasesHint = true;
    private static int option = 5;
    /////////////////////////////0   1      2   3   4   5
    private static int[] mRanges = {1, 990, 15, 66, 3,  5};
    private static int[] sizes = {1, 10000, 20, 888, 5, 8};
    private static int[] weightRanges = {1, 1000000, 10000, 8888, 5, 5}; //bigger than size
    private static int size = sizes[option];
    private static int weightRange = weightRanges[option];
    private static int mRange = mRanges[option];
    private static int casePassed = 0;
    //    @Test
    private static OJReader in = new OJReader();
    public static void main(String[] args) {
        while (true){
            final var inputs = Judge.nextRandomWeightedTree(weightRange, size, (int) Math.log(size));
            final var tree = new VoidWeighedEdgeBasedTree(inputs);
            final var M = Judge.random.nextInt(mRange)+1;
            int solveExpected = 0, solveActually = 0;
            try {
                solveActually = Problem7_NoIntersectionsPIz.solve(tree, M);
//                solveExpected = humanSolve(tree, M); //人工对拍
                solveExpected = Problem7ChangedSolve.solve(tree, M); //人工对拍
            }catch (Exception e){
//                System.err.printf("expected:%d, \nactually:%s, \nwhen M is %d\nat tree:%s\n\n", solveExpected,e.getMessage(), M,  Format.arrayToString(tree.adjacencyList, "\n", "\n"));
                System.err.printf("expected:%d, \nactually:%s, \nwhen M is %d\nat tree:%s\n\n", solveExpected,e.getMessage(), M,  Format.deepToString(inputs, " "));
                continue;
            }
            if (solveExpected!=solveActually) {
//                System.err.printf("expected:%d, \nactually:%d, \nwhen M is %d\nat tree:%s\n\n", solveExpected, solveActually, M,  Format.arrayToString(tree.adjacencyList, "\n", "\n"));
                System.err.printf("expected:%d, \nactually:%d, \nwhen M is %d\nat tree:%s\n\n", solveExpected, solveActually, M,  Format.deepToString(inputs, " "));
            }else if (correctCasesHint){
                assert (solveActually>=0);
                System.out.print(++casePassed+" cases has passed.\r");
            }
        }
    }
    private static int humanSolve(VoidWeighedEdgeBasedTree tree, int M) {
        System.out.println("Given a tree T: "+ Format.arrayToString(tree.adjacencyList, "\n", "\n"));
        System.out.println("and M = "+ M);
        System.out.println("Can you please tell me the 任取M条不相交的简单路径，求M条中最短路径长度的最大值？");
        return in.nextInt();
    }
}