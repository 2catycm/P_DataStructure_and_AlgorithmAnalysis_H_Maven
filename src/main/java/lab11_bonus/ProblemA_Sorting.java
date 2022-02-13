package lab11_bonus;

import java.util.Arrays;

//# pragma OJ Main
public class ProblemA_Sorting {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        final int[] ints = in.nextIntArray(in.nextInt());
        out.printlnIntArray(Arrays.stream(ints).sorted().toArray());
    }
}
//# include "OnlineJudgeIO.java"