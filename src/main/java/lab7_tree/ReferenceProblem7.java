package lab7_tree;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

//# pragma OJ Main
public class ReferenceProblem7 {
//    private static OJReader in = new OJReader();
//    private static OJWriter out = new OJWriter();
//    public static void main(String[] args) {
//
//    }
    //delegate using classmates' code
    static Problem7New.TwoOptionalIntegers[] referenceSolve(int[][] edges) throws IOException {
        final var processBuilder = new ProcessBuilder("D:\\EnglishStandardPath\\Practice_File\\P_DataStructure_and_AlgorithmAnalysis_H_Maven\\src\\main\\java\\lab7\\ref\\lab7_7_new.exe");
        final var process = processBuilder.start();
        final var inputFromProcess = new Scanner(process.getInputStream());
        final var outputToProcess = new PrintWriter(process.getOutputStream());
        outputToProcess.println(edges.length+1);
        for (int i = 0; i < edges.length; i++) {
//            for (var v:tree.vertices[i]){
//                outputToProcess.println(i+" "+v);
//            }
            outputToProcess.println(edges[i][0]+" "+edges[i][1]);
        }
        outputToProcess.flush();
        final var critical = new Problem7New.TwoOptionalIntegers[edges.length+1 + 1];
        for (int i = 1; i <= edges.length+1; i++) {
            final var strings = inputFromProcess.nextLine().split(" ");
            if (strings.length==1)
                critical[i] = new Problem7New.TwoOptionalIntegers(Integer.parseInt(strings[0]));
            else
//                critical[i] = new Problem7New.TwoOptionalIntegers(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]));
                critical[i] = new Problem7New.TwoOptionalIntegers(Integer.parseInt(strings[1]), Integer.parseInt(strings[0]));
        }
        return critical;
    }

}
//# include "OnlineJudgeIO.java"