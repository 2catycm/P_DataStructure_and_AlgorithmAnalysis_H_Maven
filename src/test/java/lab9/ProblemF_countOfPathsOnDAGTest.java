package lab9;

import lab8.Problem7ChangedSolve;
import org.junit.jupiter.api.Test;
import util.judge.Judge;
import util.syntax.Format;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

class ProblemF_countOfPathsOnDAGTest {
    @Test
    void singleTest(){
        final var directedGraph = new DirectedGraph(5);
        directedGraph.addEdge(5,1);
        directedGraph.addEdge(4,3);
        directedGraph.addEdge(3,2);
        final var arrayA = new int[]{-1, 4,1,0,1,2};
        final var arrayB = new int[]{-1, 3,4,4,4,1};
        System.out.println(new ProblemF_countOfPathsOnDAG.Solution(directedGraph, arrayA, arrayB).getAnswer().getAsLong());
    }
    private static boolean correctCasesHint = true;
    private static int option = 3;
    /////////////////////////////0   1      2   3   4   5
    private static int[] verticesCnts = {1, 990, 15, 66, 5,  8};
    private static int[] edgeCnts = {1, 10000, 20, 20, 2, 3};
    private static int edgeCnt = edgeCnts[option];
    private static int verticesCnt = verticesCnts[option];
    private static int casePassed = 0;

    public static void main(String[] args) {
        while (true){
            final var directedGraph = DirectedGraph.nextRandomDirectedGraph(verticesCnt, edgeCnt);
            final var arrayA = Judge.nextRandomIntArray(verticesCnt + 1, 5);
            final var arrayB = Judge.nextRandomIntArray(verticesCnt + 1, 5); //第0位不管
            long solveExpected = 0, solveActually = 0;
            try {
                solveActually = new ProblemF_countOfPathsOnDAG.Solution(directedGraph, arrayA, arrayB).getAnswer().getAsLong();
                solveExpected = refSolve(directedGraph, arrayA, arrayB);
            }catch (Exception e){
                System.err.printf("expected:%d, \nactually:%s, \nwhen arrayA is %s\narrayB is %s\nat graph:%s\n\n", solveExpected,e.getMessage(), Arrays.toString(arrayA), Arrays.toString(arrayB), directedGraph);
                continue;
            }
            if (solveExpected!=solveActually) {
                System.err.printf("expected:%d, \nactually:%d, \nwhen arrayA is %s\narrayB is %s\nat graph:%s\n\n", solveExpected, solveActually, Arrays.toString(arrayA), Arrays.toString(arrayB), directedGraph);
            }else if (correctCasesHint){
                assert (solveActually>=0);
                System.out.print(++casePassed+" cases has passed.\r");
            }
        }
    }

    private static long refSolve(DirectedGraph directedGraph, int[] arrayA, int[] arrayB) throws IOException {
        final var processBuilder = new ProcessBuilder("D:\\EnglishStandardPath\\Practice_File\\P_DataStructure_and_AlgorithmAnalysis_H_Maven\\src\\main\\java\\lab9\\ref\\ref.exe");
        final var process = processBuilder.start();
        final var inputFromProcess = new Scanner(process.getInputStream());
        final var outputToProcess = new PrintWriter(process.getOutputStream());
        outputToProcess.println(1);//T
        outputToProcess.println(directedGraph.verticesCnt+" "+ directedGraph.edgesCnt);
        for (int i = 1; i < arrayA.length; i++) { //从1开始。
            outputToProcess.println(arrayA[i]+" "+arrayB[i]);
        }
        for (int i = 1; i <= directedGraph.verticesCnt; i++) {
            for (var relative:directedGraph.relativesOf(i))
                outputToProcess.println(i+" "+relative);
        }
        outputToProcess.flush();
        return inputFromProcess.nextLong();
    }
}