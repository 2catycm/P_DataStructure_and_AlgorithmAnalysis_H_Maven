package lab10;

import org.junit.jupiter.api.Test;
import util.judge.Judge;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DijkstraShortestPathTest {
    static EdgeWeightedDirectedGraph nextRandomConnectedWeightedDAG(int verticesCnt, int additionalEdgeCnt, int weightMax){
        final var random = new Random();
        final var result = new EdgeWeightedDirectedGraph(verticesCnt);
        for (int i = 1; i <= verticesCnt-1; i++) {
            result.addEdge(i, i+1, random.nextInt(weightMax));
        }
        for (int i = 0; i < additionalEdgeCnt; i++) {
            final var u = random.nextInt(verticesCnt) + 1;
            final var v = random.nextInt(verticesCnt) + 1;
            result.addEdge(Math.min(u,v), Math.max(u, v), random.nextInt(weightMax));
        }
        return result;
    }
    @Test
    public void pqTest(){
//        final var integers = new PriorityQueue<Integer>(Comparator.naturalOrder());
        final var integers = new PriorityQueue<Integer>();
        integers.add(3);
        integers.add(2);
        integers.add(-1);
        integers.add(1);
        integers.add(1);
        integers.add(-2);
        integers.add(-2);
        for (var e:integers){
            System.out.println(e);
        }
        assert(!integers.isEmpty());//遍历的是堆的顺序，不是真的poll出来给你。
    }
    @Test
    public void singleTest(){
        final var directedGraph = new EdgeWeightedDirectedGraph(5);
        directedGraph.addEdge(1,2,1);
        directedGraph.addEdge(1,4,3);
        directedGraph.addEdge(2,3,0);
        directedGraph.addEdge(2,5,0);
        directedGraph.addEdge(4,2,2);
        directedGraph.addEdge(4,5,2);
        long solveExpected = new DijkstraShortestPath(directedGraph, 1, true).getShortestDistance(verticesCnt);
        long solveActually = new DijkstraShortestPath(directedGraph, 1, false).getShortestDistance(verticesCnt);
        System.out.println(solveExpected);
        System.out.println(solveActually);
    }
    public static void main(String[] args) {
        while (true){
            final var directedGraph = nextRandomConnectedWeightedDAG(verticesCnt, edgeCnt, 5);
            long solveExpected = 0, solveActually = 0;
            try {
                solveExpected = new DijkstraShortestPath(directedGraph, 1, true).getShortestDistance(verticesCnt);
                solveActually = new DijkstraShortestPath(directedGraph, 1, false).getShortestDistance(verticesCnt);
            }catch (Exception e){
                System.err.printf("expected:%d, \nactually:%s, \nat graph:%s\n\n", solveExpected,e.getMessage(), directedGraph);
//                e.printStackTrace();
                continue;
            }
            if (solveExpected!=solveActually) {
                System.err.printf("expected:%d, \nactually:%d, \nat graph:%s\n\n", solveExpected, solveActually, directedGraph);
            }else if (correctCasesHint){
//                assert (solveActually>=0);
                System.out.print(++casePassed+" cases has passed.\r");
            }
        }
    }
    private static boolean correctCasesHint = true;
    private static int option = 4;
    /////////////////////////////0   1      2   3   4   5
    private static int[] verticesCnts = {1, 990, 15, 66, 5,  8};
    private static int[] edgeCnts = {1, 10000, 20, 20, 2, 3};
    private static int edgeCnt = edgeCnts[option];
    private static int verticesCnt = verticesCnts[option];
    private static int casePassed = 0;
}