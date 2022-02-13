package lab11_bonus;

//# pragma OJ Main
public class ProblemE_post_in2pre {
    private static OJReader in = new OJReader();
    private static OJWriter out = new OJWriter();
    public static void main(String[] args) {
        final var Q = in.nextInt();
        for (int i = 0; i < Q; i++) {
            final var n = in.nextInt();
            final var inOrder = in.nextIntArray(n);
            final var postOrder = in.nextIntArray(n);
            out.println(solve(inOrder, postOrder));
        }
    }

    private static String solve(int[] inOrder, int[] postOrder) {
        final var stringBuilder = new StringBuilder();
        solve(0, inOrder.length, inOrder, postOrder, stringBuilder);
        return null;
    }

    private static void solve(int startInclusive, int endExclusive, int[] inOrder, int[] postOrder, StringBuilder stringBuilder) {
        int root = postOrder[endExclusive-1];
//        for (int i = 0; i < ; i++) {
//
//        }
    }
}
//# include "OnlineJudgeIO.java"