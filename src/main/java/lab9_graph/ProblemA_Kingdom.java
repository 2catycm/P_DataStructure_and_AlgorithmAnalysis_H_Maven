package lab9_graph;


//# pragma OJ Main
public class ProblemA_Kingdom {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();

    public static void main(String[] args) {
        final var T = in.nextInt();
        for (int i = 0; i < T; i++) {
            final var n = in.nextInt();
            final var m = in.nextInt();
            final var adjacentMatrix = new int[n][n];//index是减一的。
            for (int j = 0; j < m; j++) {
                final var u = in.nextInt();
                final var v = in.nextInt();
                adjacentMatrix[u-1][v-1]++;
            }
            out.print(Format.deepToString(adjacentMatrix));
        }
        out.println();
    }
}
//# include "OnlineJudgeIO.java"
//# include "Format.java"